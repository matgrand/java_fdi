// Interface for the List ADT
interface List {
    void insert(Object item, int idx);
    Object remove(int idx);
    boolean isEmpty();
    int size();
}

// Implementation of the List ADT using a linked list
class MyList implements List {
    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public MyList() {
        head = null;
        size = 0;
    }

    public void insert(Object item, int idx) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node newNode = new Node(item);
        if (idx == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            for (int i = 0; i < idx - 1; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    public Object remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Object item;
        if (idx == 0) {
            item = head.data;
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < idx - 1; i++) {
                current = current.next;
            }
            item = current.next.data;
            current.next = current.next.next;
        }
        size--;
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public String toString() {
        String result = "List: [ ";
        Node current = head;
        while (current != null) {
            result += current.data + " ";
            current = current.next;
        }
        return result + "] (" + size() + ")";
    }
}

// Tester class for MyList
public class MyListTesterLL {
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
