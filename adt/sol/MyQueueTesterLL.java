// Interface for the Queue ADT
interface Queue {
    void enqueue(Object item);
    Object dequeue();
    int size();
    boolean isEmpty();
}

// Implementation of the Queue ADT using a linked list
class MyQueue implements Queue {
    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int count;

    public MyQueue() {
        front = null;
        rear = null;
        count = 0;
    }

    public void enqueue(Object item) {
        Node newNode = new Node(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        count++;
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        Object item = front.data;
        front = front.next;
        if (isEmpty()) {
            rear = null;
        }
        count--;
        return item;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public String toString() {
        String result = "Queue: [ ";
        Node current = front;
        while (current != null) {
            result += current.data + " ";
            current = current.next;
        }
        return result + "] (" + count + ")\n";
    }
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
