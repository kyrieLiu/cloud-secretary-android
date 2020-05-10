package com.jingkai.asset.widget.mark_tablayout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.jingkai.asset.R

/**
 * Created by Tomlezen.
 * Data: 2018/7/23.
 * Time: 15:25.
 */
class MarkTabItem(ctx: Context, attrs: AttributeSet? = null) : View(ctx, attrs) {

    var text:CharSequence? = null
    var icon:Drawable? = null

    init {
        attrs?.let {
            val a = ctx.obtainStyledAttributes(attrs, R.styleable.MarkTabItem)
            text = a.getText(R.styleable.MarkTabItem_android_text)
            icon = a.getDrawable(R.styleable.MarkTabItem_android_icon)
            a.recycle()
        }
    }

}