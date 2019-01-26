import java.io.*;

public class FileRead {
    static String readText(String path){
        StringBuilder content = new StringBuilder("");
        try {
            String code = resolveCode(path);
            File file = new File(path);
            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, code);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while (null != (str = br.readLine()))
                content.append(str);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("读取文件: " + path + " 失败!");
        }
        return content.toString();
    }

    static BufferedReader getBufferedReader(String path){
        StringBuilder content = new StringBuilder("");
        try {
            String code = resolveCode(path);
            File file = new File(path);
            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, code);
            return new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("读取文件: " + path + " 失败!");
        }
        return null;
    }

    private static String resolveCode(String path) throws Exception {
        InputStream inputStream = new FileInputStream(path);
        String code = "GBK"; //gb2312
        byte[] head = new byte[3];
        inputStream.read(head);
        if (head[0] == -1 && head[1] == -2)
            code = "UTF-16";
        else if (head[0] == -2 && head[1] == -1)
            code = "Unicode";
        else if (head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "UTF-8";
        inputStream.close();
        //System.out.println(code);
        return code;
    }

    static boolean checkExist(String url) {
        File temp = new File(url);
        return temp.exists();
    }

    static boolean checkIsFile(String url) {
        File temp = new File(url);
        return temp.isFile();
    }
}
