package com.jingkai.asset.widget.mark_tablayout

import com.boda.xinyuan.widgets.mark_tablayout.MarkTab


/**
 * Created by Tomlezen.
 * Data: 2018/7/23.
 * Time: 14:35.
 */
interface OnTabSelectedListener {

    fun onTabSelected(tab: MarkTab)

    fun onTabUnselected(tab: MarkTab)

    fun onTabReselected(tab: MarkTab)

}