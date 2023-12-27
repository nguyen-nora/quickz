package vn.doitsolutions.quickz.pages.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import vn.doitsolutions.quickz.model.ExamBody
import vn.doitsolutions.quickz.model.ExamBodyData
import vn.doitsolutions.quickz.model.ExamResponseObject
import vn.doitsolutions.quickz.network.ExamApi

class HomeViewModel : ViewModel(){
    var status  = mutableStateOf<String>("init")
    var examResponseObject : ExamResponseObject? = null

    init {

    }

    fun  createExam(username: String){
     viewModelScope.launch {
         status.value = "loading"
         requestCreateExam(username).collect{response -> status.value = response}
         if(status.value == "success"){
             status.value = "success"
         }else{
             status.value = "fail"
         }
     }
    }

    suspend fun requestCreateExam(username: String) = flow{
        try {
            emit("loading")
            examResponseObject = ExamApi.retrofitService.create(
                ExamBody(
                    route = "createExam",
                    data = ExamBodyData(username = username, len = 10)
                )
            )
            if(examResponseObject?.data != null) {
                emit("success")
            }else{
                emit("fail")
            }
            emit("success")
        } catch (e: Exception) {
            println("CERA: " + e.message)
            emit("fail")
        }
    }
}