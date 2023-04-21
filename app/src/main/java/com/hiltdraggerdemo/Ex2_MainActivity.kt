package com.hiltdraggerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Ex2_MainActivity : AppCompatActivity() {
    private lateinit var tvMsg : TextView
    private val myViewModel: Ex2_MyViewModel by viewModels()
    private lateinit var btnNewScreen : Button
    private lateinit var btnUpdateData : Button
    @Inject
    lateinit var std: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex2_main)
        tvMsg = findViewById(R.id.tvMsg)
        btnNewScreen = findViewById(R.id.btnNewScreen)
        btnUpdateData = findViewById(R.id.btnUpdateData)
        btnUpdateData.setOnClickListener {
            myViewModel.updateLiveData()
        }
        tvMsg.setText(myViewModel.getEmpName())
        myViewModel.getData().observe(this, {
            tvMsg.setText(myViewModel.getEmpName() + it+" ==> Student Name is0 "+std.getName())
        })
        btnNewScreen.setOnClickListener {
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl, MyFragment(), "Test")
                .disallowAddToBackStack()
                .commit();
        }

    }
}