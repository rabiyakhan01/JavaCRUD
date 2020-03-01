package com.brd.brdtools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * Example main class with generating class during runtime and invoking generated method.
 *
 * @author hrabosch
 */
public class RuntimeClassGen {

    private static final String className = "MyClass";
    private static final String methodName = "printHello";
    private static final String methodBody = "java.lang.System.out.println(\"Hello world!\");";


    public static void main(String[] args) {
        try {
            // Use our static method to make a magic
            Class clazz = generateClass(className, methodName, methodBody);



            // Create a new instance of our newly generated class
            Object obj = clazz.newInstance();

            // Find our method in generated class
            Method method = clazz.getDeclaredMethod(methodName);

            // And finally invoke it on instance
            method.invoke(obj);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    public static Class generateClass(String className, String methodName, String methodBody)
            throws CannotCompileException {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        StringBuffer method = new StringBuffer();
        method.append("public void ")
                .append(methodName)
                .append("() {")
                .append(methodBody)
                .append(";}");

        cc.addMethod(CtMethod.make(method.toString(), cc));


        return cc.toClass();
    }

}
