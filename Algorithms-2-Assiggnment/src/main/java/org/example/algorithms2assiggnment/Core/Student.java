package org.example.algorithms2assiggnment.Core;

public class Student {
    private final String name;
    private final int id;
    private final double average;

    public Student(String name, int id, double average) {
        this.name = name;
        this.id = id;
        this.average = average;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public double getAverage() { return average; }

}
