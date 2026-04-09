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

Use Case 4: Room Search & Availability Check
-

# Goal
Enable guests to view available rooms and their details without modifying system state, reinforcing safe data access and clear separation of responsibilities.

# Actor:

**Guest –** initiates a search to view available room options.

**Search Service –** handles read-only access to inventory and room information.

# Flow:

1. Guest initiates a room search request.
2. The system retrieves availability data from the inventory.
3. Room details and pricing are obtained from room objects.
4. Unavailable room types are filtered out.
5. Available room types and their details are displayed.
6. System state remains unchanged.

# Key Concepts Used
1. Read-Only Access - Search operations are designed to read data without altering it. This prevents unintended side effects and ensures system stability.
2. Defensive Programming - The search logic performs checks to ensure only valid and available room types are displayed. This protects the system from incorrect assumptions and invalid data usage.
3. Separation of Concerns - Search functionality is isolated from inventory mutation and booking logic. This ensures that searching does not interfere with allocation or availability updates.
4. Inventory as State Holder - Inventory is accessed only to retrieve current availability counts. No updates are performed during search operations.
5. Domain Model Usage -  Room objects provide descriptive information such as pricing and amenities. This avoids duplicating room-related data in the inventory layer.
6. Validation Logic - Room types with zero availability are excluded from the search results. This ensures that guests see only actionable options.

# Key Requirements
1. Retrieve room availability from the centralized inventory.
2. Display only room types with availability greater than zero.
3. Show room details and pricing using room domain objects.
4. Ensure inventory data is not modified during search operations.
5. Maintain a clear boundary between search logic and booking logic.

# Key Benefits
1. Accurate availability visibility without state mutation
2. Reduced risk of accidental inventory corruption
3. Clear separation between read-only and write operations

# Drawbacks of Previous Use Case
Use Case 3 introduced centralized inventory but did not differentiate between read and write access.
Without explicit separation, inventory could be accidentally modified during non-booking operations.

Use Case 5: Booking Request (First-Come-First-Served)
-

# Goal: 

Handle multiple booking requests fairly by introducing a request intake mechanism that preserves arrival order, reflecting real-world booking behavior during peak demand.

# Actor:

Reservation – represents a guest’s intent to book a room.
Booking Request Queue – manages and orders incoming booking requests.

# Flow:

1. Guest submits a booking request.
2. The request is added to the booking queue.
3. Requests are stored in arrival order.
4. Queued requests wait for processing by the allocation system.
5. No inventory mutation occurs at this stage.

# Key Concepts Used
1. Problem of Simultaneous Requests - During peak demand, multiple booking requests can arrive at nearly the same time. Without ordering, requests may be processed inconsistently, leading to unfair allocation.
2. Queue Data Structure - A Queue<Reservation> is used to store booking requests.
3. Queues naturally model waiting lines where elements are processed in sequence.
4. FIFO Principle - FIFO (First-Come-First-Served) ensures that the earliest request is processed first. This mirrors fairness expectations in real booking systems.
5. Fairness - Using a queue guarantees that no request can bypass another. All guests are treated equally based on request arrival time.
6. Request Ordering - The queue preserves insertion order automatically. This eliminates the need for manual sorting or timestamp comparison.
7. Decoupling Request Intake from Allocation - Requests are collected first and processed later. This separation prepares the system for controlled allocation and concurrency handling.

# Key Requirements
1. Accept booking requests from guests.
2. Store requests in a queue structure.
3. Preserve the order in which requests arrive.
4. Ensure no room allocation or inventory updates occur at this stage.
5. Prepare requests for subsequent processing.

# Key Benefits
1. Fair and deterministic booking request handling
2. Predictable system behavior under peak load
3. Simplified request coordination before allocation

# Drawbacks of Previous Use Case
1. Use Case 4 allowed room visibility but did not handle booking intent.
2. Without a request intake mechanism, simultaneous booking attempts could not be managed fairly.

Use Case 6: Reservation Confirmation & Room Allocation
-

# Goal: 

Confirm booking requests by assigning rooms safely while ensuring inventory consistency and preventing double-booking under all circumstances.

# Actor:

1. Booking Service – processes queued booking requests and performs room allocation.
2. Inventory Service – maintains and updates room availability state.

# Flow:

1. Booking request is dequeued from the request queue.
2. The system checks availability for the requested room type.
3. A unique room ID is generated and assigned.
4. The room ID is recorded to prevent reuse.
5. Inventory count is decremented immediately.
6. Reservation is confirmed.

# Key Concepts Used
1. Problem of Double Booking - Without controlled allocation, the same room may be assigned to multiple guests. This results in room ID collisions and inconsistent system state.
2. Set Data Structure - A Set<String> is used to store allocated room IDs. Sets enforce uniqueness by design, preventing duplicate room assignments.
3. Uniqueness Enforcement - By checking against an existing set of room IDs, the system guarantees that no room is assigned more than once. This removes the need for manual duplicate checks.
4. Mapping Room Types to Assigned Rooms - A HashMap<String, Set<String>> maps each room type to its allocated room IDs. This allows grouped tracking and simplifies validation and reporting.
5. Atomic Logical Operations - Room allocation is treated as a single logical unit. Assignment and inventory update occur together to avoid partial or inconsistent state.
6. Inventory Synchronization - Inventory is updated immediately after allocation. This ensures that availability reflects the current system state at all times.

# Key Requirements
1. Retrieve booking requests from the queue in FIFO order.
2. Generate and assign a unique room ID for each confirmed reservation.
3. Prevent reuse of room IDs across all allocations.
4. Update inventory immediately after successful allocation.
5. Ensure allocation logic maintains system consistency.

# Key Benefits
1. Guaranteed uniqueness of room assignments
2. Immediate synchronization between booking and inventory
3. Elimination of double-booking scenarios

# Drawbacks of Previous Use Case
1. Use Case 5 handled request ordering but did not confirm bookings.
2. Without allocation and uniqueness enforcement, queued requests could still result in conflicting assignments.

Use Case 7: Add-On Service Selection
-

# Goal: 
Extend the booking model to support optional services, demonstrating how real-world business features can be added without modifying core booking or allocation logic.

# Actor:
1. Guest – selects optional services for an existing reservation.
2. Add-On Service – represents an individual optional offering.
3. Add-On Service Manager – manages the association between reservations and selected services.

# Flow:

1. Guest selects one or more add-on services.
2. Selected services are added to a list.
3. The list of services is mapped to the corresponding reservation ID.
4. Additional cost for the reservation is calculated.
5. Core booking and inventory state remain unchanged.

# Key Concepts Used
1. Business Extensibility - Real-world bookings often include additional offerings beyond the primary product. The system must support new features without disrupting existing logic.
2. One-to-Many Relationship - A single reservation can have multiple associated services. This relationship is modeled using a map from reservation ID to a list of services.
3. Map and List Combination - Map<String, List<Service>> allows efficient lookup of services for a reservation. Lists preserve insertion order and allow multiple services to be attached.
4. Composition over Inheritance - Services are composed with reservations rather than inherited. This avoids rigid class hierarchies and supports flexible feature growth.
5. Separation of Core and Optional Features - Add-on services are managed independently of room allocation and inventory. This prevents optional features from complicating critical booking workflows.
6. Cost Aggregation - Service costs are calculated separately and combined when needed. This keeps pricing logic modular and easier to extend.
# Key Requirements
1. Allow multiple services to be attached to a single reservation.
2. Store selected services using a reservation-to-services mapping.
3. Calculate total additional cost for selected services.
4. Ensure add-on logic does not modify booking or inventory state.
5. Support easy addition of new service types.
# Key Benefits
1. Flexible attachment of optional services to reservations
2. Clean mapping between bookings and value-added features
3. Easy expansion of services without core booking changes
# Drawbacks of Previous Use Case
1. Use Case 6 confirmed room allocation but treated bookings as static entities.
2. Without add-on support, the system could not model common real-world booking enhancements.

Use Case 8: Booking History & Reporting
-

# Goal: 
Introduce historical tracking of confirmed bookings to provide operational visibility, enable audits, and support reporting, reinforcing a persistence-oriented mindset without introducing external storage.

# Actor:

1. Admin – reviews booking history and reports for operational purposes.
2. Booking History – maintains a record of confirmed reservations.
3. Booking Report Service – generates summaries and reports from stored booking data.

# Flow:

1. A booking is successfully confirmed.
2. The confirmed reservation is added to booking history.
3. Booking history maintains records in insertion order.
4. Admin requests booking information or reports.
5. Stored reservations are retrieved and displayed as required.

# Key Concepts Used
1. Operational Visibility - Real systems require visibility into past transactions.
2. Historical data allows administrators to understand system usage and behavior.
3. List Data Structure - A List<Reservation> is used to store confirmed bookings. Lists preserve insertion order, making them suitable for chronological records.
4. Ordered Storage - Bookings are stored in the order they are confirmed. This naturally reflects real-world timelines and supports sequential reporting.
5. Historical Tracking - Once stored, bookings form an audit trail. This enables later review, analysis, and verification of system actions.
6. Reporting Readiness - Storing structured booking data prepares the system for reporting. Reports can be generated without reprocessing live booking flows.
7. Separation of Data Storage and Reporting - Booking history focuses on storing data. Reporting logic is delegated to a separate service, reducing coupling.
8. Persistence Mindset (Without Storage Medium) - Although data is stored in memory, the system treats history as long-lived information. This prepares learners conceptually for file-based or database persistence in later stages.

# Key Requirements
1. Store each confirmed reservation in booking history.
2. Maintain bookings in the order they are confirmed.
3. Allow retrieval of stored reservations for review.
4. Generate summary reports from booking history.
5. Ensure reporting does not modify stored booking data.

# Key Benefits
1. Complete and traceable booking audit trail
2. Simplified reporting and administrative analysis
3. Improved support for customer issue resolution

# Drawbacks of Previous Use Case
1. Use Case 7 extended booking functionality but did not retain historical data.
2. Without booking history, completed transactions could not be reviewed or analyzed.

Use Case 9: Error Handling & Validation
-

# Goal: 
Strengthen system reliability by introducing structured validation and error handling, ensuring that invalid inputs and inconsistent states are detected and handled early.

# Actor:

1. Guest – provides booking input that must be validated.
2. Invalid Booking Validator – validates input and system state before processing requests.
# Flow:

1. Guest provides booking input.
2. System validates input values and system constraints.
3. If validation fails, an error is raised immediately.
4. A meaningful failure message is displayed.
5. The system prevents invalid state changes and continues running safely.

# Key Concepts Used
1. Input Validation - Validation ensures that incoming data conforms to expected rules before processing. This prevents invalid or inconsistent data from entering the system.
2. Custom Exceptions - Domain-specific exceptions are used to represent invalid booking scenarios. Custom exceptions make error causes explicit and improve code readability.
3. Fail-Fast Design - The system detects errors as early as possible and stops further processing. This avoids cascading failures and simplifies debugging.
4. Guarding System State - Checks are performed before inventory updates or allocations. This ensures that critical state, such as availability counts, remains valid.
5. Graceful Failure Handling - Errors are communicated clearly without crashing the application. This improves system usability and maintainability.
6. Correctness over Happy Path - The system is designed to handle incorrect usage, not just ideal scenarios. This reflects real-world conditions where invalid input is common.

# Key Requirements
1. Validate room types before processing bookings.
2. Prevent inventory from reaching invalid or negative values.
3. Throw and handle custom exceptions for invalid scenarios.
4. Display clear and informative failure messages.
5. Ensure the system remains stable after errors.

# Key Benefits
1. Early detection of invalid system states
2. Reduced risk of silent data corruption
3. More stable and predictable application behavior

# Drawbacks of Previous Use Case
1. Use Case 8 focused on storing and reporting booking data but assumed valid input.
2. Without validation, incorrect data could corrupt system state and reports.

Use Case 10: Booking Cancellation & Inventory Rollback
-

# Goal: 
Enable safe cancellation of confirmed bookings by correctly reversing system state changes, ensuring inventory consistency and predictable recovery behavior.

# Actor:

1. Guest – initiates a cancellation request for an existing booking.
2. Cancellation Service – validates cancellations and performs controlled rollback operations.
# Flow:

1. Guest initiates a cancellation request.
2. The system validates the reservation to ensure it exists and is cancellable.
3. The allocated room ID is recorded in a rollback structure.
4. Inventory count for the corresponding room type is incremented.
5. Booking history is updated to reflect the cancellation.
6. System state is restored consistently.

# Key Concepts Used
1. State Reversal - Cancellation requires undoing previously completed operations. The system must revert inventory and booking state without introducing inconsistencies.
2. Stack Data Structure - A Stack<String> is used to track recently released room IDs. Stacks follow a Last-In-First-Out (LIFO) order, which naturally models rollback behavior.
3. LIFO Rollback Logic - The most recent allocation is the first to be reversed. This aligns with real-world undo operations and simplifies recovery logic.
4. Controlled Mutation - State changes during cancellation are performed in a strict, predefined order. This prevents partial rollbacks and protects system integrity.
5. Inventory Restoration - Inventory counts are incremented immediately after cancellation. This ensures availability accurately reflects the current system state.
6. Validation of Cancellation Requests - The system verifies that a reservation exists before allowing cancellation. Invalid or duplicate cancellation attempts are rejected safely.

# Key Requirements
1. Allow cancellation of confirmed bookings only.
2. Validate reservation existence before performing rollback.
3. Release allocated room IDs back to the availability pool.
4. Restore inventory counts accurately and immediately.
5. Prevent cancellation of non-existent or already cancelled bookings.

# Key Benefits
1. Safe recovery of inventory after cancellations
2. Consistent system state across the booking lifecycle
3. Controlled and predictable rollback behavior

# Drawbacks of Previous Use Case
1. Use Case 9 focused on input validation but did not address reversing valid operations.
2. Without rollback support, confirmed bookings could not be safely undone.

Use Case 11: Concurrent Booking Simulation (Thread Safety)
-

# Goal: 
Demonstrate how concurrent access to shared resources can lead to inconsistent system state and show how synchronization ensures correctness under multi-user conditions.

# Actor:

1. Multiple Guests – submit booking requests concurrently.
2. Concurrent Booking Processor – processes booking requests in a multi-threaded environment.

# Flow:

1. Multiple guests submit booking requests simultaneously.
2. Requests are added to a shared booking queue.
3. Threads retrieve requests using synchronized access.
4. Room allocation and inventory updates are performed inside critical sections.
5. The system completes allocations without conflicts or inconsistencies.

# Key Concepts Used
1. Race Conditions - Race conditions occur when multiple threads access and modify shared data simultaneously. The final system state becomes dependent on execution timing rather than logic.
2. Thread Safety - Thread safety ensures that shared resources behave correctly when accessed by multiple threads. This is critical in systems handling concurrent user actions.
3. Shared Mutable State - The booking queue and inventory are shared across threads. Uncontrolled access to shared mutable data can corrupt system state.
4. Critical Sections - Critical sections are blocks of code that must be executed by only one thread at a time. Synchronization ensures exclusive access to these sections.
5. Synchronized Access - Synchronization mechanisms are used to protect shared resources. This prevents interleaving operations that could lead to double allocation.
6. Concurrency vs. Parallelism - Concurrency focuses on correctness when tasks overlap in time. This use case emphasizes correctness over performance optimization.

# Key Requirements
1. Simulate multiple booking requests occurring at the same time.
2. Use shared data structures for booking requests and inventory.
3. Ensure inventory updates are performed in a thread-safe manner.
4. Prevent double allocation under concurrent execution.
5. Maintain consistent system state under load.
# Key Benefits
1. Safe multi-user booking simulation
2. Correct room allocations under concurrent load
3. Foundation for building scalable, multi-user systems
# Drawbacks of Previous Use Case
1. Earlier use cases assumed a single-threaded execution model.
2. Such assumptions are unsafe in real production environments where concurrent access is common.