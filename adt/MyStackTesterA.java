// Interface for the Stack ADT
interface Stack {
    void push(Object item);
    Object pop();
    int size();
}

// Implementation of the Stack ADT using an array
class MyStack implements Stack {
    private Object[] stack;
    private int ssize;
    private static final int INITIAL_CAPACITY = 10;

    public MyStack() {
        stack = new Object[INITIAL_CAPACITY];
        ssize = 0;
    }

    public void push(Object item) {
        if (ssize == stack.length) {
            resize();  // Resize the array if it's full
        }
        stack[ssize++] = item;
    }

    public Object pop() {
        if (ssize == 0) {
            throw new IllegalStateException("Stack is empty");
        }
        Object item = stack[--ssize];
        stack[ssize] = null;  // Nullify the reference for garbage collection
        return item;
    }

    public int size() {
        return ssize;
    }

    private void resize() {
        int newCapacity = stack.length * 2;
        Object[] newStack = new Object[newCapacity];
        System.arraycopy(stack, 0, newStack, 0, stack.length);
        stack = newStack;
    }

    public String toString() {
        String result = "Stack: [ ";
        for (int i = 0; i < ssize; i++) {
            result += stack[i] + " ";
        }
        return result + "] (" + size() + ")";
    }
}

// Tester class for MyStack
public class MyStackTesterA {
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

