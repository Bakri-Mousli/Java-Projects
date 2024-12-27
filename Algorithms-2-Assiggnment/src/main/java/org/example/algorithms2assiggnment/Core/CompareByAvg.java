package org.example.algorithms2assiggnment.Core;

import java.util.Comparator;

public class CompareByAvg implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s1.getAverage(), s2.getAverage());
    }
}