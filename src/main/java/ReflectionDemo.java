import java.lang.reflect.*;

/**
 * Created by cgspine on 16/7/22.
 */
public class ReflectionDemo {
    public static void main(String[] args){
        //正常流程
//        Person person = new Person("cgspine", 23);
//        person.sayHi();


        try {
            //获取对象
//        Class<Person> personClass = Person.class;
//        Class<?> personClass = person.getClass();
            Class<?> personClass = Class.forName("Person");

            // 获取Constructor :: getConstructor/getDeclaredConstructor
            Constructor constructor = personClass.getDeclaredConstructor(String.class, int.class);

            // 获取实例
            Person person = (Person) constructor.newInstance("cgspine", 23);
//            person.sayHi();

            // 获取属性
//            Field name = personClass.getDeclaredField("mName");
//            name.setAccessible(true);
//            name.set(person,"CGS");
//            person.sayHi();

            // 获取方法
            Method secret = personClass.getDeclaredMethod("secret");
            secret.setAccessible(true);
            secret.invoke(person);
            System.out.println(secret.getName() + "is private " + Modifier.isPrivate(secret.getModifiers()));



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
