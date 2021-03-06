/**
 * 
 */
package group99.hotel.business;

import java.time.DateTimeException;import java.time.LocalDate;java.time.temporal.ChronoUnit;
import group99.hotel.business.interfaces.Customer;
import group99.hotel.business.interfaces.Reservation;
import group99.hotel.business.interfaces.Room;

/**
 * @author Tahar, Mher, Matt
 *
 */
public class DawsonReservation implements Reservation {

	private final Customer customer;
	private final Room room;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;

	public DawsonReservation(Customer customer, Room room, int inYear, int inMonth, int inDay, int outYear,
			int outMonth, int outDay) {

		this.customer = customer;
		this.room = room;
		try {
			this.checkinDate = LocalDate.of(inYear, inMonth, inDay);
			this.checkoutDate = LocalDate.of(outYear, outMonth, outDay);
		} catch (DateTimeException e) {
			throw new IllegalArgumentException();
		}

		if (this.checkinDate.isAfter(this.checkoutDate)) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int compareTo(Reservation o) {
		if(this.getRoom().compareTo(o.getRoom()) == 0 ){
			
			if (this.getCheckInDate().isBefore(o.getCheckInDate())){
				return -1;
			}
			else return 1;
			
		}
		
		else if(this.getRoom().compareTo(o.getRoom()) == -1 ){
			return -1;
		}
		
		else return 1;
	}//????//

	@Override
	public Customer getCustomer() {
		Customer copy = new DawsonCustomer(this.customer.getName(), this.customer.getEmail(),
				this.customer.getCreditCard());
		return copy;
	}

	@Override
	public Room getRoom() {
		Room copy = new DawsonRoom(this.room.getRoomNumber(), this.room.getRoomType());
	}

	@Override
	public LocalDate getCheckInDate() {

		return LocalDate.of(this.checkinDate.getYear(), this.checkinDate.getMonth(), this.checkinDate.getDayOfMonth());
	}

	@Override
	public LocalDate getCheckOutDate() {
		return LocalDate.of(this.checkoutDate.getYear(), this.checkoutDate.getMonth(),
				this.checkoutDate.getDayOfMonth());
	}

	@Override
	public int getNumberDays() {
		return DAYS.between(this.checkinDate, this.checkoutDate);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkinDate == null) ? 0 : checkinDate.hashCode());
		result = prime * result + ((checkoutDate == null) ? 0 : checkoutDate.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DawsonReservation))
			return false;
		DawsonReservation other = (DawsonReservation) obj;
		if (checkinDate == null) {
			if (other.checkinDate != null)
				return false;
		} else if (!checkinDate.equals(other.checkinDate))
			return false;
		if (checkoutDate == null) {
			if (other.checkoutDate != null)
				return false;
		} else if (!checkoutDate.equals(other.checkoutDate))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return customer.getEmail() + "*" + this.checkinDate.getYear() + "*" + this.checkinDate.getMonth() + "*"
				+ this.checkinDate.getDayOfMonth() + "*" + this.checkoutDate.getYear() + "*"
				+ this.checkoutDate.getMonth() + "*" + this.checkoutDate.getDayOfMonth() + "*" + room.getRoomNumber();
	}

	public boolean overlap(Reservation other) {

		if (this.getRoom().equals(other.getRoom())) {
			if (!(this.getCheckInDate().isAfter(other.getCheckOutDate()))
					|| !(this.getCheckOutDate().isBefore(other.getCheckInDate()))) {
				return true;
			}
		}
		else return false;
	}
}