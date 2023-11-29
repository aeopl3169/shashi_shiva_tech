package com.shashi.shashishivatech.repository

import com.shashi.shashishivatech.api.ApplicationsAPI
import com.shashi.shashishivatech.data.networks.SafeApiCall


abstract class BaseRepository(private val api: ApplicationsAPI) : SafeApiCall {
}