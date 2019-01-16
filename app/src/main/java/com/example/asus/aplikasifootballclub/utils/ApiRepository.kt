package com.example.asus.aplikasifootballclub.utils

import java.net.URL

class ApiRepository {
    fun doRequest(url:String): String {
        return URL(url).readText()
    }
}