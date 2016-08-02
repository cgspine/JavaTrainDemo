/**
 * Created by cgspine on 16/7/22.
 */
public class Person {
    private String mName;
    private int mAge;

    public Person(String name, int age){
        mName = name;
        mAge = age;
    }

    public void sayHi(){
        System.out.print("Hi! my name is " + mName + ", and I am " + mAge + " years old");
    }

    private void secret(){
        System.out.println("除了偷窥,你看不到我的秘密");
    }
}
