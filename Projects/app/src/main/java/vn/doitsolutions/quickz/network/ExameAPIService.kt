package vn.doitsolutions.quickz.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.doitsolutions.quickz.model.ExamBody
import vn.doitsolutions.quickz.model.ExamResponseObject


private const val baseUrl: String =
    "https://script.google.com/macros/s/AKfycbzyFHn3fJTZtA81kUTR-3v_KD3mIWYinJg3--33Fh-G3scXAy6ka6GezZylZXdAQ1UO4w/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(baseUrl)
    .build()

interface ExamApiService{
    @Headers("Accept: application/json")
    @POST("exec")
    suspend  fun create(@Body examBody: ExamBody): ExamResponseObject
}

object  ExamApi{
    val retrofitService: ExamApiService by lazy { retrofit.create(ExamApiService::class.java) }
}
