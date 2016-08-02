import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by cgspine on 16/7/21.
 */
public class HttpDemo {
    public static void main(String[] args){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
