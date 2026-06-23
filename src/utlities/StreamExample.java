// ============================================================
// STREAMS AND LAMBDA EXAMPLES
// ============================================================

import java.util.*;
import java.util.stream.*;

public class StreamExamples {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Manjunath", "Raj", "Anil", "Suresh", "Arun");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // ─────────────────────────────────────────────
        // LAMBDA EXAMPLES
        // ─────────────────────────────────────────────

        // 1. Print all names
        System.out.println("=== Print all names ===");
        names.forEach(name -> System.out.println(name));

        // 2. Print names starting with 'A'
        System.out.println("=== Names starting with A ===");
        names.forEach(name -> {
            if (name.startsWith("A")) {
                System.out.println(name);
            }
        });

        // 3. Print even numbers
        System.out.println("=== Even numbers ===");
        numbers.forEach(n -> {
            if (n % 2 == 0) {
                System.out.println(n);
            }
        });

        // 4. Sort using Lambda
        System.out.println("=== Sorted names ===");
        Collections.sort(names, (a, b) -> a.compareTo(b));
        names.forEach(name -> System.out.println(name));


        // ─────────────────────────────────────────────
        // STREAM EXAMPLES
        // ─────────────────────────────────────────────

        // 1. filter() — filter even numbers
        System.out.println("=== Stream filter — even numbers ===");
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(evenNumbers);
        // Output: [2, 4, 6, 8, 10]

        // 2. map() — convert to uppercase
        System.out.println("=== Stream map — uppercase ===");
        List<String> upperNames = names.stream()
                .map(name -> name.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(upperNames);
        // Output: [MANJUNATH, RAJ, ANIL, SURESH, ARUN]

        // 3. filter() + map() combined
        System.out.println("=== filter + map combined ===");
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A"))
                .map(name -> name.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(result);
        // Output: [ANIL, ARUN]

        // 4. count() — count elements
        System.out.println("=== count ===");
        long count = numbers.stream()
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println("Even count: " + count);
        // Output: 5

        // 5. sorted() — sort stream
        System.out.println("=== sorted ===");
        List<Integer> sorted = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(sorted);

        // 6. Collectors.joining() — join strings
        System.out.println("=== joining ===");
        String joined = names.stream()
                .collect(Collectors.joining(", "));
        System.out.println(joined);
        // Output: Manjunath, Raj, Anil, Suresh, Arun

        // 7. Collectors.toSet() — collect to Set
        System.out.println("=== toSet ===");
        Set<String> nameSet = names.stream()
                .collect(Collectors.toSet());
        System.out.println(nameSet);

        // 8. findFirst() — get first element
        System.out.println("=== findFirst ===");
        Optional<String> first = names.stream()
                .filter(name -> name.startsWith("A"))
                .findFirst();
        first.ifPresent(name -> System.out.println("First: " + name));
        // Output: Anil


        // ─────────────────────────────────────────────
        // OPTIONAL EXAMPLES
        // ─────────────────────────────────────────────

        // 1. Optional.of()
        Optional<String> opt1 = Optional.of("Manjunath");
        System.out.println(opt1);
        // Output: Optional[Manjunath]

        // 2. Optional.ofNullable()
        Optional<String> opt2 = Optional.ofNullable(null);
        System.out.println(opt2);
        // Output: Optional.empty

        // 3. isPresent()
        System.out.println(opt1.isPresent()); // true
        System.out.println(opt2.isPresent()); // false

        // 4. ifPresent()
        opt1.ifPresent(name -> System.out.println("Hello " + name));
        // Output: Hello Manjunath

        // 5. orElse() — most important!!
        String name = Optional.ofNullable(null)
                .orElse("Default Name");
        System.out.println(name);
        // Output: Default Name
    }
}
