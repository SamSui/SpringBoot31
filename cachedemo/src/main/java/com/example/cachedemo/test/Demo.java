package com.example.cachedemo.test;

import java.util.ArrayList;
import java.util.List;

class Outer{
    private int[] data;

    public Outer(int size) {
        this.data = new int[size];
    }

    class Innner{

    }

    Innner createInner() {
        return new Innner();
    }
}

public class Demo {
    public static void main(String[] args) {
        ArrayList s = new ArrayList();
        String str = "wewer";
        str.length();
        Outer.Innner inner = new Outer(10).createInner();
        System.out.println(inner);

//        List<Object> list = new ArrayList<>();
//        int counter = 0;
//        while (true) {
//            list.add(new Outer(100000).createInner());
//            System.out.println(counter++);
//        }
    }
}