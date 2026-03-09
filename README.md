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

Use Case 2: Basic Room Types & Static Availability
-
**Goal:** Introduce object modeling through inheritance and abstraction before introducing data structures, allowing students to focus on domain design rather than optimization.

**Actor:** User – runs the application to view predefined room types and their availability.

**Flow:**

1. User runs the application.
2. Room objects representing different room types are created.
3. Availability for each room type is stored using simple variables.
4. Room details and availability information are printed to the console.
5. Application terminates.

**Key Concepts Used**
1. Abstract Class - An abstract class is used to represent a generalized concept that should not be instantiated directly. The Room class defines common attributes and behavior shared by all room types while enforcing a consistent structure.
2. Inheritance - Concrete room classes (SingleRoom, DoubleRoom, SuiteRoom) extend the abstract Room class. This allows shared properties to be reused while enabling specialization for each room type.
3. Polymorphism - Room objects are referenced using the Room type, enabling uniform handling of different room implementations. This prepares the system for future extensibility without changing client code.
4. Encapsulation - Room attributes such as number of beds, size, and price are encapsulated within the Room class. This ensures that room characteristics are controlled and modified only through defined behavior.
5. Static Availability Representation - Room availability is stored using simple variables rather than data structures. This intentionally highlights the limitations of hardcoded and scattered state management.
6. Separation of Domain and State - Room objects represent what a room is, while availability variables represent current system state. This distinction becomes critical when inventory management is introduced later.

**Key Requirements**
1. Define an abstract Room class with common attributes.
2. Create concrete room classes for Single, Double, and Suite rooms.
3. Initialize room objects in the application entry point.
4. Store room availability using individual variables.
5. Display room details and availability to the console.

**Key Benefits**
1. Clear introduction to object-oriented domain modeling
2. Demonstrates inheritance and abstraction in a real-world context
3. Establishes a strong foundation for later inventory refactoring

**Drawbacks of Previous Use Case**
1. Use Case 1 focused only on application startup and execution flow.
2. No domain modeling or business concepts were introduced, limiting system realism.

Use Case 3: Centralized Room Inventory Management
-
**Goal:** Introduce centralized inventory management by replacing scattered availability variables with a single, consistent data structure, demonstrating how HashMap solves real-world state management problems.

**Actor:** RoomInventory – responsible for managing and exposing room availability across the system.

**Flow:**
1. The system initializes the inventory component.
2. Room types are registered with their available counts.
3. Availability is stored and retrieved from a centralized HashMap.
4. Updates to availability are performed through controlled methods.
5. The current inventory state is displayed when requested.

**Key Concepts Used**
1. Problem of Scattered State - In the previous use case, availability was stored in separate variables. This leads to inconsistent updates, duplication, and poor scalability as the system grows.
2. HashMap - HashMap<String, Integer> is used to map room types to available room counts. This allows fast access, updates, and lookups based on a logical key.
3. O(1) Lookup - HashMap provides average constant-time complexity for get and put operations. This makes it suitable for systems that require frequent availability checks.
4. Single Source of Truth - All availability data is maintained in one centralized structure. This eliminates discrepancies caused by multiple variables representing the same state.
5. Encapsulation of Inventory Logic - Inventory-related operations are encapsulated within a dedicated class. Other parts of the system interact with inventory only through exposed methods, reducing coupling.
6. Separation of Concerns - Inventory manages how many rooms are available, not what a room is. Room characteristics such as price and size remain part of the Room domain model.
7. Scalability - Adding a new room type requires only inserting a new entry into the map. No changes are required in application logic, demonstrating scalable design.

**Key Requirements**
1. Initialize room availability using a constructor.
2. Store room availability using a HashMap.
3. Provide methods to retrieve current availability.
4. Support controlled updates to room availability.
5. Ensure inventory state remains consistent across operations.

**Key Benefits**
1. Single source of truth for room availability
2. Constant-time inventory access and updates
3. Improved scalability when introducing new room types

**Drawbacks of Previous Use Case**
1. Availability was managed using independent variables.
2. This approach does not scale and increases the risk of inconsistent system state as complexity grows.