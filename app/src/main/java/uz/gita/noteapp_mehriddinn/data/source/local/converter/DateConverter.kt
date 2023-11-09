package uz.gita.noteapp_mehriddinn.data.source.local.converter

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    @TypeConverter
    fun fromDateToTimeStamp(date: Date):String{
        val dateFormat = SimpleDateFormat("dd/MM/yyyy") //Sana formatini belgilash
        val formattedDate = dateFormat.format(date)
        return formattedDate
    }
    @TypeConverter
    fun fromTimeStampToDate(value:Long?):Date?{
        return value?.let { Date(it) }
    }
}