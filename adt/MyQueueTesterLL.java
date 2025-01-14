// Interface for the Queue ADT
interface Queue {
    void enqueue(Object item);
    Object dequeue();
    int size();
    boolean isEmpty();
}

// Implementation of the Queue ADT using a linked list
class MyQueue implements Queue {
    // complete the implementation
    
}

public class MyQueueTesterLL {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();

        System.out.println("Enqueuing elements into the queue...");
        queue.enqueue("First");
        System.out.print(queue);

        queue.enqueue("Second");
        System.out.print(queue);

        queue.enqueue("Third");
        System.out.print(queue);

        queue.enqueue("Fourth");
        System.out.print(queue);

        System.out.println("Dequeuing elements from the queue...");
        System.out.println("Dequeued: " + queue.dequeue());
        System.out.print(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.print(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.print(queue);

        System.out.println("Dequeued: " + queue.dequeue());
        System.out.print(queue);
    }
}
