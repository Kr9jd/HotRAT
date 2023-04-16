import java.io.InputStream;
import java.util.Base64;

public class MyClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = null;
        InputStream inputStream = MyClassLoader.class.getResourceAsStream(name + ".class_");
        try {
            byte[] tempbytes = read(inputStream);
            bytes = Base64.getDecoder().decode(tempbytes);
        } catch (Exception e) {
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
    private byte[] read(InputStream inputStream) {
        byte[] bytes = null;
        try {
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        }catch (Exception e) {
        }
        return bytes;
    }
}