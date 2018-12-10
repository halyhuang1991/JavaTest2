package com.Refretor;

@MyAnnotation(key = "name", value = "ceshi")
public class Info {
    @MyAnnotation(key = "id", value = "ceshi")
    private Integer id;
    private String name;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @MyAnnotation(key = "setName", value = "ceshi")
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Info{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + '}';
    }
}