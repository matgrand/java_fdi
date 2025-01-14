// Interface for the Stack ADT
interface Stack {
    void push(Object item);
    Object pop();
    int size();
    boolean isEmpty();
}

// Implementation of the Stack ADT using a linked list
class MyStack implements Stack {
    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;
    private int ssize;

    public MyStack() {
        top = null;
        ssize = 0;
    }

    public void push(Object item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
        ssize++;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        Object item = top.data;
        top = top.next;
        ssize--;
        return item;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return ssize;
    }

    public String toString() {
        String result = "Stack: [ ";
        Node current = top;
        while (current != null) {
            result += current.data + " ";
            current = current.next;
        }
        return result + "] (" + size() + ")";
    }
}

// Tester class for MyStack
public class MyStackTesterLL {
    public static void main(String[] args) {
        MyStack stack = new MyStack();

        System.out.println("Pushing elements onto the stack...");
        stack.push("First");
        System.out.println(stack);

        stack.push("Second");
        System.out.println(stack);

        stack.push("Third");
        System.out.println(stack);

        stack.push("Fourth");
        System.out.println(stack);

        System.out.println("Popping elements from the stack...");
        System.out.println("Popped: " + stack.pop());
        System.out.println(stack);

        System.out.println("Popped: " + stack.pop());
        System.out.println(stack);

        System.out.println("Popped: " + stack.pop());
        System.out.println(stack);

        System.out.println("Popped: " + stack.pop());
        System.out.println(stack);
    }
}
