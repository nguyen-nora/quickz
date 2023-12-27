package vn.doitsolutions.quickz.pages.games.component

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.doitsolutions.quickz.MainActivity
import vn.doitsolutions.quickz.ui.theme.QuickZTheme

@Composable
fun ChooseAnswerPage(list: List<String>, onAnswerClick: (String) -> Unit, isFinished: Boolean, onSubmitClick:() -> Unit) {
    var selectedAnswer by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color(0xFFFE8253)) }
    //create a radio list answer with button next or submit have selected color
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F6F6)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //create a radio list answer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            list.forEach { answer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE5E5E5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = if (selectedAnswer == answer) selectedColor else Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            selectedAnswer = answer
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedAnswer == answer,
                        onClick = {
                            selectedAnswer = answer
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Transparent,
                            unselectedColor = Color.Transparent,
                        )
                    )
                    Text(
                        text = answer,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        color = if (selectedAnswer == answer) Color.White else Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                    )
                }
            }
        }
        //create a button next or submit

        Button(
            onClick = {
                if(onSubmitClick != null && onAnswerClick != null){
                    if (isFinished) onSubmitClick() else onAnswerClick(selectedAnswer)
                }

            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28254)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
        ) {
            Text(
                text = if (isFinished) "Submit" else "Next",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickZTheme {
        //
        val radioOptions = listOf("Mango", "Banana", "Apple", "Peach")
        ChooseAnswerPage(radioOptions, {}, false, {})
    }
}