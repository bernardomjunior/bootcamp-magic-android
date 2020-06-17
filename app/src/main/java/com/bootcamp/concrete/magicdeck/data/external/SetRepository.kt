package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Set
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetRepository {

    private val retrofit: ApiService by inject(ApiService::class.java)
    private val SETS = "sets"

    fun listSets(
        onSuccess: (List<Set>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit
    ) {
        retrofit.listSets().enqueue(object : Callback<Map<String, List<Set>>> {
            override fun onResponse(
                call: Call<Map<String, List<Set>>>,
                response: Response<Map<String, List<Set>>>
            ) {
                if (response.isSuccessful) {
                    val dict = response.body() as Map<String, List<Set>>
                    dict[SETS]?.let(onSuccess)
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Map<String, List<Set>>>, t: Throwable) {
                onFailure()
            }
        })
    }

}