package vn.doitsolutions.quickz.pages.games.viewmodel

import androidx.lifecycle.ViewModel
import vn.doitsolutions.quickz.model.ExamData
import vn.doitsolutions.quickz.model.ExamQuestion


class GameViewModel(var examData: ExamData?) : ViewModel() {

    private var currentQuestion: ExamQuestion? = null
    var currentQuestionIndex: Int = 0
    var duration = 600
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
            examData?.list!![currentQuestionIndex].correct = an == examData?.list!![currentQuestionIndex].question?.correctAnswer
            if(an == examData?.list!![currentQuestionIndex].question?.correctAnswer ){
                currentScore++
            }
            currentQuestionIndex++;
        }

    }
}