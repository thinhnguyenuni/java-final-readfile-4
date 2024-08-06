package fa.training.entities;

public class Traveler extends Immigrant {

	private String company;
	private String stayPlace;

	public Traveler() {
		super();
	}

	public Traveler(int type, String immigrantID, String name, String birthDate, String passport, String nationality,
			String immigrantDate, String stayLength, double discountRate, String company, String stayPlace) {
		super(type, immigrantID, name, birthDate, passport, nationality, immigrantDate, stayLength, discountRate);
		this.company = company;
		this.stayPlace = stayPlace;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStayPlace() {
		return stayPlace;
	}

	public void setStayPlace(String stayPlace) {
		this.stayPlace = stayPlace;
	}

	@Override
	public void showInfo() {
		super.showInfo();
		System.out.println("Company: " + company);
		System.out.println("StayPlace: " + stayPlace);
	}

}
