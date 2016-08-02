import java.io.Closeable;
import java.io.IOException;

/**
 * Created by cgspine on 16/7/21.
 */
public class Util {
    public static void close(Closeable c){
        try {
            if(c != null){
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
