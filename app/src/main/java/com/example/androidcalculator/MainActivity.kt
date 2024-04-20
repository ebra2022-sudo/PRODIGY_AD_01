package com.example.androidcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcalculator.ui.theme.AndroidCalculatorTheme
import com.example.androidcalculator.ui.theme.ButtonShadowColorBottom
import com.example.androidcalculator.ui.theme.ButtonShadowColorTop

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

// calculator button Ui
// I used nested box composable so that I can make 3d looking button
@Composable
fun CalcButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 22,
    color: Color = MaterialTheme.colorScheme.tertiaryContainer,
    fontColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    onClick: () -> Unit

) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(color)
            .clickable { onClick() }
            .then(modifier)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(0.8f)
                //custom extension function to Modifier Interface
                .shadow(
                    color = ButtonShadowColorBottom,
                    offsetX = (-4).dp,
                    offsetY = (-4).dp,
                    blurRadius = 10.dp
                )
                .shadow(
                    color = ButtonShadowColorTop,
                    offsetX = (4).dp,
                    offsetY = (4).dp,
                    blurRadius = 8.dp
                )
                .clip(MaterialTheme.shapes.medium)
                .background(color)
                .clickable { onClick() }
                .then(modifier)) {
            Text(text = text, color = fontColor, fontSize = fontSize.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidCalculatorTheme {

    }
}