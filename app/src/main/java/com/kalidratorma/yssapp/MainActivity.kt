package com.kalidratorma.yssapp

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.adapter.PlayerAdapter
import com.kalidratorma.yssapp.model.Coach
import com.kalidratorma.yssapp.model.Contract
import com.kalidratorma.yssapp.model.Parent
import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.model.security.auth.AuthenticationRequest
import com.kalidratorma.yssapp.model.security.auth.AuthenticationResponse
import com.kalidratorma.yssapp.model.security.user.Role
import com.kalidratorma.yssapp.model.security.user.User
import com.kalidratorma.yssapp.service.ApiService
import com.kalidratorma.yssapp.service.AuthService
import com.kalidratorma.yssapp.service.RetrofitHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var authService: AuthService
    private var progressDialog: ProgressDialog? = null

    private lateinit var player: Player
    private lateinit var parent: Parent
    private lateinit var coach: Coach
    private lateinit var user: User

    private lateinit var playerAdapter: PlayerAdapter;
    private lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authService = RetrofitHelper.getInstance().create(AuthService::class.java)
        apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        setContentView(R.layout.login)

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            var username: String = findViewById<EditText>(R.id.userNameEditText).text.toString();
            var password: String = findViewById<EditText>(R.id.passwordEditText).text.toString();
            var request = AuthenticationRequest(username, password)
            login(request);

        }
    }

    private fun login(request: AuthenticationRequest) {

        lifecycleScope.launch {
            var localUser: User? = null
            showLoading("Аутентификация ...")
            val loginResult = authService.enter(request)
            if (loginResult.isSuccessful) {
                Log.e("login", "login success: ${loginResult.body()}")
                var loginResponse: AuthenticationResponse? = loginResult.body();
                // TODO Add
                //  String authToken = "Bearer " + loginResponse.token;
                //  Request request = original.newBuilder()
                //                    .header("Authorization", authToken)
                //                    .method(original.method(), original.body()).build();
                var userResult = apiService.getUserByName(request.username)
                if (userResult.isSuccessful) {
                    Log.e("getUser", "getUser success: ${userResult.body()}")
                    if (userResult.body() != null) {
                        user = userResult.body()!!
                        when (user.role) {
                            Role.CLIENT -> {
                                var parentResult = apiService.getParentByUserId(user.id)
                                if (userResult.isSuccessful) {
                                    Log.e("getUser", "getUser success: ${parentResult.body()}")
                                    parent = parentResult.body()!!
                                    openClientContext(parent)
                                } else {
                                    Log.e("getUser", "getUser failed: ${parentResult.message()}")
                                }
                            }
                            Role.COACH -> openCoachContext(user)
                            Role.ADMIN -> openAdminContext(user)
                            else -> {Log.e("login", "Что то пошло не так!")}
                        }
                    } else {
                        Log.e("login", "Что то пошло не так!")
                    }
                } else {
                    Log.e("getUser", "getUser failed: ${userResult.message()}")
                }
            } else {
                Log.e("login", "login failed: ${loginResult.message()}")
            }
            progressDialog?.dismiss()
        }
    }


    private fun openClientContext(parent: Parent) {
        setContentView(R.layout.client)

        findViewById<Button>(R.id.playersButton).setOnClickListener {
            openPlayers(parent)
        }

        findViewById<Button>(R.id.contractButton).setOnClickListener {
            openContract(parent.contracts)
        }

        findViewById<Button>(R.id.userSettingsButton).setOnClickListener {
            openUserSettings(parent.user)
        }

        findViewById<TextView>(R.id.surnameTextView).text = parent.surname
        findViewById<TextView>(R.id.nameTextView).text = parent.name
        findViewById<TextView>(R.id.patronymicTextView).text = parent.patronymic

    }

    private fun openContract(contracts: List<Contract>?) {
        TODO("Not yet implemented")
    }

    private fun openUserSettings(user: User?) {
        TODO("Not yet implemented")
    }

    private fun openPlayers(parent: Parent) {
        lifecycleScope.launch {
            showLoading("Загрузка данных с сервера ...")
            val result = apiService.getPlayersByParentId(parent.id)
            if (result.isSuccessful) {
                Log.e("openPlayers", "openPlayers success: ${result.body()}")
                var playerList: List<Player> = result.body()!!;
                fillRecyclerView(playerList);
            } else {
                Log.e("openPlayers", "openPlayers failed: ${result.message()}")
            }
            progressDialog?.dismiss()
        }
    }

    private fun fillRecyclerView(playerList: List<Player>) {
        setContentView(R.layout.player_list)
        recyclerView = findViewById(R.id.playersRecyclerView)
        playerAdapter = PlayerAdapter(playerList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = playerAdapter
    }

    private fun openCoachContext(user: User) {
        setContentView(R.layout.coach)
    }

    private fun openAdminContext(user: User) {
        setContentView(R.layout.admin)
    }

    private fun getPlayerById(): Player? {

        lifecycleScope.launch {
            showLoading("Загрузка данных с сервера ...")
            val result = apiService.getPlayerById(1)
            if (result.isSuccessful) {
                Log.e("getPlayerById", "getPlayerById success: ${result.body()}")
                player = result.body()!!;
                showMainLayout(player)
            } else {
                Log.e("getPlayerById", "getPlayerById failed: ${result.message()}")
            }
            progressDialog?.dismiss()
        }
        return player
    }

    private fun showMainLayout(player: Player?) {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy г.")

        setContentView(R.layout.player)

        findViewById<Button>(R.id.playersButton).setOnClickListener {
            openCoachTask()
        }

        findViewById<Button>(R.id.contractButton).setOnClickListener {
            openCoachGrade()
        }

        findViewById<Button>(R.id.userSettingsButton).setOnClickListener {
            openTests()
        }

        findViewById<Button>(R.id.statsButton).setOnClickListener {
            openStats()
        }

        findViewById<TextView>(R.id.surnameTextView).text = player?.surname
        findViewById<TextView>(R.id.nameTextView).text = player?.name
        findViewById<TextView>(R.id.birthDateEditTextDate).text =
            simpleDateFormat.format(player?.birthDate)
        DownloadImageFromInternet(findViewById<ImageView>(R.id.photoImageView)).execute(player?.photo)
    }

    private fun openCoachTask() {
        setContentView(R.layout.coach_tasks)
        findViewById<Button>(R.id.button).setOnClickListener {
            showMainLayout(player)
        }
    }

    private fun openCoachGrade() {
        setContentView(R.layout.coach_assessment)
    }

    private fun openTests() {
        setContentView(R.layout.testing)
    }

    private fun openStats() {
        setContentView(R.layout.statistics)
    }

    private fun showLoading(msg: String) {
        progressDialog = ProgressDialog.show(this, null, msg, true)
    }


    private inner class DownloadImageFromInternet(var imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {
        init {
            Toast.makeText(
                applicationContext, "Загрузка изображения ...",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }
}