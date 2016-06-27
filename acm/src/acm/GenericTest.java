package acm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("qqyumidi");
        list.add("corn");
       // list.add(100);
        List<?>[] lsa = new ArrayList<?>[10];
        for (int i = 0; i < list.size(); i++) {
            String name = (String) list.get(i); // 1
            System.out.println("name:" + name);
        }
        Integer a =10;
        Gen<Integer> intOb = new Gen<Integer>(a);
        intOb.showType();
        int i = intOb.getOb();
        System.out.println("value= " + i);
        System.out.println("----------------------------------");
        // 定义泛型类Gen的一个String版本
        Gen<String> strOb = new Gen<String>("Hello Gen!");
        strOb.showType();
        String s = strOb.getOb();
        System.out.println("value= " + s);
    }

}
class Gen<T> {
    private T ob; // 定义泛型成员变量

    public Gen(T ob) {
        this.ob = ob;
    }

    public T getOb() {
        return ob;
    }

    public void setOb(T ob) {
        this.ob = ob;
    }

    public void showType() {
        System.out.println("T的实际类型是: " + ob.getClass().getName());
    }
}
