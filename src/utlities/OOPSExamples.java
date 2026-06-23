// ============================================================
// OOPs CONCEPTS EXAMPLES
// Inheritance, Polymorphism, Encapsulation, Abstraction
// ============================================================

// ─────────────────────────────────────────────
// 1. INHERITANCE
// Child class inherits from parent class
// ─────────────────────────────────────────────

class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " is eating");
    }

    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {  // Dog inherits Animal!!

    Dog(String name) {
        super(name);  // calls parent constructor!!
    }

    void bark() {
        System.out.println(name + " is barking");
    }

    // Method Overriding — Runtime Polymorphism!!
    @Override
    void sound() {
        System.out.println(name + " barks!!");
    }
}

class Cat extends Animal {

    Cat(String name) {
        super(name);
    }

    @Override
    void sound() {
        System.out.println(name + " meows!!");
    }
}


// ─────────────────────────────────────────────
// 2. POLYMORPHISM
// ─────────────────────────────────────────────

// Compile time — Method Overloading
class Calculator {

    // same method name — different parameters!!
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}


// ─────────────────────────────────────────────
// 3. ENCAPSULATION
// Hide data using private + getters/setters
// ─────────────────────────────────────────────

class Employee {
    // private — hidden from outside!!
    private int id;
    private String name;
    private double salary;

    // Constructor
    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Getters — read access!!
    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    // Setters — write access!!
    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) {
        // can add validation!!
        if (salary > 0) {
            this.salary = salary;
        } else {
            System.out.println("Salary cannot be negative!!");
        }
    }
}


// ─────────────────────────────────────────────
// 4. ABSTRACTION
// ─────────────────────────────────────────────

// Abstract Class — partial abstraction
abstract class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    // abstract method — no body!!
    abstract double calculateArea();

    // concrete method — has body!!
    void displayColor() {
        System.out.println("Color: " + color);
    }
}

class Circle extends Shape {
    double radius;

    Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    double calculateArea() {
        return length * width;
    }
}


// Interface — full abstraction
interface Drawable {
    void draw();  // abstract — must implement!!

    // default method — Java 8!!
    default void display() {
        System.out.println("Displaying shape!!");
    }
}

interface Resizable {
    void resize(double factor);
}

// implementing multiple interfaces!!
class Square extends Shape implements Drawable, Resizable {
    double side;

    Square(String color, double side) {
        super(color);
        this.side = side;
    }

    @Override
    double calculateArea() {
        return side * side;
    }

    @Override
    public void draw() {
        System.out.println("Drawing square with side: " + side);
    }

    @Override
    public void resize(double factor) {
        side = side * factor;
        System.out.println("Resized side: " + side);
    }
}


// ─────────────────────────────────────────────
// 5. CONSTRUCTOR EXAMPLES
// ─────────────────────────────────────────────

class Person {
    String name;
    int age;

    // Default Constructor
    Person() {
        this.name = "";  // default values!!
        this.age = 0;
    }

    // Parameterized Constructor
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Constructor chaining using this()
    Person(String name) {
        this(name, 0);  // calls parameterized constructor!!
    }
}


// ─────────────────────────────────────────────
// MAIN CLASS — Run all examples
// ─────────────────────────────────────────────

public class OOPSExamples {

    public static void main(String[] args) {

        // ── INHERITANCE ──
        System.out.println("=== Inheritance ===");
        Dog dog = new Dog("Tommy");
        dog.eat();    // inherited from Animal!!
        dog.bark();   // Dog's own method!!
        dog.sound();  // overridden method!!

        Cat cat = new Cat("Kitty");
        cat.eat();    // inherited!!
        cat.sound();  // overridden!!


        // ── POLYMORPHISM — Runtime ──
        System.out.println("\n=== Runtime Polymorphism ===");
        Animal animal1 = new Dog("Tommy");  // parent ref, child object!!
        Animal animal2 = new Cat("Kitty");

        animal1.sound();  // Dog's sound — decided at runtime!!
        animal2.sound();  // Cat's sound — decided at runtime!!


        // ── POLYMORPHISM — Compile time ──
        System.out.println("\n=== Compile time Polymorphism ===");
        Calculator calc = new Calculator();
        System.out.println(calc.add(2, 3));       // 5
        System.out.println(calc.add(2, 3, 4));    // 9
        System.out.println(calc.add(2.5, 3.5));   // 6.0


        // ── ENCAPSULATION ──
        System.out.println("\n=== Encapsulation ===");
        Employee emp = new Employee(1, "Manjunath", 75000);
        System.out.println("Name: " + emp.getName());
        System.out.println("Salary: " + emp.getSalary());

        emp.setSalary(80000);  // update salary!!
        System.out.println("Updated salary: " + emp.getSalary());

        emp.setSalary(-1000);  // validation kicks in!!


        // ── ABSTRACTION — Abstract class ──
        System.out.println("\n=== Abstraction ===");
        Circle circle = new Circle("Red", 5.0);
        circle.displayColor();  // concrete method!!
        System.out.println("Circle area: " + circle.calculateArea());

        Rectangle rect = new Rectangle("Blue", 4.0, 6.0);
        System.out.println("Rectangle area: " + rect.calculateArea());


        // ── ABSTRACTION — Interface ──
        System.out.println("\n=== Interface ===");
        Square square = new Square("Green", 4.0);
        square.draw();
        square.display();   // default method!!
        square.resize(2.0);
        System.out.println("Square area: " + square.calculateArea());


        // ── CONSTRUCTOR ──
        System.out.println("\n=== Constructor ===");
        Person p1 = new Person();                    // default!!
        Person p2 = new Person("Manjunath", 25);    // parameterized!!
        Person p3 = new Person("Raj");               // constructor chaining!!

        System.out.println("P1: " + p1.name + ", " + p1.age);
        System.out.println("P2: " + p2.name + ", " + p2.age);
        System.out.println("P3: " + p3.name + ", " + p3.age);


        // ── this vs super ──
        System.out.println("\n=== this vs super ===");
        Dog d = new Dog("Bruno");
        d.sound();  // uses @Override — child method!!
        // super.sound() would call parent method!!
    }
}
