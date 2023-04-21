package com.hiltdraggerdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Ex2_MyViewModel @Inject constructor(private val e: Employee) :ViewModel() {
    private val myliveData = MutableLiveData<String>()
    init {
        myliveData.value = "This is mutalbe String"
    }

    fun getData() = myliveData

    fun updateLiveData(){
        myliveData.value = "after button click"
    }

    fun getEmpName() = e.getEName()
}