package com.example.jetweatherwise.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetweatherwise.Model.NavigationRoutes.NavigationRoutes
import com.example.jetweatherwise.R
import com.example.jetweatherwise.ViewModel.SplashViewModel
import com.example.jetweatherwise.ui.theme.archivo
import com.example.jetweatherwise.ui.theme.roboto_extra_light


@Composable
fun SplashScreen(navController: NavController,splashViewModel: SplashViewModel = viewModel()) {

    val isLoading by splashViewModel.isLoading.collectAsState()


    LaunchedEffect(isLoading) {
        if (!isLoading) {
            navController.navigate(NavigationRoutes.WeatherScreen.route) {
                popUpTo(NavigationRoutes.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.greysky),
            contentDescription = "Splash Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.applogo),
                contentDescription = null,
                modifier = Modifier.height(120.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Your World,",
                color = Color.Black,
                fontFamily = archivo,
                fontSize = 30.sp,
                lineHeight = 32.sp
            )
            Text(
                "WeatherWise",
                color = Color.Black,
                fontFamily = archivo,
                fontSize = 30.sp,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Real-time updates, precise",
                color = Color.Black,
                fontFamily = roboto_extra_light,
                fontSize = 18.sp
            )
            Text(
                "forecasts.",
                color = Color.Black,
                fontFamily = roboto_extra_light,
                fontSize = 18.sp
            )
        }
    }
}