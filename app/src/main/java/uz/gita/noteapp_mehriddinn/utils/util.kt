package uz.gita.noteapp_mehriddinn.utils

fun <T> T.myApply(block: T.() -> Unit){
    block(this)
}