package com.hiltdraggerdemo

import javax.inject.Inject

class Employee @Inject constructor() {
    fun getEName() = "Kavya"
}