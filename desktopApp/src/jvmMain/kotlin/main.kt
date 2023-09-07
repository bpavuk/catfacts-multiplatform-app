import androidx.compose.ui.Alignment
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val mainWindowState = rememberWindowState(
        size = DpSize(400.dp, 400.dp),
        position = WindowPosition.Aligned(Alignment.Center)
    )
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = { keyEvent ->
            when {
                keyEvent.isCtrlPressed && keyEvent.key == Key.Q && keyEvent.type == KeyEventType.KeyUp -> {
                    exitApplication()
                    true
                }
                keyEvent.isCtrlPressed && keyEvent.key == Key.S && keyEvent.type == KeyEventType.KeyDown -> {
                    mainWindowState.size = DpSize(400.dp, 400.dp)
                    true
                }
                keyEvent.isCtrlPressed && keyEvent.key == Key.B -> {
                    mainWindowState.size = DpSize(600.dp, 600.dp)
                    true
                }
                else -> false
            }
        },
        state = mainWindowState
    ) {
        MainView()
    }
}
