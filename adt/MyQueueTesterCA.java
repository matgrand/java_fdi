// Interface for the Queue ADT
interface Queue {
    void enqueue(Object item);
    Object dequeue();
    int size();
    boolean isEmpty();
}

// Implementation of the Queue ADT using a circular array
class MyQueue implements Queue {
    // complete the implementation
    
}

// Tester class for MyQueue
public class MyQueueTesterCA {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();

        System.out.println("Enqueuing elements into the queue...");
        queue.enqueue("First");
        System.out.println(queue);

        queue.enqueue("Second");
        System.out.println(queue);

        queue.enqueue("Third");
        System.out.println(queue);

        queue.enqueue("Fourth");
        System.out.println(queue);

        System.out.println("Dequeuing elements from the queue...");
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.println(queue);
    }
}
