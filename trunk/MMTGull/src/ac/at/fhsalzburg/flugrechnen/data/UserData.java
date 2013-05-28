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

	private String sourceCity;
	private String sourceAirportName;
	private String sourceAirportCode;

	private String destinationAirportCode;

	public UserData(long oid, String firstName, String lastName, String sourceCity, String sourceAirportName, String sourceAirportCode,
			String destinationAirportCode) {
		this.oid = oid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sourceCity = sourceCity;
		this.sourceAirportName = sourceAirportName;
		this.sourceAirportCode = sourceAirportCode;
		this.destinationAirportCode = destinationAirportCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public String getSourceAirportName() {
		return sourceAirportName;
	}

	public String getSourceAirportCode() {
		return sourceAirportCode;
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
		result = prime * result + ((sourceAirportCode == null) ? 0 : sourceAirportCode.hashCode());
		result = prime * result + ((sourceAirportName == null) ? 0 : sourceAirportName.hashCode());
		result = prime * result + ((sourceCity == null) ? 0 : sourceCity.hashCode());
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
		if (sourceAirportCode == null) {
			if (other.sourceAirportCode != null)
				return false;
		} else if (!sourceAirportCode.equals(other.sourceAirportCode))
			return false;
		if (sourceAirportName == null) {
			if (other.sourceAirportName != null)
				return false;
		} else if (!sourceAirportName.equals(other.sourceAirportName))
			return false;
		if (sourceCity == null) {
			if (other.sourceCity != null)
				return false;
		} else if (!sourceCity.equals(other.sourceCity))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserData [oid=" + oid + ", firstName=" + firstName + ", lastName=" + lastName + ", sourceCity=" + sourceCity
				+ ", sourceAirportName=" + sourceAirportName + ", sourceAirportCode=" + sourceAirportCode + ", destinationAirportCode="
				+ destinationAirportCode + "]";
	}
}
