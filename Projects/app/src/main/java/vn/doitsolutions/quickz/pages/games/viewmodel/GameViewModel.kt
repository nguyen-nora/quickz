package vn.doitsolutions.quickz.pages.games.viewmodel

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.doitsolutions.quickz.model.FakeQuestions
import vn.doitsolutions.quickz.model.Question
import vn.doitsolutions.quickz.model.Questions
import javax.inject.Inject
import androidx.lifecycle.*


class GameViewModel(var questions: Questions) : ViewModel() {
//    var questions: Questions = FakeQuestions().generate().getQuestions()
    private var currentQuestion: Question = questions.data[0]
    var currentQuestionIndex: Int = 0
    var duration = questions.duration
    var total = questions.data.size

    var currentScore: Int = 0
    var isFinished: Boolean = false

    fun getCurrentQuestion(): Question {
        currentQuestion = questions.data[currentQuestionIndex]
        return currentQuestion
    }


    fun selectedAnswer(an: String) {
        if (currentQuestionIndex == total - 1) {
            isFinished = true
        } else {
            questions.data[currentQuestionIndex].userAnswer = an
            if(an == questions.data[currentQuestionIndex].correctAnswer){
                currentScore++
            }
            currentQuestionIndex++;
        }

    }
}