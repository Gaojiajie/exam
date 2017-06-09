package gjj.lock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    static HttpURLConnection conn = null;
    static PrintWriter print=null;
    public static void httpconnect(String url) {
        try {
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String post(String params) {
        String result = "";
        try {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            OutputStream out = conn.getOutputStream();
            print = new PrintWriter(out);
            print.print(params);
            print.flush();
            InputStreamReader input = new InputStreamReader(
                    conn.getInputStream());
            BufferedReader buffer = new BufferedReader(input);
            String inputLine = null;
            while ((inputLine = buffer.readLine()) != null)
                result += inputLine;
            print.close();
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
