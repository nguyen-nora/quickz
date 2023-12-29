package vn.doitsolutions.quickz.pages.games.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import vn.doitsolutions.quickz.model.ExamBody
import vn.doitsolutions.quickz.model.ExamBodyData
import vn.doitsolutions.quickz.model.ExamData
import vn.doitsolutions.quickz.model.ExamQuestion
import vn.doitsolutions.quickz.model.ExamResponseObject
import vn.doitsolutions.quickz.model.SubmitExamBody
import vn.doitsolutions.quickz.model.UpdateQuestionBody
import vn.doitsolutions.quickz.model.UpdateQuestionData
import vn.doitsolutions.quickz.network.ExamApi
import vn.doitsolutions.quickz.network.SubmitApi
import vn.doitsolutions.quickz.network.UpdateApi
import vn.doitsolutions.quickz.network.UpdateApiService


class GameViewModel(var examData: ExamData?) : ViewModel() {

    private var currentQuestion: ExamQuestion? = null
    var currentQuestionIndex: Int = 0
    var duration = 600000
    var total = 0

    var currentScore: Int = 0
    var isFinished: Boolean = false

    init {
        total = examData?.list?.size!!
        currentQuestion = examData?.list!![currentQuestionIndex]
    }

    fun getCurrentQuestion(): ExamQuestion? {
        currentQuestion = examData?.list!![currentQuestionIndex]
        return currentQuestion
    }


    fun selectedAnswer(an: String) {

        if (currentQuestionIndex == total - 1) {
            isFinished = true
        } else {
            examData?.list!![currentQuestionIndex].userrep = an
            examData?.list!![currentQuestionIndex].correct =
                an == examData?.list!![currentQuestionIndex].question?.correctAnswer
            if (an == examData?.list!![currentQuestionIndex].question?.correctAnswer) {
                currentScore++
            }
            currentQuestionIndex++;
        }

//        viewModelScope.launch {
//
//            var _id = examData?.list!![currentQuestionIndex]._id
//            var qesid = examData?.list!![currentQuestionIndex].question?._id
//            var userrep = examData?.list!![currentQuestionIndex].userrep
//            var correct = examData?.list!![currentQuestionIndex].correct
//            requestUpdate(_id!!, qesid!!, userrep!!, correct!!).collect{response -> println(response) }
//        }
    }

    suspend fun requestUpdate(_id: String, qesid: String, userrep: String, correct: Boolean) =
        flow {
            var userId = examData?.user?._id
            try {
                emit("loading")
                var updateQuestionBody: UpdateQuestionBody? = UpdateApi.retrofitService.update(
                    UpdateQuestionBody(
                        route = "updateExamQuestion",
                        data = UpdateQuestionData(
                            userid = userId!!,
                            _id = _id,
                            questionid = qesid,
                            userrep = userrep,
                            correct = correct
                        )
                    )
                )
                if (updateQuestionBody?.data != null) {
                    emit("success")
                } else {
                    emit("fail")
                }
            } catch (e: Exception) {
                println("CERA: " + e.message)
                emit("fail")
            }
        }

    fun  submit() {
        viewModelScope.launch {
            requestSubmit().collect{response -> println(response) }
        }
    }

    suspend fun requestSubmit() = flow {
        try {
            emit("loading")
            var examResponseObject: ExamResponseObject? = SubmitApi.retrofitService.submit(
                SubmitExamBody(
                    route = "submitExam",
                    data = examData!!
                )
            )
            if (examResponseObject?.data != null) {
                emit("success")
            } else {
                emit("fail")
            }
        } catch (e: Exception) {
            println("CERA: " + e.message)
            emit("fail")
        }
    }

//    "userid": "3edcc648-0f42-4711-9378-e8ff38674c23",
//    "_id": "da6adb6c-9bfa-4cee-a008-c163af83e778",
//    "questionid": "8a733784-7980-46f4-9604-f7e244f9d089",
//    "userrep": "test4444444",
//    "correct": true


}