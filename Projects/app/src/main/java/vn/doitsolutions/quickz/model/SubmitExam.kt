package vn.doitsolutions.quickz.model

import com.squareup.moshi.Json

data class SubmitExamBody(
    @Json(name = "router") val route: String = "submitExam",
    @Json(name = "data") val data: ExamData

)