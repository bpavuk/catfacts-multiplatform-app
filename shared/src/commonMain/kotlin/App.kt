import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
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

class NoRippleInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

@Composable
fun MetroButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = NoRippleInteractionSource(),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = 80.dp)
            .border(
                width = 4.dp,
                color = Color.White
            )
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        interactionSource = interactionSource
    ) {
        content()
    }
}

expect fun getPlatformName(): String

@Composable
expect fun getCatImage(): Painter
