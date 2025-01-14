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
    private static class Bucket {
        MapEntry entry;
        Bucket next;

        Bucket(MapEntry entry) {
            this.entry = entry;
            this.next = null;
        }
    }

    private Bucket[] table;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public MyMap() {
        table = new Bucket[INITIAL_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object get(Object k) {
        int index = hash(k);
        Bucket current = table[index];
        while (current != null) {
            if (current.entry.key.equals(k)) {
                return current.entry.value;
            }
            current = current.next;
        }
        return null;
    }

    public Object put(Object k, Object v) {
        int index = hash(k);
        Bucket current = table[index];
        while (current != null) {
            if (current.entry.key.equals(k)) {
                Object oldValue = current.entry.value;
                current.entry.value = v;
                return oldValue;
            }
            current = current.next;
        }

        Bucket newBucket = new Bucket(new MapEntry(k, v));
        newBucket.next = table[index];
        table[index] = newBucket;
        size++;
        return null;
    }

    public Object remove(Object k) {
        int index = hash(k);
        Bucket current = table[index];
        Bucket previous = null;

        while (current != null) {
            if (current.entry.key.equals(k)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.entry.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public Object[] keySet() {
        Object[] keys = new Object[size];
        int idx = 0;
        for (Bucket bucket : table) {
            Bucket current = bucket;
            while (current != null) {
                keys[idx++] = current.entry.key;
                current = current.next;
            }
        }
        return keys;
    }

    public Object[] values() {
        Object[] values = new Object[size];
        int idx = 0;
        for (Bucket bucket : table) {
            Bucket current = bucket;
            while (current != null) {
                values[idx++] = current.entry.value;
                current = current.next;
            }
        }
        return values;
    }

    public MapEntry[] entrySet() {
        MapEntry[] entries = new MapEntry[size];
        int idx = 0;
        for (Bucket bucket : table) {
            Bucket current = bucket;
            while (current != null) {
                entries[idx++] = current.entry;
                current = current.next;
            }
        }
        return entries;
    }

    private int hash(Object k) {
        return Math.abs(k.hashCode()) % table.length;
    }

    public String toString() {
        String result = "Map: { ";
        for (Bucket bucket : table) {
            Bucket current = bucket;
            while (current != null) {
                result += current.entry.key + "=" + current.entry.value + " ";
                current = current.next;
            }
        }
        return result + "}";
    }
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
