package com.mustfaibra.simpleanimationcasestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustfaibra.simpleanimationcasestudy.screens.HomeScreen
import com.mustfaibra.simpleanimationcasestudy.ui.theme.SimpleAnimationCaseStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /** Setting the status bar color */
            window.statusBarColor = MaterialTheme.colors.background.toArgb()
            SimpleAnimationCaseStudyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HolderScreen()
                }
            }
        }
    }
}

@Composable
fun HolderScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                text = "Nike.",
                style = MaterialTheme.typography.body1.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = MaterialTheme.colors.onBackground,
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_nike_icon),
                contentDescription = "logo",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colors.primary,
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {  }
                    .padding(6.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_shopping_bag),
                    contentDescription = "cart",
                    modifier = Modifier
                        .size(30.dp),
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                )            }
        }
        HomeScreen()
    }
}