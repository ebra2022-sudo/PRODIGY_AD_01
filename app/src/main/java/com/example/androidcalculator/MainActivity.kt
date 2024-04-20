package com.example.androidcalculator

import android.graphics.BlurMaskFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
// shadow extension function to modifier
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp =0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }
        frameworkPaint.color = color.toArgb()
        drawIntoCanvas {canvas ->
            val leftP = offsetX.toPx()
            val topP = offsetY.toPx()
            val rightP = size.width + topP
            val bottomP = size.height + leftP
            canvas.drawRect(
                left =  leftP, right =  rightP, top  =  topP,
                bottom =  bottomP, paint = paint
            )
        }
    }
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidCalculatorTheme {
        CalcButton(text = "1", modifier = Modifier.size(100.dp)) {
        }
    }
}