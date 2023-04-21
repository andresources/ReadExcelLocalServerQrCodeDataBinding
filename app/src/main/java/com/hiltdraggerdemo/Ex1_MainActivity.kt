package com.hiltdraggerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Ex1_MainActivity : AppCompatActivity() {
    @Inject
    lateinit var emp : Employee
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(applicationContext,emp.getEName(),Toast.LENGTH_LONG).show()

    }
}