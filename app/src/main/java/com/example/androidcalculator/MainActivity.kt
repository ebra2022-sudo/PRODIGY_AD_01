package com.example.androidcalculator

import android.content.res.Configuration
import android.graphics.BlurMaskFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcalculator.ui.theme.AndroidCalculatorTheme
import com.example.androidcalculator.ui.theme.ButtonBlue
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
                    CalculatorUI()
                }
            }
        }
    }
}
@Composable
fun CalculatorUI(calculatorViewModel: Calculator = viewModel()) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val gradientColorsLight = listOf(Color.Cyan, ButtonBlue, Color(0xFFB993D1))
    val gradientColorsDark = listOf(
        Color(0xFF5C6BC0), // Dark blue (you can replace with your ButtonBlue)
        Color(0xFF2E294E)  // Dark purple or indigo (matching start color)
    )
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (isSystemInDarkTheme()) gradientColorsDark else gradientColorsLight,
                    tileMode = TileMode.Mirror
                )
            )
            .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = calculatorViewModel.inputExpression,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Medium,
                fontSize = if (calculatorViewModel.flag) 40.sp else 30.sp,
                color = if (calculatorViewModel.flag) Color(0xFFFF5722) else MaterialTheme.colorScheme.onBackground,
                lineHeight = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 10.dp)
            )
            HorizontalDivider(thickness = 2.dp)
            Text(
                text = if (calculatorViewModel.flag) "" else calculatorViewModel.evaluate(),
                textAlign = TextAlign.End,
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 10.dp)
            )
            HorizontalDivider(thickness = 2.dp)
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(if (!isPortrait) 6f else 2.5f)
                .padding(top = 10.dp),
                verticalArrangement = Arrangement.SpaceAround) {
                val buttonModifier = Modifier
                    .size(if (!isPortrait) 40.dp else 75.dp)
                    .weight(1f)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CalcButton(text= "C", modifier = buttonModifier, fontColor = Color.Red) { calculatorViewModel.clear() }
                    CalcButton(text = "(", modifier = buttonModifier ) { calculatorViewModel.receiveNumber("(") }
                    CalcButton(text = ")", modifier = buttonModifier) { calculatorViewModel.receiveNumber(")") }
                    CalcButton(text = "x", modifier = buttonModifier, color = MaterialTheme.colorScheme.secondaryContainer,
                        fontColor = MaterialTheme.colorScheme.onSecondaryContainer) { calculatorViewModel.receiveNumber("×") }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CalcButton(text = "7", modifier = buttonModifier) { calculatorViewModel.receiveNumber("7") }
                    CalcButton(text = "8", modifier = buttonModifier) { calculatorViewModel.receiveNumber("8") }
                    CalcButton(text = "9", modifier = buttonModifier) { calculatorViewModel.receiveNumber("9") }
                    CalcButton(text = "÷", modifier = buttonModifier, color = MaterialTheme.colorScheme.secondaryContainer,
                        fontColor = MaterialTheme.colorScheme.onSecondaryContainer) { calculatorViewModel.receiveNumber("÷") }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CalcButton(text = "4", modifier = buttonModifier) { calculatorViewModel.receiveNumber("4") }
                    CalcButton(text = "5", modifier = buttonModifier) { calculatorViewModel.receiveNumber("5") }
                    CalcButton(text = "6", modifier = buttonModifier) { calculatorViewModel.receiveNumber("6") }
                    CalcButton(text = "-", modifier = buttonModifier, color = MaterialTheme.colorScheme.secondaryContainer,
                        fontColor = MaterialTheme.colorScheme.onSecondaryContainer) { calculatorViewModel.receiveNumber("-") }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CalcButton(text = "1", modifier = buttonModifier) { calculatorViewModel.receiveNumber("1") }
                    CalcButton(text = "2", modifier = buttonModifier) { calculatorViewModel.receiveNumber("2") }
                    CalcButton(text = "3", modifier = buttonModifier) { calculatorViewModel.receiveNumber("3") }
                    CalcButton(text = "+", modifier = buttonModifier, color = MaterialTheme.colorScheme.secondaryContainer,
                        fontColor = MaterialTheme.colorScheme.onSecondaryContainer) { calculatorViewModel.receiveNumber("+") }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CalcButton(text = "Dl", modifier = buttonModifier, fontSize = 22, fontColor = Color.Red) { calculatorViewModel.delete() }
                    CalcButton(text = "0", modifier = buttonModifier) { calculatorViewModel.receiveNumber("0") }
                    CalcButton(text = ".", modifier = buttonModifier) { calculatorViewModel.receiveNumber(".") }
                    CalcButton(text = "=", modifier = buttonModifier, color = MaterialTheme.colorScheme.primary,
                        fontColor = MaterialTheme.colorScheme.onPrimary) { calculatorViewModel.onEqual() }
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
            frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(),
                BlurMaskFilter.Blur.NORMAL))
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
fun CalculatorLightModePreview() {
    AndroidCalculatorTheme {
       CalculatorUI()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorDarkModePreview() {
    AndroidCalculatorTheme(darkTheme = true) {
        CalculatorUI()
    }
}