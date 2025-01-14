// Interface for the Map ADT
interface Map {
    int size();
    boolean isEmpty();
    Object get(Object k);
    Object put(Object k, Object v);
    Object remove(Object k);
    Object[] keySet();
    Object[] values();
    MapEntry[] entrySet();
}

// Entry class to store key-value pairs
class MapEntry {
    Object key;
    Object value;

    MapEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}

// Implementation of the Map ADT using a hash table
class MyMap implements Map {
    // complete the implementation
    
}

// Tester class for MyMap
public class MyMapTester {
    public static void main(String[] args) {
        MyMap map = new MyMap();

        System.out.println("Adding entries to the map...");
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);
        System.out.println(map);

        System.out.println("Getting value for key 'Second': " + map.get("Second"));

        System.out.println("Removing entry with key 'First'...");
        map.remove("First");
        System.out.println(map);

        System.out.print("Key set: ");
        for (Object key : map.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        System.out.print("Values: ");
        for (Object value : map.values()) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.print("Entry set: ");
        for (MapEntry entry : map.entrySet()) {
            System.out.print(entry.key + "=" + entry.value + " ");
        }
        System.out.println();

        System.out.println("Map size: " + map.size());
        System.out.println("Is map empty? " + map.isEmpty());

        System.out.println("Adding duplicate key 'Second'...");
        map.put("Second", 22);
        System.out.println(map);

        System.out.println("Add duplicate value '3'...");
        map.put("THIRD", 3);

        System.out.println(map);

        System.out.println("Map size: " + map.size());

    }
}
