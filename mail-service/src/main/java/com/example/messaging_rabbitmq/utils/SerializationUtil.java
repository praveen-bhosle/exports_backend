package com.example.messaging_rabbitmq.utils;

import java.io.ByteArrayInputStream ; 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationUtil { 
    public static byte[] serialize(Object obj) throws IOException { 
        ByteArrayOutputStream bis =  new ByteArrayOutputStream() ; 
        ObjectOutputStream out = new ObjectOutputStream(bis) ; 
        out.writeObject(obj) ;
        return bis.toByteArray() ;  
    }

    public static<T> T deserialize( byte[] data ,  Class<T> clazz ) throws IOException , ClassNotFoundException {  
        ByteArrayInputStream bis = new ByteArrayInputStream(data) ; 
        ObjectInputStream in  = new ObjectInputStream(bis) ; 
        return clazz.cast(in.readObject()) ;
    }
}