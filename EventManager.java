package FPAssignment1;

import java.util.*;

class EventManager extends Manager<Event> implements Searchable<Event> {
	private List<Event> events; // List of all events
	private List<Booking> bookings; // List of all bookings

	public EventManager() {
		// Initialize events with sample data
		events = new ArrayList<>(List.of(new Event("Jazz Night with Joe", 10.5, 3, true),
				new Event("Youtube Rock Concert", 25.25, 0, true), new Event("Mozart Chamber Music", 50, 2, false),
				new Event("Harry Potter Concert", 100, 0, false)));
		bookings = new ArrayList<>();
	}

	@Override
	public Event search(String keyword) {
		Event[] matches = searchedEvents(keyword); // Search events by keyword
		Scanner scnr = new Scanner(System.in);
		Event e = null;

		if (matches.length == 0) {
			System.out.println("Sorry! No events match your keyword.");
		} else {
			int goBack = displayMatchingEvents(matches);

			boolean exit = false;
			boolean showPrompt = true; // Controls whether to show "Please select:" prompt

			while (!exit) {
				if (showPrompt) {
					System.out.print("Please select: ");
				}
				showPrompt = true; // Reset after each iteration
				String stringInput = scnr.nextLine().trim();
				try {
					int intInput = Integer.parseInt(stringInput);

					if (intInput >= 1 && intInput <= matches.length) {
						e = matches[intInput - 1];
						// If no seats and not available online
						if (e.getSeats() == 0 && !e.onlineOrNot()) {
							System.out.println("Sorry! There are no seats available!");
							continue;
						}

						String attendOnline;
						boolean isOnline = false; // match is online
						boolean enteredPrev = false;
						boolean exitOnline = false;

						// Ask whether the user wants to attend online
						while (!exitOnline) {
							System.out.print("Do you want to attend online (y/n): ");
							attendOnline = scnr.nextLine().trim().toLowerCase();

							if (attendOnline.equals("y")) {
								isOnline = true;
								enteredPrev = true;
								exitOnline = true;
							} else if (attendOnline.equals("n")) {
								enteredPrev = true;
								if (e.getSeats() == 0) {
									System.out.println("Sorry! There are no seats available!");
									enteredPrev = false; // Block in-person booking if no seats
								}
								isOnline = false;
								exitOnline = true;
							}
						}
						// Ask for ticket quantity
						while (enteredPrev) {
							System.out.print("Enter the quantity of tickets: ");
							try {
								int quantity = Integer.parseInt(scnr.nextLine().trim());

								if ((quantity > 0 && isOnline)
										|| (quantity <= e.getSeats() && !isOnline && e.getSeats() > 0)) {
									System.out.printf(">> %d tickets of %s booked! Go back to the main menu.\n",
											quantity, e.getName());

									bookings.add(new Booking(e, quantity, isOnline));

									// If not online, decrease seats
									if (!isOnline) {
										e.decreaseSeats(quantity);
									}
									exit = true;
									break;

								} else if (!isOnline && quantity > e.getSeats()) {
									System.out.print("Not enough tickets! ");
								} else {
									System.out.print("Invalid input! ");
								}
							} catch (NumberFormatException ex) {
								System.out.print("Invalid input! ");
							}
						}
					} else if (intInput == goBack) {
						exit = true; // Go back selected
					} else {
						System.out.print("Invalid input! Please try again: ");
						showPrompt = false; // Avoid double "Please select:"
					}
				} catch (NumberFormatException ex) {
					System.out.print("Invalid input! Please try again: ");
					showPrompt = false;
				}
			}
		}
		return e;
	}

	private int displayMatchingEvents(Event[] matches) {
		int i;
		System.out.println("The following event(s) are found:");
		for (i = 0; i < matches.length; i++) {
			Event e = matches[i];
			System.out.printf("%d. %s | $%.2f | %d seats | %s\n", i + 1, e.getName(), e.getPrice(), e.getSeats(),
					e.onlineOrNot() ? "Online Available" : "No online");
		}
		System.out.printf("%d. %s\n\n", matches.length + 1, "Goback");
		return i + 1;
	}

	public Event[] searchedEvents(String keyword) {
		List<Event> matchedList = new ArrayList<>();

		for (Event e : events) {
			if (e.getName().toLowerCase().contains(keyword.toLowerCase())) {
				matchedList.add(e);
			}
		}

		return matchedList.toArray(new Event[0]);
	}

	@Override
	public Event[] getAll() {
		return events.toArray(new Event[0]);
	}

	@Override
	public boolean book(Event item, int qty, boolean online) {
		// Check if booking already exists
		for (Booking booking : bookings) {
			if (booking.getEvent().equals(item) && booking.getOnline() == online) {
				booking.updateQty(qty); // Update existing booking

				if (!online) {
					// Reduce available seats
					for (Event e : events) {
						if (e.equals(item)) {
							e.decreaseSeats(qty);
							break;
						}
					}
				}
				return true;
			}
		}

		// New booking
		if (!online) {
			for (Event e : events) {
				if (e.equals(item)) {
					e.decreaseSeats(qty);
					break;
				}
			}
		}

		bookings.add(new Booking(item, qty, online));
		return true;
	}

	@Override
	public Booking[] getBookedItems() {
		return bookings.toArray(new Booking[0]);
	}

	@Override
	public boolean removeBookedItem(Event item) {
		Iterator<Booking> iterator = bookings.iterator();

		while (iterator.hasNext()) {
			Booking booking = iterator.next();
			if (booking.getEvent().getName().equals(item.getName())) {
				iterator.remove(); // Remove the booking

				// Restore seats if it was an in-person booking
				if (!booking.getOnline()) {
					for (Event e : events) {
						if (e.getName().equals(item.getName())) {
							e.increaseSeats(booking.getQty());
							break;
						}
					}
				}
				return true;
			}
		}

		return false; // Booking not found
	}
}
