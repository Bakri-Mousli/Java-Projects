package org.example.algorithms2assiggnment.Core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortUtil {

    public static void mergeSort(List<Student> students, Comparator<Student> comparator) {
        if (students.size() <= 1) return;

        int mid = students.size() / 2;

        // نسخ أجزاء القائمة إلى قوائم جديدة لتجنب تعديل القائمة الأصلية
        List<Student> left = new ArrayList<>(students.subList(0, mid));
        List<Student> right = new ArrayList<>(students.subList(mid, students.size()));

        mergeSort(left, comparator);  // ترتيب الجزء الأيسر
        mergeSort(right, comparator); // ترتيب الجزء الأيمن

        merge(students, left, right, comparator); // دمج الأجزاء المرتبة
    }

    private static void merge(List<Student> students, List<Student> left, List<Student> right, Comparator<Student> comparator) {
        int i = 0, j = 0, k = 0;

        // دمج الجزئين معًا في القائمة الأصلية
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                students.set(k++, left.get(i++));
            } else {
                students.set(k++, right.get(j++));
            }
        }

        // إذا كانت هناك عناصر متبقية في اليسار
        while (i < left.size()) students.set(k++, left.get(i++));
        // إذا كانت هناك عناصر متبقية في اليمين
        while (j < right.size()) students.set(k++, right.get(j++));
    }

    public static void quickSort(List<Student> students, Comparator<Student> comparator) {
        quickSort(students, 0, students.size() - 1, comparator);
    }

    private static void quickSort(List<Student> students, int low, int high, Comparator<Student> comparator) {
        if (low < high) {
            int pi = partition(students, low, high, comparator);
            quickSort(students, low, pi - 1, comparator);
            quickSort(students, pi + 1, high, comparator);
        }
    }

    private static int partition(List<Student> students, int low, int high, Comparator<Student> comparator) {
        Student pivot = students.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(students.get(j), pivot) <= 0) {
                i++;
                swap(students, i, j);
            }
        }
        swap(students, i + 1, high);
        return i + 1;
    }

    private static void swap(List<Student> students, int i, int j) {
        Student temp = students.get(i);
        students.set(i, students.get(j));
        students.set(j, temp);
    }
}

