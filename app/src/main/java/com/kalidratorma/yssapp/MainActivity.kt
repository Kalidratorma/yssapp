@file:Suppress("DEPRECATION")

package com.kalidratorma.yssapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalidratorma.yssapp.adapter.PlayerAdapter
import com.kalidratorma.yssapp.adapter.TaskAdapter
import com.kalidratorma.yssapp.adapter.VideoLinkAdapter
import com.kalidratorma.yssapp.model.ContentFile
import com.kalidratorma.yssapp.model.Parent
import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.model.Task
import com.kalidratorma.yssapp.model.TaskReport
import com.kalidratorma.yssapp.model.entity.request.TaskReportRequest
import com.kalidratorma.yssapp.model.security.auth.AuthenticationRequest
import com.kalidratorma.yssapp.model.security.user.Role
import com.kalidratorma.yssapp.model.security.user.User
import com.kalidratorma.yssapp.service.ApiService
import com.kalidratorma.yssapp.service.AuthService
import com.kalidratorma.yssapp.service.RetrofitHelper
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.time.Instant


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    companion object {
        const val PICK_IMAGE_FOR_REPORT_TASK = 1
    }

    private lateinit var apiService: ApiService
    private lateinit var authService: AuthService

    private var progressDialog: ProgressDialog? = null

    private lateinit var parent: Parent

    //  private lateinit var coach: Coach
    private lateinit var user: User

    private lateinit var recyclerView: RecyclerView

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var videoLinkAdapter: VideoLinkAdapter

    private lateinit var globalParent: Parent
    private var globalFileMap: HashMap<Int, List<ContentFile>> = HashMap()
    private lateinit var globalPlayerList: List<Player>
    private lateinit var globalPlayer: Player
    private lateinit var globalTaskList: List<Task>
    private lateinit var globalTask: Task
    private lateinit var globalVideoContentFileList: List<ContentFile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window: Window = this.window

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = resources.getColor(R.color.red)
        window.navigationBarColor = resources.getColor(R.color.red)

        authService = RetrofitHelper.getInstance().create(AuthService::class.java)
        apiService = RetrofitHelper.getInstance().create(ApiService::class.java)

        setContentView(R.layout.login)

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val username: String = findViewById<EditText>(R.id.userNameEditText).text.toString()
            val password: String = findViewById<EditText>(R.id.passwordEditText).text.toString()
            val request = AuthenticationRequest(username, password)
            login(request)

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Companion.PICK_IMAGE_FOR_REPORT_TASK) {
            data?.data?.let { uri ->
                uploadFile(Companion.PICK_IMAGE_FOR_REPORT_TASK, uri)
            }
            data?.clipData?.run {
                val array: ArrayList<Uri> = ArrayList()
                for (i in 0 until itemCount) {
                    val uri = getItemAt(i).uri as Uri
                    array.add(uri)
                }
                uploadFile(Companion.PICK_IMAGE_FOR_REPORT_TASK, *array.toTypedArray())
            }
        } else super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("Recycle")
    private fun uploadFile(requestCode: Int, vararg uris: Uri) {
        lifecycleScope.launch {
            val filePart: ArrayList<MultipartBody.Part> = ArrayList()
            for (uri in uris) {
                val stream = contentResolver.openInputStream(uri) ?: return@launch
                val mediaType = contentResolver.getType(uri)?.let { MediaType.parse(it) }
                val request = RequestBody.create(
                    contentResolver.getType(uri)?.let { MediaType.parse(it) },
                    stream.readBytes()
                )
                filePart.add(
                    MultipartBody.Part.createFormData(
                        "file",
                        "${mediaType?.type()}.${mediaType?.subtype()}",
                        request
                    )
                )
            }
            try {
                showLoading("Выгрузка файлов на сервер ...")
                val uploadFileResult = apiService.uploadFile(*filePart.toTypedArray())
                if (uploadFileResult.isSuccessful) {
                    Log.e("uploadFile", "uploadFile success: ${uploadFileResult.body()}")
                    val resultContentFileList = uploadFileResult.body() as List<ContentFile>
                    if (globalFileMap[requestCode].isNullOrEmpty()) {
                        globalFileMap[requestCode] = resultContentFileList
                    } else {
                        globalFileMap[requestCode]!!.plus(resultContentFileList)
                    }
                } else {
                    Log.e("uploadFile", "uploadFile failed: ${uploadFileResult.message()}")
                }

            } catch (e: Exception) {
                Log.d("MyActivity", "Something went wrong: ${e.message}")
                progressDialog?.dismiss()
                return@launch
            }
            Log.d("MyActivity", "on finish upload file")
            progressDialog?.dismiss()
        }
    }

    private fun login(request: AuthenticationRequest) {

        lifecycleScope.launch {
            showLoading("Аутентификация ...")
            val loginResult = authService.enter(request)
            if (loginResult.isSuccessful) {
                Log.e("login", "login success: ${loginResult.body()}")
                // var loginResponse: AuthenticationResponse?= loginResult.body();
                // TODO Add
                //  String authToken = "Bearer " + loginResponse.token;
                //  Request request = original.newBuilder()
                //                    .header("Authorization", authToken)
                //                    .method(original.method(), original.body()).build();
                val userResult = apiService.getUserByName(request.username)
                if (userResult.isSuccessful) {
                    Log.e("getUser", "getUser success: ${userResult.body()}")
                    if (userResult.body() != null) {
                        user = userResult.body()!!
                        when (user.role) {
                            Role.CLIENT -> {
                                val parentResult = apiService.getParentByUserId(user.id)
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
                            else -> {
                                Log.e("login", "Что то пошло не так!")
                            }
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
        globalParent = parent

        setContentView(R.layout.client)

        findViewById<Button>(R.id.exitButton).setOnClickListener {
            finishAndRemoveTask()
        }

        findViewById<Button>(R.id.playersButton).setOnClickListener {
            openPlayers(parent)
        }

//        findViewById<Button>(R.id.contractButton).setOnClickListener {
//            openContract(parent.contracts)
//        }

//        findViewById<Button>(R.id.userSettingsButton).setOnClickListener {
//            openUserSettings(parent.user)
//        }

        findViewById<TextView>(R.id.surnameTextView).text = parent.surname
        findViewById<TextView>(R.id.nameTextView).text = parent.name
        findViewById<TextView>(R.id.patronymicTextView).text = parent.patronymic

    }

//    private fun openContract(contracts: List<Contract>?) {
//        TODO("Not yet implemented")
//    }

//    private fun openUserSettings(user: User?) {
//        TODO("Not yet implemented")
//    }

    private fun openPlayers(parent: Parent) {
        lifecycleScope.launch {
            showLoading("Загрузка данных с сервера ...")
            val result = apiService.getPlayersByParentId(parent.id)
            if (result.isSuccessful) {
                Log.e("openPlayers", "openPlayers success: ${result.body()}")
                val playerList: List<Player> = result.body()!!
                fillAndOpenPlayerRecyclerView(playerList)
            } else {
                Log.e("openPlayers", "openPlayers failed: ${result.message()}")
            }
            progressDialog?.dismiss()
        }
    }

    private fun fillAndOpenPlayerRecyclerView(playerList: List<Player>) {
        globalPlayerList = playerList
        setContentView(R.layout.player_list)

        findViewById<Button>(R.id.backToClientButton).setOnClickListener {
            openClientContext(globalParent)
        }



        recyclerView = findViewById(R.id.playersRecyclerView)
        playerAdapter = PlayerAdapter(playerList)
        playerAdapter.onItemClick = { player ->
            showPlayerLayout(player)
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = playerAdapter
    }

    private fun fillAndOpenTaskRecyclerView(taskList: List<Task>) {
        globalTaskList = taskList
        setContentView(R.layout.task_list)
        recyclerView = findViewById(R.id.tasksRecyclerView)
        taskAdapter = TaskAdapter(taskList)
        taskAdapter.onItemClick = { task ->
            showTaskLayout(task)
        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = taskAdapter

        findViewById<Button>(R.id.backToPlayerButton).setOnClickListener {
            showPlayerLayout(globalPlayer)
        }
    }

    private fun fillAndOpenVideoLinksRecyclerView(videoContentFileList: List<ContentFile>) {
        globalVideoContentFileList = videoContentFileList
        setContentView(R.layout.video_link_list)
        recyclerView = findViewById(R.id.videoLinksRecyclerView)
        videoLinkAdapter = VideoLinkAdapter(videoContentFileList)
//        videoLinkAdapter.onItemClick = { videoLink ->
//            showVideoLinkLayout(videoLink)
//        }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = videoLinkAdapter

        findViewById<Button>(R.id.backToPlayerButton).setOnClickListener {
            showTaskLayout(globalTask)
        }
    }

    private fun showTaskLayout(task: Task) {
        globalTask = task
        setContentView(R.layout.task)

        val taskDescTextMultiLine = findViewById<TextView>(R.id.taskDescTextMultiLine)

        findViewById<TextView>(R.id.taskNameTextView).text = task.name
        taskDescTextMultiLine.text = task.description

        taskDescTextMultiLine.movementMethod =
            ScrollingMovementMethod()

        findViewById<Button>(R.id.backButton).setOnClickListener {
            fillAndOpenTaskRecyclerView(globalTaskList)
        }

        findViewById<Button>(R.id.videoButton).setOnClickListener {
            fillAndOpenVideoLinksRecyclerView(task.videoLinks)
        }

        findViewById<Button>(R.id.reportButton).setOnClickListener {
            openReportTask(task)
        }

    }

    private fun openReportTask(task: Task) {
        setContentView(R.layout.report_task)
        //val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy г.")
        // val taskReport: Task

        findViewById<Button>(R.id.backToTaskButton).setOnClickListener {
            showTaskLayout(task)
        }

        findViewById<ImageButton>(R.id.reportPhotoButton).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                Companion.PICK_IMAGE_FOR_REPORT_TASK
            )

        }

        findViewById<Button>(R.id.reportSendButton).setOnClickListener {
            val taskReportRequest =
                TaskReportRequest(
                    Instant.now().toEpochMilli(),
                    task.id,
                    globalPlayer.id,
                    null, //TODO добавить выбор из календаря, за какой день отчитываемся
                    findViewById<MultiAutoCompleteTextView>(R.id.reportTextMultiAutoCompleteTextView).text.toString(),
                    globalFileMap[Companion.PICK_IMAGE_FOR_REPORT_TASK],
                    null
                )
            sendTaskReport(taskReportRequest)
        }

    }

    private fun sendTaskReport(taskReportRequest: TaskReportRequest) {
        lifecycleScope.launch {
            showLoading("Выгрузка отчета на сервер ...")
            val result = apiService.sendTaskReport(taskReportRequest)
            if (result.isSuccessful) {
                Toast.makeText(applicationContext, "Отчет успешно отправлен", Toast.LENGTH_SHORT).show()
                Log.e("sendTaskReport", "sendTaskReport success")
                globalFileMap.remove(Companion.PICK_IMAGE_FOR_REPORT_TASK)
                showPlayerLayout(globalPlayer)
            } else {
                Toast.makeText(applicationContext, "Ошибка отправки отчета", Toast.LENGTH_SHORT).show()
                Log.e("sendTaskReport", "sendTaskReport failed")
            }
            progressDialog?.dismiss()
        }
    }


    private fun openCoachContext(user: User) {
        setContentView(R.layout.coach)
    }

    private fun openAdminContext(user: User) {
        setContentView(R.layout.admin)
    }

//    private fun getPlayerById(id: Int): Player? {
//
//        lifecycleScope.launch {
//            showLoading("Загрузка данных с сервера ...")
//            val result = apiService.getPlayerById(id)
//            if (result.isSuccessful) {
//                Log.e("getPlayerById", "getPlayerById success: ${result.body()}")
//                globalPlayer = result.body()!!;
//                showPlayerLayout(globalPlayer)
//            } else {
//                Log.e("getPlayerById", "getPlayerById failed: ${result.message()}")
//            }
//            progressDialog?.dismiss()
//        }
//        return globalPlayer
//    }

    @SuppressLint("SimpleDateFormat")
    private fun showPlayerLayout(player: Player) {
        globalPlayer = player

        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy г.")

        setContentView(R.layout.player)

        findViewById<Button>(R.id.playersButton).setOnClickListener {
            lifecycleScope.launch {
                showLoading("Загрузка данных с сервера ...")
                val result = apiService.getTasksByPlayerId(globalPlayer.id)
                if (result.isSuccessful) {
                    Log.e("openCoachTask", "openCoachTask success: ${result.body()}")
                    fillAndOpenTaskRecyclerView(result.body()!!)
                } else {
                    Log.e("openCoachTask", "openCoachTask failed: ${result.message()}")
                }
                progressDialog?.dismiss()
            }
        }

        findViewById<Button>(R.id.backToPlayerListButton).setOnClickListener {
            fillAndOpenPlayerRecyclerView(globalPlayerList)
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

        findViewById<TextView>(R.id.surnameTextView).text = player.surname
        findViewById<TextView>(R.id.nameTextView).text = player.name
        findViewById<TextView>(R.id.birthDateEditTextDate).text =
            simpleDateFormat.format(player.birthDate)
        DownloadImageFromInternet(applicationContext, findViewById(R.id.photoImageView)).execute(
            player.photo
        )
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
}