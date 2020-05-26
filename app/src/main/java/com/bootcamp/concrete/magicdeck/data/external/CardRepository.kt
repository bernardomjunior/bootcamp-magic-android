package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CardRepository {

    private val retrofit = ApiNetwork.retrofit

    fun listCards(
        onSuccess: (List<Card>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit,
        set: String,
        type: String,
        page: Int
    ){
        retrofit.listCards(set, type, page).enqueue(
            object : Callback<List<Card>>{
                override fun onResponse(call: Call<List<Card>>, response: Response<List<Card>>) {
                    if (response.isSuccessful){
                        onSuccess(response.body() as List<Card>)
                    }else{
                        onError()
                    }
                }

                override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                    onFailure()
                }
            }
        )
    }

}