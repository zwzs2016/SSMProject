package com.uwan.SSM.AppBeans;

public class Toyota implements Onecar,Twocar {
    private int id;

    public Toyota(){
        System.out.println("已经构造了");
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OneCar{" +
                "id=" + id +
                '}';
    }

    @Override
    public void f1() {
        System.out.println("方法f1");
    }

    @Override
    public void f2() {
        System.out.println("方法f2");
    }

    @Override
    public void f3() {
        System.out.println("方法f3");
    }

    @Override
    public void fun4() {
        System.out.println("方法f4");
    }
}
