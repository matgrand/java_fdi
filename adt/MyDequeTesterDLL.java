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
    // complete the implementation
    
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
