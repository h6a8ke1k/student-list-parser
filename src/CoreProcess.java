import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

class CoreProcess {
    private static ArrayList<String[]> arrayList;
    static StringBuilder output = new StringBuilder();

    static int start(String inURL, String outURL) {
        output.delete(0, output.length());
        BufferedReader br = FileRead.getBufferedReader(inURL);
        if (br == null) {
            output.append("打开文件: ");
            output.append(inURL);
            output.append(" 错误!\n");
            return -1; // 打开文件错误
        }
        String str;
        arrayList = new ArrayList<>();
        boolean allCorrect = true;
        try {
            while (null != (str = br.readLine()))
                if (!processString(str)) allCorrect = false;
        } catch (IOException e) {
            System.err.println("读取文件: " + inURL + " 错误!");
            output.append("读取文件: ");
            output.append(inURL);
            output.append(" 错误!\n");
            return -2; // 读取文件错误
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!writeFile(outURL)) {
            output.append("写入文件: ");
            output.append(outURL);
            output.append(" 错误!\n");
            return -3; // 写入错误
        }
        if (!allCorrect) {
            output.append("部分学号未找到对应姓名!\n");
            return -4; // 部分学号未找到对应姓名
        }
        output.append("成功!\n");
        return 0; // 成功
    }

    private static boolean processString(String str) {
        if (!str.equals(""))
            return processArray(str.split(","));
        return true;
    }

    private static boolean processArray(String[] arr) {
        String[] strArray = new String[2];
        boolean wait = false;
        String regEx = "^\\d{9}$"; // 学号
        Pattern pattern = Pattern.compile(regEx);
        String regEx1 = "\\d+"; // 重新分班后的学号(短)
        Pattern pattern1 = Pattern.compile(regEx1);
        boolean allCorrect = true;
        for (String str : arr) {
            if (wait && !str.equals("") && !checkIgnore(str) && !pattern1.matcher(str).find()) {
                strArray[1] = str;
                arrayList.add(strArray);
                if (checkWarn(str)) {
                    System.out.println("[" + strArray[0] + "," + strArray[1] + "]: 请检查姓名是否存在多余字符.");
                    output.append("[");
                    output.append(strArray[0]);
                    output.append(",");
                    output.append(strArray[1]);
                    output.append("]: 请检查姓名是否存在多余字符.\n");
                }
                strArray = new String[2];
                wait = false;
            }
            if (pattern.matcher(str).find()) {
                if (wait) {
                    System.out.println(strArray[0] + ": 找不到对应姓名.");
                    output.append(strArray[0]);
                    output.append(": 找不到对应姓名.\n");
                    allCorrect = false;
                } else {
                    wait = true;
                }
                strArray[0] = str;
            }
        }
        return allCorrect;
    }

    private static boolean writeFile(String outURL) {
        try {
            FileWrite.writeArrayList(arrayList, outURL);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean checkIgnore(String in) {
        boolean ret = false;
        String[] list = SettingsUI.ignoreStr.getText().split(",");
        for (String str : list) {
            if (in.equals(str)) ret = true;
        }
        return ret;
    }

    private static boolean checkWarn(String in) {
        boolean ret = false;
        String[] list = SettingsUI.warnStr.getText().split(",");
        for (String str : list) {
            if (in.contains(str)) ret = true;
        }
        return ret;
    }
}
