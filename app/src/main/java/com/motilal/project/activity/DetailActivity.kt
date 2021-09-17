package com.motilal.project.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.motilal.project.R

class DetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var tvDescription: TextView = findViewById(R.id.tvDescription)
        var tvStar: TextView = findViewById(R.id.tvStar)
        var tvBranch: TextView = findViewById(R.id.tvBranch)
        var tvCloneUrl: TextView = findViewById(R.id.tvCloneUrl)
        val i = intent
        val desc = i.getStringExtra("description")
        val star = i.getStringExtra("star")
        val cloneUrl = i.getStringExtra("cloneUrl")
        val branch = i.getStringExtra("branch")
        tvDescription.text = desc
        tvStar.text = star
        tvBranch.text = branch
        tvCloneUrl.text = cloneUrl
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}