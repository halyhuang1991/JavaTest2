package com.Refretor;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

import com.Beans.Person;

public class TestRefretor {
    public static void run() {
        Person p = new Person();
        Class classType = p.getClass();// c1 = Class.forName("com.Beans.Person");p=c1.newInstance();
        Field[] fields = classType.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Class c = f.getType();
                if (c.getName() == "java.lang.String") {
                    f.set(p, "lisi");
                } else {
                    f.set(p, 1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // System.out.println(p.getName());
        }
        try {
            Method met = classType.getMethod("setName", String.class);
            met.invoke(p, "test");
            met = classType.getMethod("getName");
            String result = (String) met.invoke(p);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Class c1;
        Info pp;
        try {
            c1 = Class.forName("com.Refretor.Info");
            Annotation[] annotations = c1.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                System.out.println(annotations[i]);
            }
            pp = (Info) c1.newInstance();
            Method method = c1.getMethod("setName", String.class);
            annotations = method.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                Annotation an = annotations[i];
                if (method.isAnnotationPresent(MyAnnotation.class)) {
                    MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
                    String key = myAnnotation.key();
                    String value = myAnnotation.value();
                    System.out.println(key + ":" + value);
                }
                System.out.println(annotations[i]);
            }
            Field field = c1.getDeclaredField("id");
            field.setAccessible(true);
            annotations = field.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                System.out.println(annotations[i]);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public static String initStr(String old) {
        String newStr = old.substring(0, 1).toUpperCase() + old.substring(1);
        return newStr;
    }
}