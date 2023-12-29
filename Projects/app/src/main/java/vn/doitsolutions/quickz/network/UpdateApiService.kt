package vn.doitsolutions.quickz.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import vn.doitsolutions.quickz.model.ExamResponseObject
import vn.doitsolutions.quickz.model.UpdateQuestionBody
import java.util.concurrent.TimeUnit


private const val baseUrl: String =
    "https://script.google.com/macros/s/AKfycbzyFHn3fJTZtA81kUTR-3v_KD3mIWYinJg3--33Fh-G3scXAy6ka6GezZylZXdAQ1UO4w/"

private val moshiUpdate = Moshi.Builder()
//    .add(ExamQuestionArrayListMoshiAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private  val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    .callTimeout(2, TimeUnit.MINUTES)
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)

private val retrofitUpdate = Retrofit.Builder()
    .client(httpClient.build())
    .addConverterFactory(MoshiConverterFactory.create(moshiUpdate))
    .baseUrl(baseUrl)
    .build()

interface UpdateApiService{
    @Headers("Accept: application/json")
    @POST("exec")
    suspend  fun update(@Body body: UpdateQuestionBody): UpdateQuestionBody?
}

object  UpdateApi{
    val retrofitService: UpdateApiService by lazy { retrofitUpdate.create(UpdateApiService::class.java) }
}
