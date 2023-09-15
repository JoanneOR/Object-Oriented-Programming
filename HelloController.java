package com.example.demo1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelloController {
    private String name;
    private String studentID;
    private String dateBirth;

    private String semester;


    private final ArrayList<Module> modules;

    private Map<Module, String> grades = new HashMap<>();


    public HelloController(String name, String studentID, String dateBirth, String semester) {
        this.name = name;
        this.studentID = studentID;
        this.dateBirth = dateBirth;
        this.semester = semester;
        this.modules = new ArrayList<Module>();


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getSemester(){return semester;}

    public void setSemester(String semester) {
        this.semester = semester;
    }



    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HelloController student = (HelloController) o;
        return Objects.equals(name, student.name) && Objects.equals(studentID, student.studentID) && Objects.equals(dateBirth, student.dateBirth);
    }

    public String toString() {
        return "Student:" + " Name ='" + name + '\'' + ", Student ID ='" + studentID + '\'' + ", Date of Birth ='" + dateBirth + '\'';
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void addGrade(Module module, String grade) {
        grades.put(module, grade);
    }

    public void modifyGrade(Module module, String newGrade) {
        if (grades.containsKey(module)) {
            grades.put(module, newGrade);
        }
    }
}

