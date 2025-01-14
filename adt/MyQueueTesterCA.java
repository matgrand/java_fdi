// Interface for the Queue ADT
interface Queue {
    void enqueue(Object item);
    Object dequeue();
    int size();
}

// Implementation of the Queue ADT using a circular array
class MyQueue implements Queue {
    private Object[] queue;
    private int front;
    private int rear;
    private int count;
    private static final int INITIAL_CAPACITY = 10;

    public MyQueue() {
        queue = new Object[INITIAL_CAPACITY];
        front = 0;
        rear = 0;
        count = 0;
    }

    public void enqueue(Object item) {
        if (count == queue.length) {
            resize();  // Resize the array if it's full
        }
        queue[rear] = item;
        rear = (rear + 1) % queue.length;  // Circular increment
        count++;
    }

    public Object dequeue() {
        if (count == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        Object item = queue[front];
        front = (front + 1) % queue.length;  // Circular increment
        count--;
        return item;
    }

    public int size() {
        return count;
    }

    private void resize() {
        int newCapacity = queue.length * 2;
        Object[] newQueue = new Object[newCapacity];
        for (int i = 0; i < count; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        rear = count;
    }

    public String toString() {
        String result = "Queue: [ ";
        for (int i = 0; i < count; i++) {
            result += queue[(front + i) % queue.length] + " ";
        }
        return result + "] (" + size() + ")";
    }
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
