package ru.stqa.pft.sandbox;

public class MyFirstProgram {
    public static void hello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }

    public static void main(String[] args) {
        String name = "Elena";
        hello(name);

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

        Rectangle r = new Rectangle(5, 7);
        System.out.printf("Площадь прямоугольника со сторонами %.2f и %.2f = %.2f%n", r.a, r.b, r.area());

        Point p1 = new Point(1, 2.5);
        Point p2 = new Point(5, 7.5);

        System.out.println();
        System.out.println("Результат с помощью функции:");
        System.out.printf("Расстояние между точками (%.2f; %.2f) и (%.2f; %.2f) = %.2f%n", p1.x, p1.y, p2.x, p2.y, distance(p1, p2));

        System.out.println();
        System.out.println("Результат с помощью метода класса:");
        System.out.printf("Расстояние между точками (%.2f; %.2f) и (%.2f; %.2f) = %.2f%n", p1.x, p1.y, p2.x, p2.y, p1.distance(p2));
    }
}