import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

    }

    /**
     * @param data 写入的数据
     * @param path 写入的文件全路径
     * @throws IOException
     */
    public void write(Object data, String path) throws IOException {
        //设置文件路径
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        //将数据写入文件中
        fileOutputStream.write(String.valueOf(data).getBytes());
    }
}
