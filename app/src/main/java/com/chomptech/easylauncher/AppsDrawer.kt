package com.chomptech.easylauncher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import RAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.GridLayoutManager





class AppsDrawer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_drawer)

        val recyclerView = findViewById(R.id.appsList) as RecyclerView
        val radapter = RAdapter(this)
        recyclerView.adapter = radapter
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 6)
    }
}
