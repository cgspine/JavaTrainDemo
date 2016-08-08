import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by cgspine on 16/8/7.
 */
public class ProxyDemo {
    public static void main(String[] args){
        Operator operator = new OperatorImpl();

        // 没有代理的情况
//        operator.doSomething();

        // 静态代理
//        OperatorProxy proxy = new OperatorProxy(operator);
//        proxy.doSomething();

        // 动态代理
//        OperatorInvocationHandler invocationHandler = new OperatorInvocationHandler(operator);
//        Operator proxy = (Operator) Proxy.newProxyInstance(Operator.class.getClassLoader(), new Class[]{ Operator.class }, invocationHandler);
//        proxy.doSomething();


        // 实际应用
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> repos = service.listRepos("cgspine");
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    public interface Operator{
        void doSomething();
    }

    public static class OperatorImpl implements Operator {
        @Override
        public void doSomething(){
            System.out.println("OperatorImpl doSomeThing");
        }
    }

    public static class OperatorProxy implements Operator {
        private Operator mOperator;

        public OperatorProxy(Operator operator) {
            mOperator = operator;
        }

        @Override
        public void doSomething() {
            System.out.print("OperatorProxy proxy before");
            mOperator.doSomething();
            System.out.print("OperatorProxy proxy after");
        }
    }

    public static class OperatorInvocationHandler implements InvocationHandler {
        private Object mTarget;

        OperatorInvocationHandler(Object object) {
            mTarget = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + ": proxy before");
            Object obj = method.invoke(mTarget, args);
            System.out.println(method.getName() + ": proxy after");
            return obj;
        }
    }


    public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }

    static class Repo {
        String id;
        String name;
        String full_name;
    }
}
