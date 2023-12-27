package vn.doitsolutions.quickz.model

import com.squareup.moshi.Json

data class ExamBody (
    @Json( name = "route") var route: String = "createExam",
    @Json( name = "data") var data: ExamBodyData,
)

data class ExamBodyData(
    @Json(name= "username") var username: String,
    @Json(name = "len") var len: Int,
)