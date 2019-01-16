package com.example.asus.aplikasifootballclub.utils

import com.example.asus.aplikasifootballclub.model.gsonMatch
import com.example.asus.aplikasifootballclub.model.gsonMatchDetail
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ApiRepositoryTest {

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    @Mock
    private
    lateinit var dataPrevMatch:gsonMatch

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testAPIintoDataset() {
        val teams: MutableList<gsonMatchDetail> = mutableListOf()
        val dataMatch = gsonMatch(teams)
        Mockito.`when`(gson.fromJson(apiRepository.doRequest("https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=133602"),
                gsonMatch::class.java)).thenReturn(dataMatch)
        dataPrevMatch = dataMatch // dataset ready to use
    }


    @Test
    fun testAPI() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }

}