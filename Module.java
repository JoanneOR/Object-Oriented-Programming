package com.example.demo1;


public class Module {
    private String name;
    private String code;

    private String semester;
    private String grade;





    public Module(String name, String code,String semester) {
        this.name = name;
        this.code = code;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSem() {
        return semester;
    }

    public void setSem(String semester) {
        this.semester = semester;
    }

    public String getGrade() {
        return this.grade;

    }
    public void setGrade(String grade) {
        this.grade = grade;
    }


}
