import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.icerockdev.app.MR
import com.myapplication.common.R
import dev.icerock.moko.resources.compose.painterResource

actual fun getPlatformName(): String = "Android"

@Composable
actual fun getCatImage(): Painter = painterResource(R.drawable.background_cat)

@Composable fun MainView() = App()
