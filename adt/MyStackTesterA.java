// Interface for the Stack ADT
interface Stack {
    void push(Object item);
    Object pop();
    int size();
    boolean isEmpty();
}

// Implementation of the Stack ADT using an array
class MyStack implements Stack {
    // complete the implementation
    
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

