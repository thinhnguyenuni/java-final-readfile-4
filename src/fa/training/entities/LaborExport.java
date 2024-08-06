package fa.training.entities;

public class LaborExport extends Immigrant {

	private String company;
	private String laborType;

	public LaborExport() {
		super();
	}

	public LaborExport(int type, String immigrantID, String name, String birthDate, String passport, String nationality,
			String immigrantDate, String stayLength, double discountRate, String company, String laborType) {
		super(type, immigrantID, name, birthDate, passport, nationality, immigrantDate, stayLength, discountRate);
		this.company = company;
		this.laborType = laborType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLaborType() {
		return laborType;
	}

	public void setLaborType(String laborType) {
		this.laborType = laborType;
	}

	@Override
	public void showInfo() {
		super.showInfo();
		System.out.println("Company: " + company);
		System.out.println("LaborType: " + laborType);
	}
}
