package com.Refretor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class loadjar {

    private static StringBuffer sBuffer;

    public static void getJar(String jar) throws Exception {
        try {
            File file = new File(jar);
            URL url = file.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[] { url },
                    Thread.currentThread().getContextClassLoader());

            JarFile jarFile = new JarFile(jar);
            Enumeration<JarEntry> enumeration = jarFile.entries();
            JarEntry jarEntry;

            sBuffer = new StringBuffer(); // 存数据

            while (enumeration.hasMoreElements()) {
                jarEntry = enumeration.nextElement();

                if (jarEntry.getName().indexOf("META-INF") < 0) {
                    String classFullName = jarEntry.getName();
                    if (classFullName.indexOf(".class") < 0) {
                        classFullName = classFullName.substring(0, classFullName.length() - 1);
                    } else {
                        // 去除后缀.class，获得类名
                        String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                        Class<?> myClass = classLoader.loadClass(className);
                        sBuffer.append("类名\t：" + className);
                        System.out.println("类名\t：" + className);

                        // 获得属性名
                        Class<?> clazz = Class.forName(className);
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
                            sBuffer.append("属性名\t：" + field.getName() + "\n");
                            System.out.println("属性名\t：" + field.getName());
                            sBuffer.append("-属性类型\t：" + field.getType() + "\n");
                            System.out.println("-属性类型\t：" + field.getType());
                        }

                        Method[] methods = myClass.getMethods();
                        for (Method method : methods) {
                            if (method.toString().indexOf(className) > 0) {
                                sBuffer.append("方法名\t：" +
                                         method.toString().substring(method.toString().indexOf(className)) + "\n");
                                System.out.println(
                                        "方法名\t：" + method.toString().substring(method.toString().indexOf(className)));
                            }
                        }
                        sBuffer.append(
                                "--------------------------------------------------------------------------------"
                                        + "\n");
                        System.out.println(
                                "--------------------------------------------------------------------------------");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sBuffer.append("End");
            System.out.println("End");
        }
    }

    public Object copy(Object object) throws Exception {
        // 获得对象的类型
        Class classType = object.getClass();
        System.out.println("Class:" + classType.getName());

        // 通过默认构造方法创建一个新的对象
        Object objectCopy = classType.getConstructor(new Class[] {}).newInstance(new Object[] {});

        // 获得对象的所有属性
        Field fields[] = classType.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            // 获得和属性对应的getXXX()方法的名字
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            // 获得和属性对应的setXXX()方法的名字
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            // 获得和属性对应的getXXX()方法
            Method getMethod = classType.getMethod(getMethodName, new Class[] {});
            // 获得和属性对应的setXXX()方法
            Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() });

            // 调用原对象的getXXX()方法
            Object value = getMethod.invoke(object, new Object[] {});
            System.out.println(fieldName + ":" + value);
            // 调用拷贝对象的setXXX()方法
            setMethod.invoke(objectCopy, new Object[] { value });
        }
        return objectCopy;
    }
}