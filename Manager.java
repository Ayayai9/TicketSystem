package FPAssignment1;

public abstract class Manager<T> {
	// necessary instance variables and methods

	public abstract T[] getAll();

	public abstract boolean book(T item, int qty, boolean online);

	// was told by tutor it was okay to change the generic to Booking[]
	public abstract Booking[] getBookedItems();

	public abstract boolean removeBookedItem(T item);

	// additional methods if necessary
}
