import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.revsb_11.R

@Composable
fun CustomCircularProgressIndicator(modifier: Modifier = Modifier) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_progress_indicator_dark))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever, // Зацикливание анимации
        isPlaying = true, // Автоматическое воспроизведение
        speed = 1f // Скорость воспроизведения
    )

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val animationSize = minOf(screenHeight, screenWidth) / 5

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier.size(animationSize).wrapContentSize(Alignment.Center),
        contentScale = ContentScale.Crop // Или другой ContentScale по вашему выбору
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewRotatingCircularProgressIndicator() {
    CustomCircularProgressIndicator(modifier = Modifier)
}