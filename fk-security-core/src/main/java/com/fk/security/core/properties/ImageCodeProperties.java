package com.fk.security.core.properties;

import lombok.Data;

/**
 * @author fankun
 * @date 2018/3/13 15:14
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
   private  int width = 67;
   private  int height = 23;
   public ImageCodeProperties (){
       setLength(4);
   }
}
