package com.example.asus.aplikasifootballclub.pageteams

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.asus.aplikasifootballclub.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_players_detail.*

class TeamsDetailPlayer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        lblheight_player.text = intent.getStringExtra("strHeight")
        lblweight_player.text = intent.getStringExtra("strWeight")
        lblDesc_player.text = intent.getStringExtra("strDescriptionEN")
        Picasso.get().load(intent.getStringExtra("strFanart1")).into(img_player_fanart)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
