package com.shashi.shashishivatech.repository

import com.google.gson.JsonObject
import com.shashi.shashishivatech.api.ApplicationsAPI
import com.shashi.shashishivatech.data.models.ApplicationsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApplicationsRepository @Inject constructor(
    private val api: ApplicationsAPI
) : BaseRepository(api) {

    suspend fun getApplications(jsonObject: JsonObject): Flow<ApplicationsResponse> {
        return flow {
            val applicationsData = api.getApplications(
                jsonObject
            )
            emit(applicationsData)

        }.flowOn(Dispatchers.IO)

    }

}