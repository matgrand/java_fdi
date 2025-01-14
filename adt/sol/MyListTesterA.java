// Interface for the List ADT
interface List {
    void insert(Object item, int idx);
    Object remove(int idx);
    boolean isEmpty();
    int size();
}

// Implementation of the List ADT using an array
class MyList implements List {
    private Object[] list;
    private int size;
    private static final int INITIAL_CAPACITY = 1;

    public MyList() {
        list = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void insert(Object item, int idx) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (size == list.length) {
            resize();  // Resize the array if it's full
        }
        // Shift elements to the right
        for (int i = size; i > idx; i--) {
            list[i] = list[i - 1];
        }
        list[idx] = item;
        size++;
    }

    public Object remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Object item = list[idx];
        // Shift elements to the left
        for (int i = idx; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        list[size - 1] = null;  // Nullify the last element
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = list.length * 2;
        Object[] newList = new Object[newCapacity];
        System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
    }

    public String toString() {
        String result = "List: [ ";
        for (int i = 0; i < size; i++) {
            result += list[i] + " ";
        }
        return result + "] (" + size() + ")";
    }
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
