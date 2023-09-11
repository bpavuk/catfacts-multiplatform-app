import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

actual fun getPlatformName(): String = "Desktop"

@Composable
actual fun getCatImage(): Painter = painterResource("images/background_cat.jpg")

@Composable fun MainView() = App()

@Preview
@Composable
fun AppPreview() {
    App()
}