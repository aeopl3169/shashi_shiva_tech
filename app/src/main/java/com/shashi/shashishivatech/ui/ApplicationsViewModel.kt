package com.shashi.shashishivatech.ui

import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.shashi.shashishivatech.data.models.ApplicationsResponse
import com.shashi.shashishivatech.data.networks.NoConnectivityException
import com.shashi.shashishivatech.repository.ApplicationsRepository
import com.shashi.shashishivatech.data.networks.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ApplicationsViewModel
@Inject constructor(
    private val repository: ApplicationsRepository
) : BaseViewModel(repository) {

    private val _getApplicationFlow: MutableStateFlow<Resource<ApplicationsResponse>> =
        MutableStateFlow(Resource.Loading)
    val getApplicationsFlow: StateFlow<Resource<ApplicationsResponse>> = _getApplicationFlow


    fun getApplications(kid_id: String) = viewModelScope.launch {
        _getApplicationFlow.value = Resource.Loading

        val jsonObject = JsonObject()
        jsonObject.addProperty("kid_id", kid_id)

        repository.getApplications(jsonObject)
            .catch { e ->
                if (e is NoConnectivityException) {
                    _getApplicationFlow.value = Resource.Error(e.message)

                } else if (e is HttpException) {
                    val exception: HttpException = e
                    when (exception.code()) {
                        401 -> {
                            _getApplicationFlow.value = Resource.Error("Invalid! Please try again after sometime!")
                        }

                        500 -> {
                            _getApplicationFlow.value =
                                Resource.Error("Unable to get data. Please try again after sometime!")
                        }

                        else -> {
                            _getApplicationFlow.value = Resource.Error("Error")
                        }
                    }
                }
            }.collect { data ->
                _getApplicationFlow.value = Resource.Success(data)
            }
    }

}