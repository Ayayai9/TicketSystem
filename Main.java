package FPAssignment1;

import java.util.*;

public class Main {
	private static boolean hasUnpaidBooking = false; // Tracks whether the user has bookings not yet checked out
	private static final EventManager manager = new EventManager(); // Manages events and bookings
	private static final Scanner scnr = new Scanner(System.in);

	public static void main(String[] args) {
		boolean exit = false;

		while (!exit) {
			// Display main menu options
			printMenu();
			String input = scnr.nextLine();

			switch (input) {
			case "1":
				// List all available events
				listAllEvents();
				break;
			case "2":
				// Book a ticket by searching a keyword
				bookTicket();
				break;
			case "3":
				// View all bookings made by the user
				viewBooking();
				break;
			case "4":
				// Remove a specific booking from the list
				removeBooking();
				break;
			case "5":
				// Proceed to checkout
				checkout();
				break;
			case "6":
				// Quit program, but warn user if they haven't checked out
				exit = quit();
				break;
			default:
				// Handle invalid menu choices
				System.out.println("Please enter a valid number from 1-6");
			}
		}

		scnr.close(); // Clean up scanner resource
	}

	private static void printMenu() {
		System.out.println("\nChoose an option:");
		System.out.println("1. List all events");
		System.out.println("2. Book a ticket");
		System.out.println("3. View my booking");
		System.out.println("4. Remove an event from the booking");
		System.out.println("5. Checkout");
		System.out.println("6. Quit");
		System.out.print("Please select: ");
	}

	private static void listAllEvents() {
		Event[] printEvents = manager.getAll();
		System.out.println("The current events are:");
		for (int i = 0; i < printEvents.length; i++) {
			Event e = printEvents[i];
			System.out.printf("%d. %s | $%.2f | %d seats | %s\n", i + 1, e.getName(), e.getPrice(), e.getSeats(),
					e.onlineOrNot() ? "Online Available" : "No online");
		}
	}

	private static void bookTicket() {
		System.out.print("Enter a keyword: ");
		String keyword = scnr.nextLine();
		Event booking = manager.search(keyword);

		// If a booking was made, set unpaid booking flag
		if (booking != null) {
			hasUnpaidBooking = true;
		}
	}

	private static void viewBooking() {
		Booking[] bookedEvents = manager.getBookedItems();
		double total = 0;
		int totalTickets = 0;

		if (bookedEvents.length == 0) {
			System.out.println("No booked events.");
		} else {
			for (int i = 0; i < bookedEvents.length; i++) {
				Booking b = bookedEvents[i];
				double eventTotal = b.getQty() * b.getEvent().getPrice();
				total += eventTotal;
				totalTickets += b.getQty();

				// Nicely formatted output
				System.out.printf("%d. %-25s X %4d %-10s : $%7.2f\n", i + 1, b.getEvent().getName(), b.getQty(),
						b.getOnline() ? "Online" : "In-person", eventTotal);
			}
			System.out.printf("Total: $%.2f for %d tickets\n", total, totalTickets);
		}
	}

	private static void removeBooking() {
		Booking[] removeBooking = manager.getBookedItems();
		String selection = null;

		if (removeBooking.length == 0) {
			System.out.println("No booked events.");
		} else {
			// Show all current bookings
			for (int i = 0; i < removeBooking.length; i++) {
				Booking b = removeBooking[i];
				double eventTotal = b.getQty() * b.getEvent().getPrice();
				System.out.printf("%d. %s X %d %s : $%.2f\n", i + 1, b.getEvent().getName(), b.getQty(),
						b.getOnline() ? "Online" : "In-person", eventTotal);
			}
			System.out.printf("%d. Cancel\n", removeBooking.length + 1); // Option to cancel action

			boolean validInput = false;

			// Loop until user provides valid selection
			while (!validInput) {
				System.out.print("Please select: ");
				selection = scnr.nextLine().trim();

				try {
					int choice = Integer.parseInt(selection);
					if (choice >= 1 && choice <= removeBooking.length) {
						Event eventToRemove = removeBooking[choice - 1].getEvent();
						manager.removeBookedItem(eventToRemove);
						System.out.printf(">> Booking %s removed. Go back to the main menu.\n",
								eventToRemove.getName());

						// If this was the only booking, reset unpaid flag
						if (removeBooking.length == 1) {
							hasUnpaidBooking = false;
						}
						validInput = true;

					} else if (choice == removeBooking.length + 1) {
						// User chose to cancel
						validInput = true;
					}
				} catch (NumberFormatException ex) {
					System.out.println("Invalid input! Please select a valid option.");
				}
			}
		}
	}

	private static void checkout() {
		Booking[] finalBookedEvents = manager.getBookedItems();
		double finalTotal = 0;
		int finalTotalTickets = 0;

		if (finalBookedEvents.length == 0) {
			System.out.println("No booked events.");
		} else {
			for (int i = 0; i < finalBookedEvents.length; i++) {
				Booking b = finalBookedEvents[i];
				double eventTotal = b.getQty() * b.getEvent().getPrice();
				finalTotal += eventTotal;
				finalTotalTickets += b.getQty();

				System.out.printf("%d. %s X %d %s : $%.2f\n", i + 1, b.getEvent().getName(), b.getQty(),
						b.getOnline() ? "Online" : "In-person", eventTotal);
			}

			System.out.printf("Total: $%.2f for %d tickets\n", finalTotal, finalTotalTickets);

			// Ask for confirmation
			System.out.print("Proceed? (y/n) ");
			String payment = scnr.nextLine();
			if (payment.equals("y")) {
				hasUnpaidBooking = false;
				System.out.println("Thank you! Tickets have been sent to your registered email address.");
			}
		}
	}

	private static boolean quit() {
		// Quit program, but warn user if they haven't checked out
		if (hasUnpaidBooking) {
			System.out.println("Go back! You have unpaid booking.");
			return false;
		} else {
			System.out.println("Goodbye!");
			return true;
		}
	}
}
