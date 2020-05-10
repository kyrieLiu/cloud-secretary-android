package com.boda.xinyuan.widgets.mark_tablayout

import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import com.jingkai.asset.widget.mark_tablayout.MarkTabLayout

/**
 * Created by liuyin on 2019/2/22 11:25
 * @Describe 
 */
class MarkTab(var parent: MarkTabLayout? = null, var view: MarkTabLayout.MarkTabView? = null) {

    var tag: String? = null
    var icon: Drawable? = null
        set(value) {
            field = value
            updateView()
        }
    var text: CharSequence? = null
        set(value) {
            field = value
            updateView()
        }
    var position: Int = INVALID_POSITION

    val isSelected: Boolean
        get() = parent?.getSelectedTabPosition() == position

//    fun setIcon(@DrawableRes resId: Int) {
//        parent?.let {
//            icon = (AppCompatResources.getDrawable(it.context, resId))
//        }
//    }

    fun setText(@StringRes resId: Int) {
        text = parent?.context?.getString(resId)
    }

    fun select() {
        parent?.selectTab(this)
    }

    fun updateView() {
        view?.update()
    }

    fun reset() {
        parent = null
        view = null
    }

    companion object {
        const val INVALID_POSITION = -1
    }

}