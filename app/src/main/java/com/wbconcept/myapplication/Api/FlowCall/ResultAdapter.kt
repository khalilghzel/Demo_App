package com.wbconcept.myapplication.Api.FlowCall


 import com.wbconcept.myapplication.Api.FlowCall.ResultCall
 import com.wbconcept.myapplication.Util.ApiResponse
 import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ResultCall(call)
    override fun responseType(): Type = type
}