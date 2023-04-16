import com.sun.jna.platform.win32.Shell32;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Method method = null;
        try {
            Class clazz = new MyClassLoader().loadClass("Loader1");
            method = clazz.getDeclaredMethod("run",String.class);
            method.invoke(null,args[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            method.invoke(null,"first");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}