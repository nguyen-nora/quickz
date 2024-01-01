package vn.doitsolutions.quickz.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.doitsolutions.quickz.model.ExamBody
import vn.doitsolutions.quickz.model.ExamQuestionArrayListMoshiAdapter
import vn.doitsolutions.quickz.model.ExamResponseObject
import java.util.concurrent.TimeUnit


private const val baseUrl: String =
    "https://script.google.com/macros/s/AKfycbxQpI4kccRjbEWfC3CI0X66NcoR5CgAedgJddR_JHhfoll-BOycvbAKNGwDJoe8R-nv_w/"

private val moshiExam = Moshi.Builder()
    .add(ExamQuestionArrayListMoshiAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private  val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .callTimeout(2, TimeUnit.MINUTES)
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)

private val retrofitExam = Retrofit.Builder()
    .client(httpClient.build())
    .addConverterFactory(MoshiConverterFactory.create(moshiExam))
    .baseUrl(baseUrl)
    .build()

interface ExamApiService{
    @Headers("Accept: application/json")
    @POST("exec")
    suspend  fun create(@Body examBody: ExamBody): ExamResponseObject?
}

object  ExamApi{
    val retrofitService: ExamApiService by lazy { retrofitExam.create(ExamApiService::class.java) }
}
