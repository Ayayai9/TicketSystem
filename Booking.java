package FPAssignment1;

public class Booking {
	private Event event; // The event associated with the booking
	private int qty; // Number of tickets booked
	private boolean online; // Whether the booking is for online attendance

	// Constructor to initialize a booking
	public Booking(Event event, int qty, boolean online) {
		this.event = event;
		this.qty = qty;
		this.online = online;
	}

	// Get the associated event
	public Event getEvent() {
		return this.event;
	}

	// Get the quantity of tickets booked
	public int getQty() {
		return this.qty;
	}

	// Check if the booking is for online attendance
	public boolean getOnline() {
		return online;
	}

	// Update the online attendance flag
	public void setOnline(boolean online) {
		this.online = online;
	}

	// Add more tickets to this booking
	public void updateQty(int qty) {
		this.qty = this.qty + qty;
	}
}
