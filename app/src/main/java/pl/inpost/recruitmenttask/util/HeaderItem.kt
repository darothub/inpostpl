package pl.inpost.recruitmenttask.util

import android.icu.text.CaseMap.Title

class HeaderItem(val title: String): ListItem() {
    override fun getType() = TYPE_HEADER
}