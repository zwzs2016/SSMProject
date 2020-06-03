package com.uwan.SSM.AppBeans;

public class Person {
    private Onecar car;
    private Twocar car2;
    public Person(Onecar onecar,Twocar twocar){
        car=onecar;
        car2=twocar;
    }
    public void drive(){
        car.f1();
        car.f2();
        car.f3();
        car2.fun4();
    }

}
