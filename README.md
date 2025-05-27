# Super Event Ticket System

This repository implements a basic Java ticket system for an event organiser, called **Super Event**. The project demonstrates core Java programming concepts, including object-oriented principles and the use of the Java Collections Framework.

## Features

- Manage events and tickets for an event organiser.
- Add, view, and remove events.
- Purchase and cancel tickets.
- Store and retrieve information about events and tickets using Java Collections.
- Object-oriented design with classes representing events, tickets, and users.

## Technologies Used

- Java (100%)
- Java Collections Framework

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Ayayai9/TicketSystem.git
   cd TicketSystem
   ```

2. **Compile the Java files:**
   ```bash
   javac src/*.java
   ```

3. **Run the application:**
   ```bash
   java -cp src Main
   ```
   > **Note:** The entry point may be a class named `Main.java`. If the main class has a different name, update the command accordingly.

## Project Structure

```
TicketSystem/
├── src/
│   ├── Main.java
│   ├── Event.java
│   ├── Ticket.java
│   └── ... (other classes)
└── README.md
```

## Object-Oriented Concepts Practiced

- **Encapsulation:** Each class manages its own state and behavior.
- **Inheritance & Polymorphism:** Event and Ticket classes may use inheritance to extend base functionality.
- **Collections:** Use of `ArrayList`, `HashMap`, or other Java collections to manage events and tickets.

## Example Use Cases

- **Add Event:** Organisers can add new events to the system.
- **Buy Ticket:** Users can purchase tickets for available events.
- **Cancel Ticket:** Users can cancel their tickets.
- **List Events/Tickets:** View details of all events and tickets.

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements or bug fixes.

## License

This project is open source and available under the [MIT License](LICENSE).

---

*Practice Java basics, OO design, and collections with a real-world event ticketing scenario!*
