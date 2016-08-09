import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * Created by cgspine on 16/8/9.
 */
public class AnnotationDemo {

    public static void main(String[] args){
        Class<?> cls = App.class;
        for (Method method: cls.getMethods()) {
            MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
            if (methodInfo != null) {
                System.out.println("method name:" + method.getName());
                System.out.println("method author:" + methodInfo.author());
                System.out.println("method version:" + methodInfo.version());
                System.out.println("method date:" + methodInfo.date());
            }
        }

    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Inherited
    public @interface MethodInfo {

        String author() default "cgspine@gmail.com";

        String date();

        int version() default 1;
    }

    public static class App {

        @MethodInfo(author = "1052693298@qq.com",date = "2014/02/14",version = 2)
        public String getAppName() {
            return "JavaTrainDemo";
        }
    }
}
