package com.android.jsdplugin

import android.os.Bundle
import android.util.ArrayMap
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.Collections

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.findViewById<Button>(R.id.test).setOnClickListener {
      MainTest.test()
    }

  }



}