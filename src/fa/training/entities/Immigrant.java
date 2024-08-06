package fa.training.entities;

public abstract class Immigrant {

	private int type;
	private String immigrantID;
	private String name;
	private String birthDate;
	private String passport;
	private String nationality;
	private String immigrantDate;
	private String stayLength;
	private double discountRate;

	public Immigrant() {
	}

	public Immigrant(int type, String immigrantID, String name, String birthDate, String passport, String nationality,
			String immigrantDate, String stayLength2, double discountRate) {
		super();
		this.type = type;
		this.immigrantID = immigrantID;
		this.name = name;
		this.birthDate = birthDate;
		this.passport = passport;
		this.nationality = nationality;
		this.immigrantDate = immigrantDate;
		this.stayLength = stayLength2;
		this.discountRate = discountRate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getImmigrantID() {
		return immigrantID;
	}

	public void setImmigrantID(String immigrantID) {
		this.immigrantID = immigrantID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getImmigrantDate() {
		return immigrantDate;
	}

	public void setImmigrantDate(String immigrantDate) {
		this.immigrantDate = immigrantDate;
	}

	public String getStayLength() {
		return stayLength;
	}

	public void setStayLength(String stayLength) {
		this.stayLength = stayLength;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public void showInfo() {
		System.out.println("Type: " + type);
		System.out.println("ImmigrantID: " + immigrantID);
		System.out.println("Name: " + name);
		System.out.println("BirthDate: " + birthDate);
		System.out.println("Passport: " + passport);
		System.out.println("Nationality: " + nationality);
		System.out.println("ImmigrantDate: " + immigrantDate);
		System.out.println("StayLength: " + stayLength);
		System.out.println("DiscountRate: " + discountRate);
	}
}
