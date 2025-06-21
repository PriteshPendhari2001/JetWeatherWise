package com.example.jetweatherwise.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jetweatherwise.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


//Customize Fonts
val archivo = FontFamily(
    Font(R.font.archivo_extrabold) //This we can use for Main Strings for Highlightings
)


val roboto = FontFamily(
    Font(R.font.roboto_medium_500), //Strong bold
)

val roboto_regular = FontFamily(
    Font(R.font.roboto_regular_400), // normal bold
)

val roboto_extra_light = FontFamily(
    Font(R.font.roboto_extra_light_200) // for minimal string
)

