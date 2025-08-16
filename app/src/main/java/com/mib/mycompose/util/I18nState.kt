package com.mib.mycompose.util

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  author : cengyimou
 *  date : 2025/8/16 00:00
 *  description :
 */
object I18nState {
    val currentTag = mutableStateOf<String?>(null) // null 代表跟随系统
    
    fun setTag(tag: String){
        currentTag.value = tag
    }
    
    fun changeI18(context: Context){
        currentTag.value = if (currentTag.value == "en") "es" else "en"
        saveCurrentLanguage(context, currentTag.value)
    }

    suspend fun getCurrentLanguage(context: Context): String {
        return withContext(Dispatchers.IO) {
            SPUtils.getSharedStringData(context, LANGUAGE_KEY, DEFAULT_LANGUAGE_TAG)
        }
    }

    fun saveCurrentLanguage(context: Context, languageTag: String?) {
        SPUtils.setSharedStringData(context, LANGUAGE_KEY, languageTag ?: DEFAULT_LANGUAGE_TAG)
    }

    const val LANGUAGE_KEY = "language_key"

    const val DEFAULT_LANGUAGE_TAG = "en"
    const val ES_LANGUAGE_TAG = "es"
    const val EN_LANGUAGE_TAG = "en"
}

val LocalI18n = staticCompositionLocalOf<I18n> {
    error("No I18n provided")
}

class I18n(private val context: Context) {
    fun t(@StringRes id: Int, vararg args: Any) =
        context.resources.getString(id, *args)
}

@Composable
fun I18nProvider(tag: String?, content: @Composable () -> Unit) {
    val localContext = LocalContext.current
    val cfgCtx = remember(tag) {
        val cfg = Configuration(localContext.resources.configuration)
        if (!tag.isNullOrBlank()) {
            val locale = java.util.Locale.forLanguageTag(tag)
            cfg.setLocale(locale)
        }
        localContext.createConfigurationContext(cfg)
    }
    CompositionLocalProvider(LocalI18n provides I18n(cfgCtx)) {
        content()
    }
}
