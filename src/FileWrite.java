import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileWrite {
    static void writeText(String str, String path)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file.getName(), true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        osw.write(str);
        osw.flush();
        osw.close();
        System.out.println("Done");
    }

    static void writeArrayList(ArrayList<String[]> arrayList, String path)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file, true);
        //FileWriter fw = new FileWriter(file, true);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");
        for (String[] strArray : arrayList) {
            osw.write(strArray[0] + "," + strArray[1] + "\n");
        }
        osw.close();
        bos.flush();
        bos.close();
        fos.close();
        System.out.println("Done.");
    }
}
