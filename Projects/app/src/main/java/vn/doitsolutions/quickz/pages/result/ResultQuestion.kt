package vn.doitsolutions.quickz.pages.result

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import vn.doitsolutions.quickz.model.Question
import vn.doitsolutions.quickz.model.Questions
import vn.doitsolutions.quickz.pages.auth.HomePage
import vn.doitsolutions.quickz.pages.games.BackButton
import vn.doitsolutions.quickz.ui.theme.QuickZTheme

class ResultQuestion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = intent
        val questions: Questions? = i.getParcelableExtra<Parcelable>("final_list") as Questions?
        var total = questions!!.data.size
        setContent {
            QuickZTheme {
                ResultQuestionPage(
                    onBackClick = { startActivity(Intent(this, HomePage::class.java)) },
                    questions, total = total
                )
            }
        }
    }
}

@Composable
fun ResultQuestionPage(
    onBackClick: () -> Unit,
    questions: Questions,
    total: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5))
    ) {
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
                    text = "My Answer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            var list = ArrayList<Question>()
            list = questions!!.data
            var d = 1
            for (i in list) {
                navQuestion(
                    questid = d++,
                    total = total,
                    question = i
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun navQuestion(
    questid: Int,
    total: Int,
    question: Question
) {
    //QuestionCard
    Card(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFFFFFF))
        ) {
            //Question
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${questid}/${total}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFE8253),
                )
                if (question.userAnswer != question.correctAnswer) {
                    Text(
                        text = " - Wrong",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFE8253),
                        textAlign = TextAlign.End,
                    )
                } else {
                    Text(
                        text = " - Correct",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFE8253),
                        textAlign = TextAlign.End,
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                text = "${question.question}",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF1B0330)
            )
            Text(
                text = "${question.userAnswer}",
                fontSize = 14.sp,
                lineHeight = 18.2.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF1B0330),
            )
            if (question.userAnswer != question.correctAnswer) {
                Text(
                    text = "Correct Answer: ${question.correctAnswer}",
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF1B0330),
                )
            }
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    QuickZTheme {
        //ResultQuestionPage(total = 10)
        //navQuestion()
    }
}
