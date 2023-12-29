package vn.doitsolutions.quickz.model

import com.squareup.moshi.Json

data class ExamResponseObject (
    @Json( name = "status") val status: String?,
    @Json( name = "msg") val msg: String?,
    @Json( name = "data") val data: ExamData?)
