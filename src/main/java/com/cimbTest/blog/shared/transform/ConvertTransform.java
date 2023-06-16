package com.cimbTest.blog.shared.transform;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ConvertTransform {

  @Named("instantTimeToString")
  default String instantTimeToString(Date dateTime){
    if (dateTime != null) {
      try {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(dateTime);
      }catch (Exception e){
        return dateTime.toString();
      }
    }
    return null;
  }
}
