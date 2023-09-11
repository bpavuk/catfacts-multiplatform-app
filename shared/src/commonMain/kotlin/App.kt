import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bpavuk.catfacts.CatFacts
import kotlinx.coroutines.launch

val sdk = CatFacts()

@Composable
fun App() {
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
        Box {
            Image(
                painter = getCatImage(),
                contentDescription = null,
                modifier = Modifier
                    .blur(32.dp),
                contentScale = ContentScale.Crop
            )
            var catFact by remember { mutableStateOf("") }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                AnimatedContent(
                    catFact,
                    transitionSpec = {
                        slideInVertically() +
                                fadeIn(tween()) togetherWith ExitTransition.None
                    }
                ) { fact ->
                    Text(
                        fact,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }
                MetroButton(
                    onClick = {
                        coroutineScope.launch {
                            catFact = sdk.getCatFact().fact
                        }
                    }
                ) {
                    Text(
                        text = "Show my cat fact!",
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

@Composable
fun MetroButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    content: @Composable (RowScope.() -> Unit)
) {
    val rememberedInteractionSource = remember { interactionSource }
    val isPressed by rememberedInteractionSource.collectIsPressedAsState()
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(bottom = 80.dp)
                .border(
                    width = 4.dp,
                    color = Color.White
                )
                .animateContentSize(tween())
                .padding(if (isPressed) 0.dp else 2.dp)
                .then(modifier),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 0.dp,
                focusedElevation = 0.dp
            ),
            interactionSource = rememberedInteractionSource,
        ) {
            content()
        }
    }
}

expect fun getPlatformName(): String

@Composable
expect fun getCatImage(): Painter
