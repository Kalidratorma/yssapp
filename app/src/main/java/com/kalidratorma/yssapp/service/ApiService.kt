package com.kalidratorma.yssapp.service

import com.kalidratorma.yssapp.model.Coach
import com.kalidratorma.yssapp.model.ContentFile
import com.kalidratorma.yssapp.model.Parent
import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.model.Task
import com.kalidratorma.yssapp.model.TaskReport
import com.kalidratorma.yssapp.model.entity.request.TaskReportRequest
import com.kalidratorma.yssapp.model.security.user.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("player/{id}")
    suspend fun getPlayerById(@Path("id") id:Int): Response<Player>

    @GET("user/{username}")
    suspend fun getUserByName(@Path("username") username:String): Response<User>

    @GET("parent/{parentId}")
    suspend fun getParentById(@Path("parentId") parentId:Int): Response<Parent>

    @GET("coach/{coachId}")
    suspend fun getCoachById(@Path("coachId") coachId:Int): Response<Coach>

    @GET("parent/byUser/{userId}")
    suspend fun getParentByUserId(@Path("userId") userId:Int): Response<Parent>

    @GET("coach/byUser/{userId}")
    suspend fun getCoachByUserId(@Path("userId") userId:Int): Response<Coach>

    @GET("task/{taskId}")
    suspend fun getTaskById(@Path("taskId") taskId:Int): Response<Task>

    @GET("player/byParent/{parentId}")
    suspend fun getPlayersByParentId(@Path("parentId") parentId:Int): Response<List<Player>>

    @GET("task/byPlayer/{playerId}")
    suspend fun getTasksByPlayerId(@Path("playerId") playerId:Int): Response<List<Task>>

    @Multipart
    @POST("file")
    suspend fun uploadFile(@Part vararg file: MultipartBody.Part): Response<List<ContentFile>>

    @POST("taskReport")
    suspend fun sendTaskReport(@Body taskReportRequest: TaskReportRequest): Response<Unit>
}