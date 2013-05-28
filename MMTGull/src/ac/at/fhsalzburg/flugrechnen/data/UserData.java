package ac.at.fhsalzburg.flugrechnen.data;

/**
 * Represents a person with its current location and destination
 * 
 * @author Stefan Huber
 */
public class UserData {

	private long oid;

	private String firstName;
	private String lastName;

	private String startCity;
	private String startAirportName;
	private String startAirportCode;

	private String destinationAirportCode;

	public UserData(long oid, String firstName, String lastName, String sourceCity, String sourceAirportName, String sourceAirportCode,
			String destinationAirportCode) {
		this.oid = oid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.startCity = sourceCity;
		this.startAirportName = sourceAirportName;
		this.startAirportCode = sourceAirportCode;
		this.destinationAirportCode = destinationAirportCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSourceCity() {
		return startCity;
	}

	public String getSourceAirportName() {
		return startAirportName;
	}

	public String getSourceAirportCode() {
		return startAirportCode;
	}

	public String getDestinationAirportCode() {
		return destinationAirportCode;
	}

	public long getOid() {
		return oid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationAirportCode == null) ? 0 : destinationAirportCode.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (oid ^ (oid >>> 32));
		result = prime * result + ((startAirportCode == null) ? 0 : startAirportCode.hashCode());
		result = prime * result + ((startAirportName == null) ? 0 : startAirportName.hashCode());
		result = prime * result + ((startCity == null) ? 0 : startCity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (destinationAirportCode == null) {
			if (other.destinationAirportCode != null)
				return false;
		} else if (!destinationAirportCode.equals(other.destinationAirportCode))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (oid != other.oid)
			return false;
		if (startAirportCode == null) {
			if (other.startAirportCode != null)
				return false;
		} else if (!startAirportCode.equals(other.startAirportCode))
			return false;
		if (startAirportName == null) {
			if (other.startAirportName != null)
				return false;
		} else if (!startAirportName.equals(other.startAirportName))
			return false;
		if (startCity == null) {
			if (other.startCity != null)
				return false;
		} else if (!startCity.equals(other.startCity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserData [oid=" + oid + ", firstName=" + firstName + ", lastName=" + lastName + ", startCity=" + startCity
				+ ", startAirportName=" + startAirportName + ", startAirportCode=" + startAirportCode + ", destinationAirportCode="
				+ destinationAirportCode + "]";
	}
}
