@file:OptIn(ExperimentalMaterial3Api::class)

package vn.doitsolutions.quickz.pages.games

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import org.json.JSONArray
import org.json.JSONObject
import vn.doitsolutions.quickz.model.FakeQuestions
import vn.doitsolutions.quickz.model.Question
import vn.doitsolutions.quickz.model.Questions
import vn.doitsolutions.quickz.network.ConnectTcp
import vn.doitsolutions.quickz.pages.auth.HomePage
import vn.doitsolutions.quickz.pages.games.component.ChooseAnswerPage
import vn.doitsolutions.quickz.pages.games.component.QuestionCardPage
import vn.doitsolutions.quickz.pages.games.viewmodel.GameViewModel
import vn.doitsolutions.quickz.pages.result.Finished
import vn.doitsolutions.quickz.ui.theme.QuickZTheme


@Suppress("UNCHECKED_CAST")
class TypeMultiChoice : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //test Socket
        val jsonFiles = assets.list("json")
        val questionList = ArrayList<Question>()
        jsonFiles?.forEach { fileName ->
            val jsonString = assets.open("json/$fileName").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                var incorrect_answers = jsonObject.getJSONArray("incorrect_answers").toArrayList()
                var correct_answer = jsonObject.getJSONArray("correct_answers").toArrayList()
                var all_answer = incorrect_answers
                all_answer.addAll(correct_answer)
                val question = Question(
                    question = jsonObject.getString("question"),
                    correctAnswer = correct_answer.get(0),
                    answers =  all_answer,
                    userAnswer = null
                )
                questionList.add(question)
            }

        }



        var gameViewModel = GameViewModel(Questions(questionList, 10))
        setContent {
            QuickZTheme {
                GamesPage(
                    gameViewModel = gameViewModel,
                    onBackClick = {startActivity(Intent(this, HomePage::class.java))},
                    onSubmitClick = {
                    val i = Intent(this, Finished::class.java)
                    i.putExtra("question_list", it)
                    startActivity(i)
                })
            }
        }
    }
}

fun JSONArray.toArrayList(): ArrayList<String> {
    val list = arrayListOf<String>()
    for (i in 0 until this.length()) {
        list.add(this.getString(i))
    }

    return list
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GamesPage(
    gameViewModel: GameViewModel = viewModel(),
    onBackClick: () -> Unit,
    onSubmitClick: (Questions) -> Unit,
) {
    var culIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F6F6)),
    ) {
        ConnectTcp()
        timerCard(gameViewModel.duration, onBackClick)
        Spacer(modifier = Modifier.height(16.dp))
        //using for loop to next question
        QuestionCardPage(
            question = gameViewModel.getCurrentQuestion().question!!,
            questionNumber = culIndex + 1,
            totalQuestion = gameViewModel.total
        )
        Spacer(modifier = Modifier.height(16.dp))
        //using for loop to next question

        ChooseAnswerPage(
            list = gameViewModel.getCurrentQuestion().answers!!,
            onAnswerClick = {
                gameViewModel.selectedAnswer(it)
                if (culIndex == gameViewModel.total - 1) {
                    culIndex = gameViewModel.total
                } else {
                    culIndex++
                }
            },
            isFinished = gameViewModel.isFinished,
            onSubmitClick = {
                onSubmitClick(gameViewModel.questions)
            }
        )
    }
}

@Composable
fun timerCard(duration: Int, onBackClick: () -> Unit) {
    Card(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(color = Color(0xFFFFFFFF))
        ) {
            BackButton(onBackClick)
            Text(
                text = "Playing games",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .background(color = Color(0xFFFFFFFF))
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "05:42",
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.Start
            )
            ProgressBar()
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(alignment = Alignment.CenterVertically),
                text = "10:00",
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .size(24.dp)
            .background(color = Color(0xFFFFFFFF), RoundedCornerShape(12.dp)),
        onClick = { onClick() },
    ) {
        Icon(
            painter = rememberAsyncImagePainter("https://img.icons8.com/fluency-systems-regular/2x/back.png"),
            contentDescription = "Back",
            )
    }
}

@Composable
fun ProgressBar() {
    Column() {
        LinearProgressIndicator(
            modifier = Modifier
                .padding(start = 12.dp, top = 2.dp, bottom = 12.dp)
                .height(12.dp)
                .size(280.dp),
            color = Color(0xFF9B7BF8),
            trackColor = Color(0xFFF0F0F0),
            progress = 0.5f,
        )
    }
}

@Composable
fun PlayContent() {
    Card(
        modifier = Modifier
            .width(186.dp)
            .padding(bottom = 26.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Play", fontSize = 16.sp, fontWeight = FontWeight(600))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamesPagePreview() {
    val viewModel = GameViewModel(FakeQuestions().generate().getQuestions())
    QuickZTheme {
        //GamesPage(gameViewModel = viewModel, {})
    }
}
