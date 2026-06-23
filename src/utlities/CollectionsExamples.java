// ============================================================
// COLLECTIONS EXAMPLES
// List, Set, Map with all methods
// ============================================================

import java.util.*;

public class CollectionsExamples {

    public static void main(String[] args) {

        // ─────────────────────────────────────────────
        // LIST — ArrayList
        // Duplicates allowed, insertion order maintained
        // ─────────────────────────────────────────────
        System.out.println("=== ArrayList Examples ===");

        List<String> arrayList = new ArrayList<>();

        // add()
        arrayList.add("Manjunath");
        arrayList.add("Raj");
        arrayList.add("Anil");
        arrayList.add("Manjunath"); // duplicate allowed!!
        System.out.println("After add: " + arrayList);
        // Output: [Manjunath, Raj, Anil, Manjunath]

        // get()
        System.out.println("Get index 0: " + arrayList.get(0));
        // Output: Manjunath

        // set() — update
        arrayList.set(0, "Suresh");
        System.out.println("After set: " + arrayList);

        // remove() by index
        arrayList.remove(0);
        System.out.println("After remove by index: " + arrayList);

        // remove() by value
        arrayList.remove("Anil");
        System.out.println("After remove by value: " + arrayList);

        // contains()
        System.out.println("Contains Raj: " + arrayList.contains("Raj"));

        // size()
        System.out.println("Size: " + arrayList.size());

        // isEmpty()
        System.out.println("Is empty: " + arrayList.isEmpty());

        // indexOf()
        System.out.println("Index of Raj: " + arrayList.indexOf("Raj"));

        // Collections.sort()
        arrayList.add("Manjunath");
        arrayList.add("Anil");
        Collections.sort(arrayList);
        System.out.println("After sort: " + arrayList);

        // clear()
        arrayList.clear();
        System.out.println("After clear: " + arrayList);


        // ─────────────────────────────────────────────
        // LIST — LinkedList
        // Fast insert/delete, slow retrieval
        // ─────────────────────────────────────────────
        System.out.println("\n=== LinkedList Examples ===");

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("Manjunath");
        linkedList.add("Raj");
        linkedList.addFirst("First");  // add at beginning!!
        linkedList.addLast("Last");    // add at end!!
        System.out.println("LinkedList: " + linkedList);

        linkedList.removeFirst(); // remove first!!
        linkedList.removeLast();  // remove last!!
        System.out.println("After remove: " + linkedList);


        // ─────────────────────────────────────────────
        // SET — HashSet
        // No duplicates, no order
        // ─────────────────────────────────────────────
        System.out.println("\n=== HashSet Examples ===");

        Set<String> hashSet = new HashSet<>();
        hashSet.add("Manjunath");
        hashSet.add("Raj");
        hashSet.add("Anil");
        hashSet.add("Manjunath"); // duplicate ignored!!
        System.out.println("HashSet: " + hashSet);
        // Output: random order, no duplicate!!

        System.out.println("Contains Raj: " + hashSet.contains("Raj"));
        System.out.println("Size: " + hashSet.size());

        hashSet.remove("Raj");
        System.out.println("After remove: " + hashSet);


        // ─────────────────────────────────────────────
        // SET — LinkedHashSet
        // No duplicates, insertion order maintained
        // ─────────────────────────────────────────────
        System.out.println("\n=== LinkedHashSet Examples ===");

        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Manjunath");
        linkedHashSet.add("Raj");
        linkedHashSet.add("Anil");
        linkedHashSet.add("Manjunath"); // duplicate ignored!!
        System.out.println("LinkedHashSet: " + linkedHashSet);
        // Output: [Manjunath, Raj, Anil] — insertion order!!


        // ─────────────────────────────────────────────
        // SET — TreeSet
        // No duplicates, sorted order, no null
        // ─────────────────────────────────────────────
        System.out.println("\n=== TreeSet Examples ===");

        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Manjunath");
        treeSet.add("Raj");
        treeSet.add("Anil");
        System.out.println("TreeSet: " + treeSet);
        // Output: [Anil, Manjunath, Raj] — sorted!!


        // ─────────────────────────────────────────────
        // MAP — HashMap
        // Key-value pairs, no order, one null key
        // ─────────────────────────────────────────────
        System.out.println("\n=== HashMap Examples ===");

        Map<Integer, String> hashMap = new HashMap<>();

        // put()
        hashMap.put(1, "Manjunath");
        hashMap.put(2, "Raj");
        hashMap.put(3, "Anil");
        hashMap.put(1, "Suresh"); // key 1 overwritten!!
        System.out.println("HashMap: " + hashMap);

        // get()
        System.out.println("Get key 1: " + hashMap.get(1));
        // Output: Suresh

        // containsKey()
        System.out.println("Contains key 2: " + hashMap.containsKey(2));

        // containsValue()
        System.out.println("Contains value Raj: " + hashMap.containsValue("Raj"));

        // keySet()
        System.out.println("Keys: " + hashMap.keySet());

        // values()
        System.out.println("Values: " + hashMap.values());

        // entrySet() — iterate map!!
        System.out.println("=== Iterate using entrySet ===");
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // size()
        System.out.println("Size: " + hashMap.size());

        // remove()
        hashMap.remove(3);
        System.out.println("After remove: " + hashMap);

        // isEmpty()
        System.out.println("Is empty: " + hashMap.isEmpty());


        // ─────────────────────────────────────────────
        // MAP — LinkedHashMap
        // Key-value pairs, insertion order maintained
        // ─────────────────────────────────────────────
        System.out.println("\n=== LinkedHashMap Examples ===");

        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(3, "Manjunath");
        linkedHashMap.put(1, "Raj");
        linkedHashMap.put(2, "Anil");
        System.out.println("LinkedHashMap: " + linkedHashMap);
        // Output: {3=Manjunath, 1=Raj, 2=Anil} — insertion order!!


        // ─────────────────────────────────────────────
        // MAP — TreeMap
        // Key-value pairs, sorted by key, no null key
        // ─────────────────────────────────────────────
        System.out.println("\n=== TreeMap Examples ===");

        Map<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "Manjunath");
        treeMap.put(1, "Raj");
        treeMap.put(2, "Anil");
        System.out.println("TreeMap: " + treeMap);
        // Output: {1=Raj, 2=Anil, 3=Manjunath} — sorted by key!!
    }
}
