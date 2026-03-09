Book My Stay App
-
This project presents the design and implementation of a Hotel Booking Management System to illustrate the practical application of Core Java and fundamental data structures in real-world scenarios. The system is developed incrementally, with each use case introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, and prevention of double-booking. By focusing on core logic and system behavior rather than user interface concerns, the project enables learners to understand not only how data structures are used, but why they are essential in scalable and maintainable software systems.

Use Case 1: Application Entry & Welcome Message
-
**Goal:** Establish a clear and predictable starting point for the Hotel Booking application by demonstrating how a Java program begins execution and produces console output.

**Actor:** User – runs the application from the command line or IDE.

**Flow:**

1. User runs the application.
2. JVM invokes the main() method.
3. Application prints a welcome message along with the application name and version.
4. Application terminates.

**Key Concepts Used**
1. Class - Even the simplest Java application must be defined inside a class.
2. The class acts as a container for application behavior and marks the logical boundary of the program.
3. main() Method - The main method is the entry point of every standalone Java application. The JVM looks specifically for the method signature:
4. public static void main(String[] args).
5. static Keyword - The main() method is declared static so that it can be executed without creating an object of the class.
6. This allows the JVM to start execution directly.
7. Console Output - System.out.println() is used to send text output to the console.
8. This is the simplest way to observe program behavior during early development.
9. String Literals - Text enclosed in double quotes (e.g., "Hotel Booking System v1.0") is treated as a String literal, which is immutable and stored in the String pool.
10. Method Invocation - Calling println() on the out object demonstrates how methods are invoked on objects in Java, even in basic programs.
11. Application Flow - Execution proceeds top to bottom inside the main() method unless altered by control structures.
12. This use case reinforces linear execution flow.
13. JavaDoc Comments - JavaDoc comments are used to document the class and its purpose. They serve as the foundation for professional code documentation.
14. JavaDoc Annotations - Tags such as @author and @version provide metadata about the class and help maintain traceability as the system evolves.

**Key Requirements**
1. Create a Java class that represents the application entry point.
2. Implement the main() method using the correct signature.
3. Print a welcome message to the console.
4. Display the application name and version information.
5. Use JavaDoc comments to document the class and its intent.
6. Ensure the program executes and terminates without errors.

**Key Benefits**
1. Clear and predictable application startup behavior
2. Single, well-defined execution entry point
3. Improved debuggability during early development