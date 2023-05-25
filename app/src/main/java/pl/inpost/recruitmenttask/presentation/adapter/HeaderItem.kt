package pl.inpost.recruitmenttask.presentation.adapter

class HeaderItem(val title: String): ListItem() {
    override fun getType() = TYPE_HEADER
}