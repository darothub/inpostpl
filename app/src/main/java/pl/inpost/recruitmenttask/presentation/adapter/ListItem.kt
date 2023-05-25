package pl.inpost.recruitmenttask.presentation.adapter

abstract class ListItem {
   companion object {
       const val TYPE_HEADER = 0
       const val TYPE_BODY = 1
   }
    abstract fun getType(): Int
}