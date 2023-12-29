package vn.doitsolutions.quickz.model

import com.squareup.moshi.Json

data class UpdateQuestionBody (
    @Json(name = "route") var route: String = "updateExamQuestion",
    @Json(name = "data") var data: UpdateQuestionData?
    )


data class UpdateQuestionData(
    @Json(name = "_id") var _id: String,
    @Json(name = "userid") var userid: String,
    @Json(name = "questionid") var questionid: String,
    @Json(name = "userrep") var userrep: String,
    @Json(name = "correct") var correct: Boolean
)
