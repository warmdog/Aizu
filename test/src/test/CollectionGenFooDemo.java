package test;

import java.util.ArrayList;

public class CollectionGenFooDemo {
    public static void main(String args[]) {
        CollectionGenFoo<ArrayList> listFoo = null;
        listFoo = new CollectionGenFoo<ArrayList>(new ArrayList());
        // 出错了,不让这么干。
        // 原来作者写的这个地方有误，需要将listFoo改为listFoo1
        // 需要将CollectionGenFoo<Collection>改为CollectionGenFoo<ArrayList>
        // CollectionGenFoo<Collection> listFoo1 = null;
        // listFoo1=new CollectionGenFoo<ArrayList>(new ArrayList());
        System.out.println("实例化成功!");
    }
}
