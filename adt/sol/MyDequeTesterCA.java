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

// Implementation of the Deque ADT using a circular array
class MyDeque implements Deque {
    private Object[] deque;
    private int front;
    private int rear;
    private int size;
    private static final int INITIAL_CAPACITY = 1;

    public MyDeque() {
        deque = new Object[INITIAL_CAPACITY];
        front = 0;
        rear = 0;
        size = 0;
    }

    public void addFirst(Object e) {
        if (size == deque.length) {
            resize();
        }
        front = (front - 1 + deque.length) % deque.length; // Circular decrement
        deque[front] = e;
        size++;
    }

    public void addLast(Object e) {
        if (size == deque.length) {
            resize();
        }
        deque[rear] = e;
        rear = (rear + 1) % deque.length;
        size++;
    }

    public Object removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Object item = deque[front];
        front = (front + 1) % deque.length;
        size--;
        return item;
    }

    public Object removeLast() {
        if (isEmpty()) {
            return null;
        }
        rear = (rear - 1 + deque.length) % deque.length;
        Object item = deque[rear];
        size--;
        return item;
    }

    public Object first() {
        return isEmpty() ? null : deque[front];
    }

    public Object last() {
        return isEmpty() ? null : deque[(rear - 1 + deque.length) % deque.length];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        int newCapacity = deque.length * 2;
        Object[] newDeque = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newDeque[i] = deque[(front + i) % deque.length];
        }
        deque = newDeque;
        front = 0;
        rear = size;
    }

    public String toString() {
        String result = "Deque: [ ";
        for (int i = 0; i < size; i++) {
            result += deque[(front + i) % deque.length] + " ";
        }
        return result + "] (" + size() + ")";
    }
}

// Tester class for MyDeque
public class MyDequeTesterCA {
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
