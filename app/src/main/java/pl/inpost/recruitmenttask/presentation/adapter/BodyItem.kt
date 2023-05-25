package pl.inpost.recruitmenttask.presentation.adapter

class BodyItem<T>( var data: T): ListItem() {
    override fun getType() = TYPE_BODY
}