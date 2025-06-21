package com.example.jetweatherwise.View

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetweatherwise.R
import com.example.jetweatherwise.View.Components.BottomBar
import com.example.jetweatherwise.View.Components.CustomTopBar
import com.example.jetweatherwise.View.Components.ErrorCard
import com.example.jetweatherwise.View.Components.IdleCard
import com.example.jetweatherwise.View.Components.LoadingCard
import com.example.jetweatherwise.ViewModel.WeatherUiState
import com.example.jetweatherwise.ViewModel.WeatherViewModel
import com.example.jetweatherwise.ui.theme.archivo
import com.example.jetweatherwise.ui.theme.roboto_regular
import com.example.weatherwise.Model.WeatherData.WeatherResponse
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(navController: NavController) {

    val viewModel: WeatherViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var showError by remember { mutableStateOf(true) }

    Scaffold(
        topBar = { CustomTopBar() },
        bottomBar = { BottomBar(navController = navController) },
        containerColor = Color.Transparent
    ) { innerPadding ->


        val bgGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF6EA0FF), Color(0xFF8AC8FF), Color(0xFFB6E1FF))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgGradient)
                .padding(innerPadding)
        ) {
            when (uiState) {
                is WeatherUiState.Idle -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        IdleCard()
                    }
                }

                is WeatherUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingCard()
                    }
                }

                is WeatherUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        if (uiState is WeatherUiState.Error && showError){
                            val msg = (uiState as WeatherUiState.Error).message
                            ErrorCard(message = msg, onDismiss = { showError = false }, visible = true)
                        }

                    }
                }

                is WeatherUiState.Success -> {
                    val data = (uiState as WeatherUiState.Success).data
                    WeatherContent(navController, data)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherContent(navController: NavController, weatherData: WeatherResponse) {


    val today = LocalDate.now()
    val dayName = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val monthName = today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dayOfMonth = today.dayOfMonth

    val viewModel: WeatherViewModel = viewModel()
    val isSaved by viewModel.isSaved.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(weatherData.name) {
        viewModel.checkIfCityIsSaved(weatherData.name)
    }

        //Custom gradient background Color
        val bgGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF6EA0FF), Color(0xFF8AC8FF), Color(0xFFB6E1FF))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgGradient)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.1f), Color.Transparent),
                            radius = 500f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Current Weather Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(300.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.25f)
                    ),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.2f),
                                        Color.White.copy(alpha = 0.05f)
                                    )
                                )
                            )
                    ) {

                        // Save/Remove Button
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    if (!isSaved) {
                                        viewModel.saveToFavorites(weatherData)
                                        snackbarHostState.showSnackbar("Saved to favorites")
                                    } else {
                                        viewModel.removeFromFavorites(weatherData.name)
                                        snackbarHostState.showSnackbar("Removed from favorites")
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = if (isSaved) "Remove from favorites" else "Save to favorites",
                                tint = if (isSaved) Color.Red else Color.White
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            // Location and Date
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = weatherData.name,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }

                                Text(
                                    text = "$dayName $monthName $dayOfMonth",
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 14.sp
                                )
                            }

                            // Weather Icon and Temperature
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(R.drawable.sunny),
                                    contentDescription = "Sunny",
                                    tint = Color(0xFFFFD700),
                                    modifier = Modifier.size(80.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "${weatherData.main.temp}°",
                                    fontSize = 64.sp,
                                    fontWeight = FontWeight.Light,
                                    color = Color.White
                                )
                                Text(
                                    text = "Sunny",
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 18.sp
                                )
                            }

                            // Additional info
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                WeatherInfoItem("Feels like ${weatherData.main.feels_like}°")
                                WeatherInfoItem("Humidity ${weatherData.main.humidity}%")
                                WeatherInfoItem("Wind ${weatherData.wind.speed} km/h")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Weather Details Card
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(0.9f).wrapContentHeight()
                ) {

                    Text(
                        text = "WEATHER DETAILS",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 15.sp,
                        fontFamily = archivo,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(bottom = 8.dp, start = 8.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            WeatherDetailCard(Icons.Default.WaterDrop, "Humidity", "${weatherData.main.humidity}%")
                        }
                        Box(
                            modifier = Modifier.weight(1f)) {
                            WeatherDetailCard(Icons.Default.Air, "Wind", weatherData.wind.speed)
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            WeatherDetailCard(Icons.Default.Cloud, "Clouds", "${weatherData.clouds.all} (All)")
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            WeatherDetailCard(Icons.Default.Speed, "Pressure", "${weatherData.main.pressure} hPa")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Hourly Forecast
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(160.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = "HOURLY FORECAST",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 15.sp,
                            fontFamily = archivo,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 24.dp, bottom = 8.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            items(hourlyForecast) { hour ->
                                HourlyForecastCard(hour)
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(32.dp))
            }

        }
    }


@Composable
fun WeatherInfoItem(label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            fontFamily = roboto_regular
        )
    }
}



@Composable
fun WeatherDetailCard(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    label,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    value,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



@Composable
fun HourlyForecastCard(hour: HourForecast) {
    Column(
        modifier = Modifier.width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hour.time,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = hour.icon),
            contentDescription = hour.description,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${hour.temp}°",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


// Sample Data of hourlyForecast card
data class HourForecast(val time: String, val temp: Int, val icon: Int, val description: String)

val hourlyForecast = listOf(
    HourForecast("Now", 22, R.drawable.sunny, "Sunny"),
    HourForecast("1 PM", 23, R.drawable.cloud, "Cloudy"),
    HourForecast("2 PM", 23, R.drawable.cloud, "Cloudy"),
    HourForecast("3 PM", 22, R.drawable.cloud, "Cloudy"),
    HourForecast("4 PM", 21, R.drawable.cloud, "Partly Cloudy"),
    HourForecast("5 PM", 20, R.drawable.cloud, "Partly Cloudy"),
    HourForecast("6 PM", 19, R.drawable.sunny, "Sunset"),
)
