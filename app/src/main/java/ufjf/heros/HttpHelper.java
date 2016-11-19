package ufjf.heros;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alex on 19/11/2016.
 */

public class HttpHelper {
    public final int TIMEOUT_MILLIS = 15000;

    public String doGet(String url) throws IOException {
        //return doGet(url, null, "UTF-8");
        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;

        try {
            conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.connect();

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            byte[] bytes = toBytes(in);
            s = new String(bytes, "UTF-8");
            in.close();

        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }

    private byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                baos.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
