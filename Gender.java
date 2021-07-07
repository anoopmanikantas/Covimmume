package com.example.covimmune;

public class Gender {
    private String sex;

    public Gender(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString(){
        return sex;
    }
}
