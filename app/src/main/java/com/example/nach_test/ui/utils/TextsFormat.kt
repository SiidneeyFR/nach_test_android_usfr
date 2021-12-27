package com.example.nach_test.ui.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

class TextsFormat {
    private fun TextsFormat() { //not called
    }

    fun fromHtml(html: String?): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }
}