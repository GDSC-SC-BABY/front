package com.example.baby.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.baby.R

// Set of Material typography styles to start with
val nanumSquare = FontFamily(
    Font(R.font.nanum_square_eb, FontWeight.ExtraBold),
    Font(R.font.nanum_square_b, FontWeight.Bold),
    Font(R.font.nanum_square_r, FontWeight.Normal),
    Font(R.font.nanum_square_l, FontWeight.Light)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val startActivityFontStyle = Typography(
    defaultFontFamily = nanumSquare,
    h1 = TextStyle(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h2 = TextStyle(
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    body1 = TextStyle(
        color = Color(R.color.gray4),
        fontFamily = nanumSquare,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    button = TextStyle(
        color = Color(R.color.secondary_color),
        fontFamily = nanumSquare,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
    ),
)