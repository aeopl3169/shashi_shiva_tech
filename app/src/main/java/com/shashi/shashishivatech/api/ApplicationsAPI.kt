package com.shashi.shashishivatech.api

import com.google.gson.JsonObject
import com.shashi.shashishivatech.data.models.ApplicationsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApplicationsAPI {

    @POST("apps/list")
    suspend fun getApplications(
        @Body jsonObject: JsonObject,
    ): ApplicationsResponse

}