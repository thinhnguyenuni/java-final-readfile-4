package fa.training.entities;

public class OverseaStudent extends Immigrant {

	private String university;
	private String majors;

	public OverseaStudent() {
		super();
	}

	public OverseaStudent(int type, String immigrantID, String name, String birthDate, String passport,
			String nationality, String immigrantDate, String stayLength, double discountRate, String university,
			String majors) {
		super(type, immigrantID, name, birthDate, passport, nationality, immigrantDate, stayLength, discountRate);
		this.university = university;
		this.majors = majors;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getMajors() {
		return majors;
	}

	public void setMajors(String majors) {
		this.majors = majors;
	}

	@Override
	public void showInfo() {
		super.showInfo();
		System.out.println("University: " + university);
		System.out.println("Majors: " + majors);
	}
}
