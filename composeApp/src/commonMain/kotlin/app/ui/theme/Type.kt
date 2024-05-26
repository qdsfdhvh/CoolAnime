package app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.seiko.anime.Res
import com.seiko.anime.cute_regular
import com.seiko.anime.hei_bold
import com.seiko.anime.hei_regular
import com.seiko.anime.poster_bold
import com.seiko.anime.roboto_bold
import com.seiko.anime.roboto_regular
import org.jetbrains.compose.resources.Font

object CoolAnimeFontFamilies {

  val posterFontFamily: FontFamily
    @Composable
    get() = FontFamily(
      Font(Res.font.poster_bold, FontWeight.Bold),
    )

  val cuteFontFamily: FontFamily
    @Composable
    get() = FontFamily(
      Font(Res.font.cute_regular, FontWeight.Normal),
    )

  val heiFontFamily: FontFamily
    @Composable
    get() = FontFamily(
      Font(Res.font.hei_bold, FontWeight.Bold),
      Font(Res.font.hei_regular, FontWeight.Normal),
    )

  val robotoFamily: FontFamily
    @Composable
    get() = FontFamily(
      Font(Res.font.roboto_regular, FontWeight.Normal),
      Font(Res.font.roboto_bold, FontWeight.Bold),
    )
}

object CoolAnimeTypography
