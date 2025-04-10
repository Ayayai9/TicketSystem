package FPAssignment1;

public class Event {
	// Event attributes
	private String name; // Name of the event
	private double price; // Price per ticket
	private int seats; // Available in-person seats
	private boolean online; // Indicates if the event is available online

	// Constructor to initialize an Event
	public Event(String name, double price, int seats, boolean online) {
		this.name = name;
		this.price = price;
		this.seats = seats;
		this.online = online;
	}

	// Get the name of the event
	public String getName() {
		return name;
	}

	// Get the price of a ticket
	public double getPrice() {
		return price;
	}

	// Get number of available in-person seats
	public int getSeats() {
		return seats;
	}

	// Check if the event is available online
	public boolean onlineOrNot() {
		return online;
	}

	// Reduce the number of seats after booking
	public void decreaseSeats(int qty) {
		seats -= qty;
	}

	// Increase the number of seats (e.g., when a booking is canceled)
	public void increaseSeats(int qty) {
		seats += qty;
	}
}
