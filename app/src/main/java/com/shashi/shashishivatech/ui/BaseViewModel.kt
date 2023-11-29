package com.shashi.shashishivatech.ui

import androidx.lifecycle.ViewModel
import com.shashi.shashishivatech.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {
}