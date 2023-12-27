package vn.doitsolutions.quickz.pages.result

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.doitsolutions.quickz.R
import vn.doitsolutions.quickz.model.ExamData
import vn.doitsolutions.quickz.model.ExamQuestion
import vn.doitsolutions.quickz.model.Question
//import vn.doitsolutions.quickz.model.QuestionList
import vn.doitsolutions.quickz.pages.auth.HomePage
import vn.doitsolutions.quickz.pages.games.viewmodel.GameViewModel
import vn.doitsolutions.quickz.ui.theme.QuickZTheme

class Finished : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        val data = i.getParcelableArrayListExtra<Question>("examDataFinished") as ExamData?
        var gameViewModel = GameViewModel(data)
        var score = getScore(gameViewModel.examData!!.list)
        var total = gameViewModel.total
        setContent {
            QuickZTheme {
                FinishedPage(score = score, total = total,
                    onBackClick = {startActivity(Intent(this, HomePage::class.java))},
                    onSubmitClick = {
                        var examDataFinal = gameViewModel.examData
                    val i = Intent(this, ResultQuestion::class.java)
                    i.putExtra("examDataFinal", examDataFinal)
                    startActivity(i)
                })
            }
        }
    }
}
fun getScore(list: ArrayList<ExamQuestion>): Int {
    var score = 0
    for (i in list) {
        if (i.correct!!) {
            score++
        }
    }
    return score
}


@Composable
fun FinishedPage(
    score: Int,
    total: Int,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F6F6))
            .padding(12.dp)
    ) {
        //ScoreCard
        Card(
            modifier = Modifier
                .background(color = Color(0xFF9B7BF8), shape = RoundedCornerShape(size = 16.dp))
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 28.dp)
        ) {
            Text(
                text = "Congratulation!",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF9B7BF8))
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Your score is ${score} / ${total}",
                fontSize = 22.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF9B7BF8))
            )
            CheckAnswerButton {
                onSubmitClick()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF))
                    .padding(top = 20.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ComponentResult()
                ComponentResult()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFFFFFF))
                    .padding(top = 20.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ComponentResult()
                ComponentResult()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AcceptButton(onBackClick)
    }
}

@Composable
fun CheckAnswerButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28254)),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF9B7BF8))
            .padding(top = 12.dp),
        onClick = { onClick() }) {
        Text("Check Your Answer", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ComponentResult(int: Int = 0) {
    Card(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
            .padding(36.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color(0xFFFFFFFF)),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.tickcircle),
                contentDescription = "image description",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(22.dp)
            )
            Text(
                text = "14 Question",
                fontSize = 16.sp,
                lineHeight = 18.2.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            )
            Text(
                text = "Correct Answer",
                fontSize = 14.sp,
                lineHeight = 18.2.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        }
    }
}

@Composable
fun AcceptButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28254)),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(color = Color(0xFFF6F6F6))
            .padding(bottom = 100.dp),
        onClick = { onClick() }) {
        Text("Back to homepage", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickZTheme {
        //FinishedPage(1, 1)
        //ComponentResult()
    }
}