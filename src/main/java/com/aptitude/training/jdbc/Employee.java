package com.aptitude.training.jdbc;

public class Employee {
    String name;
    String position;
    String city;
    int managerId;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public Employee(String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", city='" + city + '\'' +
                ", managerId=" + managerId +
                ", id=" + id +
                ", salary=" + salary +
                '}';
    }

    public Employee(String name, String position, String city, int managerId, int salary) {
        this.name = name;
        this.position = position;
        this.city = city;
        this.managerId = managerId;
        this.salary = salary;
    }

    public Employee(String name) {

        this.name = name;
    }

    public Employee() {

    }

    public Employee(String name, String position, String city, int salary) {

        this.name = name;
        this.position = position;
        this.city = city;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    int salary;
}
