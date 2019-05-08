package co.edu.intecap.notes.model.converters;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateTypeConverter {

    @TypeConverter
    public Long toTime(Date date){
        return  date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date toDate(Long time){
        return  time == null ? null : new Date(time);
    }


}