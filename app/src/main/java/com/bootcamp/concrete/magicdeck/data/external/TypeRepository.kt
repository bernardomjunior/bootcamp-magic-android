package com.bootcamp.concrete.magicdeck.data.external

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TypeRepository {

    private val retrofit = ApiNetwork.retrofit
    private val TYPES = "types"

    fun listTypes(
        onSuccess: (List<String>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit
    ) {
        retrofit.listTypes().enqueue(object : Callback<Map<String, List<String>>> {
            override fun onResponse(
                call: Call<Map<String, List<String>>>,
                response: Response<Map<String, List<String>>>
            ) {
                if (response.isSuccessful) {
                    val dict = response.body() as Map<String, List<String>>
                    dict[TYPES]?.let(onSuccess)

                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                onFailure()
            }
        })
    }

}