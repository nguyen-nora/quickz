package vn.doitsolutions.quickz.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.doitsolutions.quickz.model.ExamData
import vn.doitsolutions.quickz.model.ExamQuestionArrayListMoshiAdapter
import vn.doitsolutions.quickz.model.ExamResponseObject
import vn.doitsolutions.quickz.model.SubmitExamBody
import vn.doitsolutions.quickz.model.UpdateQuestionBody
import java.util.concurrent.TimeUnit


private const val baseUrl: String =
    "https://script.google.com/macros/s/AKfycbzyFHn3fJTZtA81kUTR-3v_KD3mIWYinJg3--33Fh-G3scXAy6ka6GezZylZXdAQ1UO4w/"

private val moshiSubmit = Moshi.Builder()
//    .add(ExamQuestionArrayListMoshiAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private  val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .callTimeout(2, TimeUnit.MINUTES)
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)

private val retrofitSubmit = Retrofit.Builder()
    .client(httpClient.build())
    .addConverterFactory(MoshiConverterFactory.create(moshiSubmit))
    .baseUrl(baseUrl)
    .build()

interface SubmitApiService{
    @Headers("Accept: application/json")
    @POST("exec")
    suspend  fun submit(@Body body: SubmitExamBody): ExamResponseObject?
}

object  SubmitApi{
    val retrofitService: SubmitApiService by lazy { retrofitSubmit.create(SubmitApiService::class.java) }
}
