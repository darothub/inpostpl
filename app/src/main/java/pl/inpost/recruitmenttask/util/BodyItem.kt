package pl.inpost.recruitmenttask.util

class BodyItem<T>( var data: T): ListItem() {
    override fun getType() = TYPE_BODY
}