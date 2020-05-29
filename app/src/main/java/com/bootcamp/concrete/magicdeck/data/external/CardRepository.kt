package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardRepository {

    private val retrofit = ApiNetwork.retrofit
    private val CARDS = "cards"

    fun listCards(
        set: String,
        type: String,
        page: Int,
        onSuccess: (List<Card>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit

    ){
        retrofit.listCards(set, type, page).enqueue(
            object : Callback<Map<String,List<Card>>>{
                override fun onResponse(call: Call<Map<String,List<Card>>>, response: Response<Map<String,List<Card>>>) {
                    if (response.isSuccessful){
                        val map = response.body() as Map<String,List<Card>>
                        map[CARDS]?.let(onSuccess)
                    }else{
                        onError()
                    }
                }

                override fun onFailure(call: Call<Map<String,List<Card>>>, t: Throwable) {
                    onFailure()
                }
            }
        )
    }

}