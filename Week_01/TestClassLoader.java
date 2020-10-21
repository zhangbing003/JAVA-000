import java.io.File;
import java.io.FileInputStream;

/**
 * Created by zhangbing on 2020/10/21.
 */
public class TestClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> helloClass = new TestClassLoader().findClass("Hello");
            helloClass.getMethod("hello").invoke(helloClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override 
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] fileByte = new byte[0];
        try {
            fileByte = getFileByte("d://" + name + ".xlass");
        } catch (Exception e) {
            e.printStackTrace();
        }
        decodeBytes(fileByte);
        return defineClass(name, fileByte, 0, fileByte.length);
    }
    
    private void decodeBytes(byte[] fileByte) {
        for (int i = 0; i < fileByte.length; i++) {
            fileByte[i] = (byte) (255 - fileByte[i]);
        }
    }

    public static byte[] getFileByte(String filePath) throws Exception {
        File file = new File(filePath);
        long fileSize = file.length();
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        fi.close();
        return buffer;
    }
}
