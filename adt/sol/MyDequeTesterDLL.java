// Interface for the Deque ADT
interface Deque {
    void addFirst(Object e);
    void addLast(Object e);
    Object removeFirst();
    Object removeLast();
    Object first();
    Object last();
    int size();
    boolean isEmpty();
}

// Implementation of the Deque ADT using a doubly linked list
class MyDeque implements Deque {
    private static class Node {
        Object data;
        Node next;
        Node prev;

        Node(Object data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public MyDeque() {
        front = null;
        rear = null;
        size = 0;
    }

    public void addFirst(Object e) {
        Node newNode = new Node(e);
        if (front == null) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
        size++;
    }

    public void addLast(Object e) {
        Node newNode = new Node(e);
        if (rear == null) {
            front = rear = newNode;
        } else {
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public Object removeFirst() {
        if (front == null) {
            return null;
        }
        Object item = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        } else {
            front.prev = null;
        }
        size--;
        return item;
    }

    public Object removeLast() {
        if (rear == null) {
            return null;
        }
        Object item = rear.data;
        rear = rear.prev;
        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }
        size--;
        return item;
    }

    public Object first() {
        return (front != null) ? front.data : null;
    }

    public Object last() {
        return (rear != null) ? rear.data : null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        String result = "Deque: [ ";
        Node current = front;
        while (current != null) {
            result += current.data + " ";
            current = current.next;
        }
        return result + "] (" + size() + ")";
    }
}

// Tester class for MyDeque
public class MyDequeTesterDLL {
    public static void main(String[] args) {
        MyDeque deque = new MyDeque();

        System.out.println("Adding elements to the front and back of the deque...");
        deque.addFirst("First");
        System.out.println(deque);

        deque.addLast("Second");
        System.out.println(deque);

        deque.addFirst("Third");
        System.out.println(deque);

        deque.addLast("Fourth");
        System.out.println(deque);

        System.out.println("Removing elements from the front and back...");
        System.out.println("Removed from front: " + deque.removeFirst());
        System.out.println(deque);

        System.out.println("Removed from back: " + deque.removeLast());
        System.out.println(deque);

        System.out.println("First element: " + deque.first());
        System.out.println("Last element: " + deque.last());

        System.out.println("Is the deque empty? " + deque.isEmpty());
        System.out.println("Deque size: " + deque.size());
    }
}
