package com.github.uin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokableMethod {

   private final Method method;
   private final Object instance;

   public InvokableMethod(Method method, Object instance) {
      this.method = method;
      this.instance = instance;
   }

   public <T extends AbstractMenu> void invoke(T menu) {
      try {
         method.invoke(instance, menu);
      } catch (IllegalAccessException | InvocationTargetException e) {
         e.printStackTrace();
      }
   }

}
