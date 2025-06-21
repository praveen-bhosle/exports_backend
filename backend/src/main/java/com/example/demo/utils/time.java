package com.example.demo.utils;
import java.util.Calendar;
import java.util.Date ;

public class time {
    
   public static Date current() { 
    return  new Date() ;
   } 

   public static Date afterOneMonth() { 
    Date now = new Date() ; 
    Calendar cal = Calendar.getInstance(); 
    cal.setTime(now); 
    cal.add(Calendar.MONTH, 1); 
    return  cal.getTime() ;
   }
    
}