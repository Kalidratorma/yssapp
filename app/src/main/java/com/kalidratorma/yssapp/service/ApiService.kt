package com.kalidratorma.yssapp.service

import com.kalidratorma.yssapp.model.Coach
import com.kalidratorma.yssapp.model.Parent
import com.kalidratorma.yssapp.model.Player
import com.kalidratorma.yssapp.model.Task
import com.kalidratorma.yssapp.model.security.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/player/{id}")
    suspend fun getPlayerById(@Path("id") id:Int): Response<Player>

    @GET("/api/user/{username}")
    suspend fun getUserByName(@Path("username") username:String): Response<User>

    @GET("/api/parent/{parentId}")
    suspend fun getParentById(@Path("parentId") parentId:Int): Response<Parent>

    @GET("/api/coach/{coachId}")
    suspend fun getCoachById(@Path("coachId") coachId:Int): Response<Coach>

    @GET("/api/parent/byUser/{userId}")
    suspend fun getParentByUserId(@Path("userId") userId:Int): Response<Parent>

    @GET("/api/coach/byUser/{userId}")
    suspend fun getCoachByUserId(@Path("userId") userId:Int): Response<Coach>

    @GET("/api/task/{taskId}")
    suspend fun getTaskById(@Path("taskId") taskId:Int): Response<Task>

    @GET("/api/player/byParent/{parentId}")
    suspend fun getPlayersByParentId(@Path("parentId") parentId:Int): Response<List<Player>>

    @GET("/api/task/byPlayer/{playerId}")
    suspend fun getTasksByPlayerId(@Path("playerId") playerId:Int): Response<List<Task>>

}