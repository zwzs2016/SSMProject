package com.company;

import java.util.Arrays;

public class Person implements Comparable<Person>{
    public int age;
    public String name;
    public Person(String name,int age){
        this.age=age;
        this.name=name;
    }
    public String getname(){
        return this.name;
    }
    public int getage(){
        return this.age;
    }

    @Override
    public int compareTo(Person person) {
        int i=0;
        i=name.compareTo(person.name);
        if (i==0){
            return age-person.age;
        }else {
            return i;
        }
    }

    public static void main(String[] args) {
        Person[] ps=new Person[3];
        ps[0]=new Person("Tom",28);
        ps[1]=new Person("Mike",18);
        ps[2]=new Person("Mike",20);
        Arrays.sort(ps);
        for (Person p:ps) {
            System.out.println(p.getname()+","+p.getage());
        }
    }
}
