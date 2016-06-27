package test11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class CollectionGenFooDemo {

	public static void main(String args[]) {
        CollectionGenFoo<ArrayList> listFoo = null;
        listFoo = new CollectionGenFoo<ArrayList>(new ArrayList());
        // 出错了,不让这么干。
        // 原来作者写的这个地方有误，需要将listFoo改为listFoo1
        // 需要将CollectionGenFoo<Collection>改为CollectionGenFoo<ArrayList>
         CollectionGenFoo<? extends Collection> listFoo1 = null;
         listFoo1=new CollectionGenFoo<ArrayList>(new ArrayList());
        int a = (int)(Math.random()*4);
        System.out.println(a);
        System.out.println("实例化成功!");
        a  aa1 = new a();
        List b=  new ArrayList<>();
        b.add(0,"dwaa");
        b.add(1,"dwawaaa");
        b.add("dwdwa");
        b.add(1222);
         System.out.println(aa1.aa(b));
    }
}
