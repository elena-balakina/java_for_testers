package ru.stqa.pft.sandbox;

public class MyFirstProgram {
    public static void hello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public static double area(double l) {
        return l * l;
    }

    public static double area(double a, double b) {
        return a * b;
    }

    public static void main(String[] args) {
        String name = "Elena";
        double length = 5;
        double a = 5;
        double b = 7;

        hello(name);
        System.out.println("Площадь квадрата со стороной " + length + " = " + area(length));
        System.out.printf("Площадь прямоугольника со сторонами %.2f и %.2f = %.2f", a, b, area(a, b));
    }
}