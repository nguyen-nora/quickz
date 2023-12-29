@file:OptIn(ExperimentalMaterial3Api::class)

package vn.doitsolutions.quickz.pages.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Slider
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import vn.doitsolutions.quickz.R
import vn.doitsolutions.quickz.R.drawable.*
import vn.doitsolutions.quickz.model.ExamData
import vn.doitsolutions.quickz.model.User
import vn.doitsolutions.quickz.navigation.NavigationItem
import vn.doitsolutions.quickz.pages.games.TypeMultiChoice
import vn.doitsolutions.quickz.pages.home.HomeViewModel
import vn.doitsolutions.quickz.ui.theme.QuickZTheme

class HomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var i = intent
        var user = i.getParcelableExtra<Parcelable>("userObject") as User?
        setContent {
            QuickZTheme() {
                HomeView(user = user,
                onPlayGames = {
                    val i = Intent(this, TypeMultiChoice::class.java)
                    i.putExtra("examData", it)
                    startActivity(i)
                })
            }
        }
    }
}

@Composable
fun HomeView(
    user: User?,
    homeViewModel: HomeViewModel = viewModel(),
    onPlayGames: (examData: ExamData?) -> Unit) {

    var loadingStatus by homeViewModel.status
    var message by remember {  mutableStateOf<String>("")  }
    val context  = LocalContext.current

    when(val status  = loadingStatus){
        "loading" -> {
                Toast.makeText(context,
                    message,
                    Toast.LENGTH_LONG).show()
        }
        "success" -> {
            onPlayGames(homeViewModel.examResponseObject?.data)
        }
        "fail" -> {

                Toast.makeText(context,
                    message,
                    Toast.LENGTH_LONG).show()

        }
    }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF6F6F6))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                painter = rememberAsyncImagePainter(model = "https://s3-alpha-sig.figma.com/img/ad3d/1783/a095dda4e9aecf5b09621b193b2545a3?Expires=1702252800&Signature=eM2UuO8GnvN-22BL7hR28IiRKFm0R5RzluPJuSnWKY7AHpdqJgD-lUGm7BOj9yWBGiz-WnI0jnxE4RXNYjKExc2KAFIxPCXDs3nj5sACLP3iXyQ0DckthsIzBIn8aA0wrTPzFzoAM7tEMjL4CtUcQ4u4PMviC3nT4HXn~1HHscQsO8Cj40HIzZi975QG1dLscssNZgEFvZViYNoxbWcdqXJvq186YPY4S3T6bEc92t5Tqao2aW3u8EjgVfV5vOShdusj~ZJESV9HO2~NIsikQwkpFDQrHieBv2IrSxEHBsq2na0~Hp6OroZ2-LUhJFPgU1IiFtgexD0xZF4xFXX2Uw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"),
                contentDescription = "image description",
                contentScale = ContentScale.Fit
            )
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 12.dp),
                text = "Hello, ${user?.fullname}",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = "Quiz List \uD83D\uDD25",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .background(
                        color = Color(0xFF9B7BF8),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tech",
                        fontSize = 12.sp,
                        color = Color(0xFFFFFFFF),
                    )
                    Image(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        painter = rememberAsyncImagePainter(model = "https://w7.pngwing.com/pngs/727/776/png-transparent-heart-love-red-valentine-romantic-romance-glossy-free-vector-graphics.png"),
                        contentDescription = "image description",
                        contentScale = ContentScale.Fit
                    )
                }
                Text(
                    text = "Technology General knowledge Quiz",
                    fontSize = 24.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp),
                    lineHeight = 33.6.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 99.dp, bottom = 12.dp, start = 18.dp, end = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "10 Questions",
                        fontSize = 12.sp,
                        color = Color(0xFFFFFFFF),
                    )
                    Text(
                        text = "10 Minutes",
                        fontSize = 12.sp,
                        color = Color(0xFFFFFFFF),
                    )
                }
                PlayButton(onClick = {
                    homeViewModel.createExam(user!!.username!!)
//                    loadingStatus = homeViewModel.status.value

                })


            }
            //SliderMinimal()
        }
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(top = 16.dp, bottom = 16.dp),
            text = "Recent Played",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                painter = painterResource(id = R.drawable.math),
                contentDescription = "image description",
                contentScale = ContentScale.Fit
            )
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 12.dp),
                text = "Algebra",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton()
        }
        BottomNavigationBar()
    }
}

@Composable
fun PlayButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF28254)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(64.dp),
        onClick = { onClick() }) {
        Text("Play now", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        //go to TypeMultiChoice
        //onClick = { startActivity(Intent(LocalContext.current, MainActivity::class.java)) }
    }
}

@Composable
fun TextButton() {
    TextButton(onClick = { /* Do something! */ }) {
        Text(
            "Play again",
            color = Color(0xFF9B7BF8),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SliderMinimal() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Quiz,
        NavigationItem.Profile,
    )

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 50.dp,
                        spotColor = Color(0x141B032F),
                        ambientColor = Color(0x141B032F)
                    )
                    .padding(start = 14.dp, top = 8.dp, end = 14.dp, bottom = 12.dp),
                backgroundColor = Color(0xFFFFFFFF),
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.icon),
                                contentDescription = screen.title,
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(bottom = 4.dp)
                            )
                        },
                        label = { Text(text = screen.title) },
                        selectedContentColor = Color(0xFF9B7BF8),
                        unselectedContentColor = Color.White.copy(0.4f),
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
            composable(NavigationItem.Home.route) {
                HomePage()
            }
            composable(NavigationItem.Quiz.route) {
                //HomeView()
            }
            composable(NavigationItem.Profile.route) {
                //HomeView()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    QuickZTheme {
        //HomeView()
    }
}