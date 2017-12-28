package hackathon.pojo;

import java.io.Serializable;

public class CustomerSearch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] gender;
	private String[] year;
	private Integer[] province;

	public String[] getGender() {
		return gender;
	}

	public void setGender(String[] gender) {
		this.gender = gender;
	}

	public String[] getYear() {
		return year;
	}

	public void setYear(String[] year) {
		this.year = year;
	}

	public Integer[] getProvince() {
		return province;
	}

	public void setProvince(Integer[] province) {
		this.province = province;
	}

}
