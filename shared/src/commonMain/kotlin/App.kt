import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bpavuk.catfacts.CatFacts
import kotlinx.coroutines.launch

val sdk = CatFacts()

@Composable
fun App() {
    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
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
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        catFact = sdk.getCatFact().fact
                    }
                },
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Text("Get Cat Fact")
            }
        }
    }
}

expect fun getPlatformName(): String