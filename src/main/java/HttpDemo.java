import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cgspine on 16/7/21.
 */
public class HttpDemo {
    public static void main(String[] args){
        // HttpURLConnection
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        getWithHttpURLConnection("http://wwww.baidu.com", outputStream);
//        System.out.println(outputStream.toString());
//        Util.close(outputStream);

        // OkHttp sync
//        try {
//            Response response = getWithOkHttpSync("http://www.baidu.com");
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // OkHttp async
//        getWithOkHttpAsync("http://www.baidu.com", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
//            }
//        });
//        System.out.println("request sent!!!");


    }

    static OkHttpClient client = new OkHttpClient();
    static void getWithOkHttpAsync(String url, Callback callback){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    static Response getWithOkHttpSync(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        return client.newCall(request).execute();
    }

    static void getWithHttpURLConnection(String urlString,OutputStream out){
        HttpURLConnection conn = null;
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            // 设置是否向httpUrlConnection输出
            conn.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            conn.setDoInput(true);
            // 缓存
            conn.setUseCaches(false);
            // 请求方法
            conn.setRequestMethod("GET");
            in = conn.getInputStream();
            byte[] buffer = new byte[64];
            int size;
            while ((size = in.read(buffer)) != -1){
                System.out.println(size );
                out.write(buffer, 0, size);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                conn.disconnect();
            }
            Util.close(in);
        }
    }
}
