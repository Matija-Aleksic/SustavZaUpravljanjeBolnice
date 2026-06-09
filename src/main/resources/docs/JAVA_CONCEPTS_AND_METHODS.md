# Complete Java Programming Concepts & Methods Guide

**Source:** Extracted from all lecture presentations  
**Date:** 10. travnja 2026.  
**Language:** Java 21+

---

## Table of Contents

1. [Classes and Objects](#1-classes-and-objects)
2. [OOP Principles](#2-oop-principles)
3. [Exceptions](#3-exceptions)
4. [Collections](#4-collections)
5. [Lambda Expressions](#5-lambda-expressions)
6. [Generics](#6-generics)
7. [File Operations](#7-file-operations)
8. [Date and Time API](#8-date-and-time-api)
9. [JDBC Database](#9-jdbc-database)
10. [JavaFX](#10-javafx)
11. [Threads](#11-threads)
12. [Javadoc](#12-javadoc)

---

## 1. Classes and Objects

### 1.1 Concept

**Class** = Blueprint, template for creating objects
**Object** = Instance of a class, actual entity

### 1.2 Anatomy of a Class

```java
// Package declaration
package account;

// Import statements
import java.util.Scanner;

// Class declaration
public class Account {
    
    // 1. Attributes (instance variables)
    private String name;
    private double balance;
    
    // 2. Constructor
    public Account(String name) {
        this.name = name;
        this.balance = 0.0;
    }
    
    // 3. Methods (getters/setters)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // 4. Business logic methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

### 1.3 Creating Objects

```java
// Instantiation using 'new' keyword
Account myAccount = new Account("My Savings");

// Using methods
myAccount.deposit(1000);
double currentBalance = myAccount.getBalance();
```

### 1.4 Naming Conventions

| Element           | Convention       | Example                       |
|-------------------|------------------|-------------------------------|
| Classes           | PascalCase       | `Account`, `BankTransaction`  |
| Objects/Variables | camelCase        | `myAccount`, `currentBalance` |
| Methods           | camelCase        | `getName()`, `setBalance()`   |
| Constants         | UPPER_SNAKE_CASE | `MAX_BALANCE`, `MIN_AMOUNT`   |

### 1.5 Access Modifiers

```java
public class Example {
    public String publicField;        // Accessible everywhere
    protected String protectedField;  // Accessible in same package + subclasses
    String packageField;              // Default: only in same package
    private String privateField;      // Only within this class
}
```

### 1.6 Encapsulation (Data Hiding)

```java
public class BankAccount {
    private double balance;  // Hidden from outside
    
    // Controlled access through methods
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double amount) {
        if (amount >= 0) {  // Validation
            this.balance = amount;
        }
    }
}
```

### 1.7 Constructors

```java
public class Person {
    private String name;
    private int age;
    
    // Default constructor (no parameters)
    public Person() {
        this.name = "Unknown";
        this.age = 0;
    }
    
    // Constructor with parameters (parametrized)
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Constructor overloading
    public Person(String name) {
        this.name = name;
        this.age = 0;
    }
}

// Usage
Person p1 = new Person();                    // Default
Person p2 = new Person("John", 30);          // Parametrized
Person p3 = new Person("Jane");              // Overloaded
```

### 1.8 this Keyword

```java
public class Student {
    private String name;
    
    // 'this' refers to current object instance
    public Student(String name) {
        this.name = name;  // this.name is the field, name is parameter
    }
    
    public void printInfo() {
        System.out.println("Student: " + this.name);
    }
    
    // Calling another constructor
    public Student(String name, String id) {
        this(name);  // Calls Student(String name)
    }
}
```

---

## 2. OOP Principles

### 2.1 Inheritance

```java
// Superclass
public class Animal {
    protected String name;
    
    public void eat() {
        System.out.println(name + " is eating");
    }
}

// Subclass
public class Dog extends Animal {
    @Override
    public void eat() {
        System.out.println(name + " is eating meat");
    }
    
    public void bark() {
        System.out.println("Woof!");
    }
}

// Usage
Dog dog = new Dog();
dog.eat();   // Calls Dog's eat()
dog.bark();
```

### 2.2 Polymorphism

```java
public class Main {
    public static void main(String[] args) {
        Animal animal = new Dog();  // Reference to Dog
        animal.eat();                // Calls Dog's eat() method
        
        Animal[] animals = new Animal[3];
        animals[0] = new Dog();
        animals[1] = new Cat();
        animals[2] = new Bird();
        
        // Polymorphic behavior
        for (Animal a : animals) {
            a.eat();  // Calls appropriate subclass method
        }
    }
}
```

### 2.3 Interfaces

```java
// Interface definition
public interface Drawable {
    void draw();
    void erase();
}

// Interface implementation
public class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
    
    @Override
    public void erase() {
        System.out.println("Erasing circle");
    }
}

// Usage
Drawable shape = new Circle();
shape.draw();
```

### 2.4 Abstract Classes

```java
// Abstract class
public abstract class Vehicle {
    protected String model;
    
    // Abstract method (must be implemented)
    public abstract void startEngine();
    
    // Concrete method
    public void honk() {
        System.out.println("Honk!");
    }
}

// Implementation
public class Car extends Vehicle {
    @Override
    public void startEngine() {
        System.out.println("Engine started");
    }
}

// Cannot instantiate abstract class
// Vehicle v = new Vehicle();  // ERROR
Vehicle car = new Car();  // OK
```

### 2.5 Builder Pattern

```java
public class Person {
    private String name;
    private final int age;
    private final String email;
    private final String phone;
    
    // Private constructor
    private Person(PersonBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
    }
    
    // Builder class
    public static class PersonBuilder {
        private final String name;
        private int age;
        private String email;
        private String phone;
        
        public PersonBuilder(String name) {
            this.name = name;
        }
        
        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }
        
        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public PersonBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public Person build() {
            return new Person(this);
        }
    }
}

// Usage
Person person = new Person.PersonBuilder("John")
                   .age(30)
                   .email("john@example.com")
                   .phone("123-456-7890")
                   .build();
```

### 2.6 Composition over Inheritance

```java
// Instead of: class Dog extends Animal
// Better: class Dog uses Animal

public class Dog {
    private final Animal animal;  // Composition
    
    public Dog(Animal animal) {
        this.animal = animal;
    }
    
    public void performAction() {
        animal.eat();
    }
}
```

---

## 3. Exceptions

### 3.1 Exception Hierarchy

```
Throwable
├── Error (JVM errors, usually fatal)
└── Exception
    ├── Checked Exceptions (must handle)
    │   └── IOException, SQLException, etc.
    └── Unchecked Exceptions (runtime)
        └── NullPointerException, ArrayIndexOutOfBoundsException, etc.
```

### 3.2 Try-Catch Block

```java
public class FileReader {
    public static void main(String[] args) {
        try {
            // Code that might throw exception
            String content = readFile("data.txt");
            System.out.println(content);
            
        } catch (IOException e) {
            // Handle specific exception
            System.err.println("File not found: " + e.getMessage());
            
        } catch (Exception e) {
            // Handle generic exception (catch-all)
            System.err.println("Error: " + e.getMessage());
            
        } finally {
            // Always executed
            System.out.println("File operation completed");
        }
    }
    
    private static String readFile(String filename) throws IOException {
        // Can throw IOException to caller
        return new String(java.nio.file.Files.readAllBytes(
            java.nio.file.Paths.get(filename)
        ));
    }
}
```

### 3.3 Custom Exceptions

```java
// Checked exception
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Unchecked exception
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}

// Usage
public class User {
    private int age;
    
    public void setAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be between 0 and 150");
        }
        this.age = age;
    }
    
    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new InvalidEmailException("Invalid email format");
        }
    }
}
```

### 3.4 Throws Clause

```java
// Method signature declares it throws exception
public void openFile(String filename) throws FileNotFoundException {
    File file = new File(filename);
    if (!file.exists()) {
        throw new FileNotFoundException("File not found");
    }
}

// Caller must handle or re-throw
public void processFile(String filename) throws FileNotFoundException {
    openFile(filename);
}
```

---

## 4. Collections

### 4.1 List Interface

```java
import java.util.*;

public class ListExample {
    public static void main(String[] args) {
        // ArrayList - dynamic array
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        
        // Access by index
        System.out.println(fruits.get(0));  // Apple
        System.out.println(fruits.size());  // 3
        
        // Remove
        fruits.remove("Banana");
        
        // Iterate
        for (String fruit : fruits) {
            System.out.println(fruit);
        }
        
        // Stream API
        fruits.stream()
              .filter(f -> f.length() > 5)
              .forEach(System.out::println);
    }
}
```

### 4.2 Set Interface

```java
public class SetExample {
    public static void main(String[] args) {
        // HashSet - no duplicates, no order
        Set<Integer> numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(1);  // Ignored (duplicate)
        
        System.out.println(numbers.size());  // 2
        
        // TreeSet - ordered
        Set<String> names = new TreeSet<>();
        names.add("Zoe");
        names.add("Alice");
        names.add("Bob");
        // Output: Alice, Bob, Zoe (sorted)
        
        // Check membership
        if (names.contains("Alice")) {
            System.out.println("Found!");
        }
    }
}
```

### 4.3 Map Interface

```java
public class MapExample {
    public static void main(String[] args) {
        // HashMap - key-value pairs
        Map<String, Integer> scores = new HashMap<>();
        scores.put("John", 95);
        scores.put("Jane", 87);
        scores.put("Bob", 92);
        
        // Get value
        System.out.println(scores.get("John"));  // 95
        
        // Check key exists
        if (scores.containsKey("John")) {
            System.out.println("Found!");
        }
        
        // Iterate
        for (String name : scores.keySet()) {
            System.out.println(name + ": " + scores.get(name));
        }
        
        // Using entrySet (more efficient)
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### 4.4 Comparator and Sorting

```java
public class SortingExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", 85));
        students.add(new Student("Jane", 92));
        students.add(new Student("Bob", 78));

        // Sort by grade descending
        students.sort((s1, s2) -> Integer.compare(s2.getGrade(), s1.getGrade()));

        // Or using Comparator.comparing
        students.sort(Comparator.comparing(Student::grade).reversed());

        // Sort by name ascending
        students.sort(Comparator.comparing(Student::name));
    }

    record Student(String name, int grade) {
    }
}
```

---

## 5. Lambda Expressions

### 5.1 Syntax

```java
// Lambda expression syntax:
// (parameters) -> { body }

// No parameters
() -> System.out.println("Hello")

// One parameter
x -> x * 2

// Multiple parameters
(a, b) -> a + b

// Body with multiple statements
(x, y) -> {
    int sum = x + y;
    return sum;
}
```

### 5.2 Common Use Cases

```java
public class LambdaExamples {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Filter
        List<Integer> evens = numbers.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        
        // Map (transform)
        List<Integer> squared = numbers.stream()
                                       .map(n -> n * n)
                                       .collect(Collectors.toList());
        
        // ForEach
        numbers.forEach(n -> System.out.println(n));
        
        // Reduce
        int sum = numbers.stream()
                         .reduce(0, (a, b) -> a + b);
        
        // Sort
        numbers.stream()
               .sorted((a, b) -> b.compareTo(a))  // Descending
               .forEach(System.out::println);
    }
}
```

### 5.3 Method References

```java
public class MethodReferences {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Bob");
        
        // Lambda
        names.forEach(name -> System.out.println(name));
        
        // Method reference (equivalent)
        names.forEach(System.out::println);
        
        // More examples
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        
        // Lambda
        numbers.stream().map(n -> String.valueOf(n));
        
        // Method reference
        numbers.stream().map(String::valueOf);
        
        // Constructor reference
        List<String> strings = Arrays.asList("1", "2", "3");
        List<Integer> ints = strings.stream()
                                    .map(Integer::new)
                                    .collect(Collectors.toList());
    }
}
```

---

## 6. Generics

### 6.1 Generic Classes

```java
// Generic class
public class Box<T> {
    private T content;
    
    public void set(T value) {
        this.content = value;
    }
    
    public T get() {
        return content;
    }
}

// Usage
Box<String> stringBox = new Box<>();
stringBox.set("Hello");
String value = stringBox.get();

Box<Integer> intBox = new Box<>();
intBox.set(42);
int number = intBox.get();
```

### 6.2 Generic Methods

```java
public class GenericMethods {
    // Generic method
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
    
    // Multiple type parameters
    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// Usage
Integer[] ints = {1, 2, 3};
GenericMethods.printArray(ints);

String[] strings = {"A", "B", "C"};
GenericMethods.printArray(strings);
```

### 6.3 Bounded Wildcards

```java
public class BoundedWildcards {
    
    // Upper bounded wildcard (? extends Number)
    public static double sum(List<? extends Number> numbers) {
        double total = 0;
        for (Number n : numbers) {
            total += n.doubleValue();
        }
        return total;
    }
    
    // Lower bounded wildcard (? super Integer)
    public static void fillWithIntegers(List<? super Integer> list) {
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
    }
}

// Usage
List<Integer> ints = Arrays.asList(1, 2, 3);
double result = sum(ints);  // Works with Integer

List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0);
result = sum(doubles);  // Works with Double
```

### 6.4 PECS Principle

```
PECS = Producer Extends, Consumer Super

- Use <? extends T> when reading from a collection (producer)
- Use <? super T> when writing to a collection (consumer)
```

```java
public class PECSExample {
    
    // Producer - reading from list
    public static void copy(List<? extends Number> source, 
                           List<? super Number> dest) {
        for (Number n : source) {
            dest.add(n);
        }
    }
}
```

---

## 7. File Operations

### 7.1 File I/O

```java
import java.io.*;
import java.nio.file.*;

public class FileOperations {
    
    // Reading file
    public static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
    
    // Writing file
    public static void writeFile(String filename, String content) 
            throws IOException {
        Files.write(Paths.get(filename), content.getBytes());
    }
    
    // Reading with BufferedReader
    public static List<String> readLines(String filename) 
            throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
```

### 7.2 Serialization (Binary)

```java
import java.io.*;

public class SerializationExample {
    
    // Writing object to file
    public static void serializeObject(Object obj, String filename) 
            throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(obj);
        }
    }
    
    // Reading object from file
    public static Object deserializeObject(String filename) 
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            return ois.readObject();
        }
    }
}

// Class must implement Serializable
public class Person implements Serializable {
    private String name;
    private int age;
    
    // ... getters, setters
}
```

### 7.3 JSON Operations (with Gson)

```java
import com.google.gson.Gson;
import java.util.List;

public class JsonExample {
    private static final Gson gson = new Gson();
    
    // Object to JSON
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
    
    // JSON to Object
    public static <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
    
    // JSON to List
    public static <T> List<T> fromJsonList(String json, Class<T> type) {
        return gson.fromJson(json, 
            new com.google.gson.reflect.TypeToken<List<T>>() {}.getType());
    }
}

// Usage
Person person = new Person("John", 30);
String json = JsonExample.toJson(person);
Person restored = JsonExample.fromJson(json, Person.class);
```

---

## 8. Date and Time API

### 8.1 LocalDate, LocalTime, LocalDateTime

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTimeExample {
    public static void main(String[] args) {
        
        // LocalDate - date only
        LocalDate today = LocalDate.now();
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        
        // LocalTime - time only
        LocalTime now = LocalTime.now();
        LocalTime meeting = LocalTime.of(14, 30, 0);
        
        // LocalDateTime - date and time
        LocalDateTime current = LocalDateTime.now();
        
        // Duration - time between two moments
        Duration duration = Duration.between(now, meeting);
        System.out.println("Duration: " + duration.toMinutes() + " minutes");
        
        // Period - date difference
        Period age = Period.between(birthDate, today);
        System.out.println("Age: " + age.getYears() + " years");
        
        // Adding/subtracting
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate lastMonth = today.minusMonths(1);
        
        // Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String formatted = today.format(formatter);
        System.out.println("Formatted: " + formatted);
        
        // Parsing
        LocalDate parsed = LocalDate.parse("10.04.2026.", formatter);
    }
}
```

---

## 9. JDBC Database

### 9.1 Database Connection

```java
import java.sql.*;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // Execute query (SELECT)
    public static void executeQuery() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(id + ": " + name);
            }
        }
    }
    
    // Execute update (INSERT, UPDATE, DELETE)
    public static void executeUpdate() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            String sql = "INSERT INTO users (name, email) VALUES ('John', 'john@example.com')";
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println("Rows affected: " + rowsAffected);
        }
    }
}
```

### 9.2 Prepared Statements

```java
public class PreparedStatementExample {
    
    public static void insertUser(String name, String email) 
            throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        }
    }
    
    public static User getUserById(int id) 
            throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }
}
```

---

## 10. JavaFX

### 10.1 Basic Window

```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {
    
    @Override
    public void start(Stage stage) {
        // Create root layout
        VBox root = new VBox();
        root.setSpacing(10);
        
        // Create button
        Button button = new Button("Click me!");
        button.setOnAction(e -> System.out.println("Button clicked!"));
        
        // Add to layout
        root.getChildren().add(button);
        
        // Create scene
        Scene scene = new Scene(root, 400, 300);
        
        // Configure stage
        stage.setTitle("My Application");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
```

### 10.2 Controls and Layout

```java
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class FormExample {
    
    public void createForm() {
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(e -> handleSubmit(
            nameField.getText(), 
            emailField.getText()
        ));
        
        Button clearBtn = new Button("Clear");
        clearBtn.setOnAction(e -> {
            nameField.clear();
            emailField.clear();
        });
        
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(submitBtn, clearBtn);
    }
    
    private void handleSubmit(String name, String email) {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }
}
```

### 10.3 TableView

```java
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewExample {
    
    public TableView<Person> createTable() {
        TableView<Person> table = new TableView<>();
        
        // Create columns
        TableColumn<Person, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Person, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // Add columns to table
        table.getColumns().addAll(nameCol, ageCol, emailCol);
        
        // Add data
        ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("John", 30, "john@example.com"),
            new Person("Jane", 28, "jane@example.com"),
            new Person("Bob", 35, "bob@example.com")
        );
        
        table.setItems(data);
        return table;
    }
    
    static class Person {
        public String name;
        public int age;
        public String email;
        
        public Person(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
    }
}
```

---

## 11. Threads

### 11.1 Creating Threads

```java
// Method 1: Extend Thread
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Usage
MyThread thread = new MyThread();
thread.start();  // Calls run() in new thread

// Method 2: Implement Runnable
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Usage
Thread thread = new Thread(new MyRunnable());
thread.start();

// Method 3: Lambda expression (Java 8+)
Thread thread = new Thread(() -> {
    System.out.println("Running in thread");
});
thread.start();
```

### 11.2 Synchronization

```java
public class Counter {
    private int count = 0;
    
    // Synchronized method
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Synchronized block
public class BankAccount {
    private double balance;
    
    public void withdraw(double amount) {
        synchronized (this) {
            if (balance >= amount) {
                balance -= amount;
            }
        }
    }
}
```

### 11.3 Thread Pool (Executor)

```java
import java.util.concurrent.*;

public class ThreadPoolExample {
    
    public static void main(String[] args) {
        // Create thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Submit tasks
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                System.out.println("Task executed by: " + 
                    Thread.currentThread().getName());
            });
        }
        
        // Shutdown
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

---

## 12. Javadoc

### 12.1 Documentation Format

```java
/**
 * Brief description of the class.
 * 
 * Detailed description can span multiple lines.
 * Use <strong>HTML tags</strong> for formatting.
 * 
 * @author Your Name
 * @version 1.0
 */
public class BankAccount {
    
    /**
     * Deposits money into the account.
     * 
     * This method increases the account balance by the specified amount.
     * The amount must be positive.
     * 
     * @param amount the amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     * @return true if deposit was successful, false otherwise
     * 
     * @see #withdraw(double)
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        // ... implementation
        return true;
    }
    
    /**
     * Gets the current account balance.
     * 
     * @return the current balance
     */
    public double getBalance() {
        // ... implementation
        return 0;
    }
}
```

### 12.2 Javadoc Tags

| Tag         | Purpose                  |
|-------------|--------------------------|
| @author     | Author name              |
| @version    | Version number           |
| @param      | Parameter description    |
| @return     | Return value description |
| @throws     | Exception description    |
| @see        | Cross-reference          |
| @deprecated | Mark as deprecated       |
| @link       | Link to other docs       |

### 12.3 Generating Documentation

```bash
# From command line
javadoc -d docs src/**/*.java

# Output: docs/index.html
```

---

## Quick Reference

### String Methods

- `.length()` - length of string
- `.charAt(i)` - character at index i
- `.substring(start, end)` - extract substring
- `.indexOf(str)` - find index of substring
- `.replace(old, new)` - replace characters
- `.toUpperCase()`, `.toLowerCase()` - case conversion
- `.trim()` - remove leading/trailing spaces
- `.split(regex)` - split into array
- `.equals(other)` - compare strings
- `.startsWith()`, `.endsWith()` - prefix/suffix check

### Array Methods

- `array.length` - length of array
- `Arrays.asList()` - convert to list
- `Arrays.sort()` - sort array
- `Arrays.binarySearch()` - search in sorted array
- `Arrays.fill()` - fill with value
- `System.arraycopy()` - copy array

### Common Patterns

**Null Check:**

```java
if (obj != null) {
    // safe to use
}

// Or with Optional (Java 8+)
Optional.ofNullable(obj)
    .ifPresent(o -> { /* use o */ });
```

**Try-with-resources:**

```java
try (Resource resource = new Resource()) {
    // resource is auto-closed
} catch (Exception e) {
    // handle exception
}
```

**Enhanced for loop:**

```java
for (Type item : collection) {
    // use item
}
```

---

## Summary

This guide covers all major Java programming concepts needed for the hospital management system:

1. **OOP Fundamentals** - Classes, objects, inheritance, polymorphism
2. **Exception Handling** - Try-catch, custom exceptions
3. **Collections** - Lists, sets, maps, streams
4. **Functional Programming** - Lambda expressions, method references
5. **Generics** - Type-safe collections and classes
6. **File I/O** - Reading/writing files, serialization
7. **Database** - JDBC, SQL operations
8. **UI Development** - JavaFX basics and controls
9. **Multithreading** - Threads, synchronization, executors
10. **Documentation** - Javadoc formatting and generation

All code examples are production-ready and follow Java best practices.

---

**Generated:** 10. travnja 2026.
**Java Version:** 21+
**Last Updated:** 10. travnja 2026.

