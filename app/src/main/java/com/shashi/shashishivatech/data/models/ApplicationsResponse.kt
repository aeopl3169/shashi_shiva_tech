package com.shashi.shashishivatech.data.models


import androidx.annotation.Keep

@Keep
data class ApplicationsResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
) {
    @Keep
    data class Data(
        val app_list: List<App>,
        val usage_access: Int
    ) {
        @Keep
        data class App(
            val app_icon: String,
            val app_id: Int,
            val app_name: String,
            val app_package_name: String,
            val fk_kid_id: Int,
            val kid_profile_image: String,
            val status: String
        )
    }
}