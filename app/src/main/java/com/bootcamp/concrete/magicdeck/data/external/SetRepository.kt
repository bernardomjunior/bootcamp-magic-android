package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Set

class SetRepository {

    private val retrofit = ApiNetwork.retrofit
    private val SETS = "sets"

    suspend fun listSets(
        onSuccess: (List<Set>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit
    ) {
        val response= retrofit.listSets()
        response[SETS]?.let{
            if (it.isNotEmpty()) onSuccess(it)
        }



//        retrofit.listSets().enqueue(object : Callback<Map<String, List<Set>>> {
//            override fun onResponse(
//                call: Call<Map<String, List<Set>>>,
//                response: Response<Map<String, List<Set>>>
//            ) {
//                if (response.isSuccessful) {
//                    val dict = response.body() as Map<String, List<Set>>
//                    dict[SETS]?.let(onSuccess)
//                } else {
//                    onError()
//                }
//            }
//
//            override fun onFailure(call: Call<Map<String, List<Set>>>, t: Throwable) {
//                onFailure()
//            }
//        })
    }

}