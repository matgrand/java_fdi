// Interface for the List ADT
interface List {
    void insert(Object item, int idx);
    Object remove(int idx);
    boolean isEmpty();
    int size();
}

// Implementation of the List ADT using an array
class MyList implements List {
    // complete the implementation
    
}

// Tester class for MyList
public class MyListTesterA {
    public static void main(String[] args) {
        MyList list = new MyList();

        System.out.println("Inserting elements into the list...");
        list.insert("First", 0);
        System.out.println(list);

        list.insert("Second", 1);
        System.out.println(list);

        list.insert("Third", 1);
        System.out.println(list);

        list.insert("Fourth", 2);
        System.out.println(list);

        System.out.println("Removing elements from the list...");
        System.out.println("Removed 2: " + list.remove(2));
        System.out.println(list);

        System.out.println("Removed 0: " + list.remove(0));
        System.out.println(list);

        System.out.println("Is the list empty? " + list.isEmpty());
        System.out.println("List size: " + list.size());
    }
}
