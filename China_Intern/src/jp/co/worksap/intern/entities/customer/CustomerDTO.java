package jp.co.worksap.intern.entities.customer;

import java.util.Date;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class CustomerDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5538243149394207296L;

	private Long customerId;
	private String name;
	private String gender;
	private Date birthday;
	private String nationality;
	private String passportNo;
	private String address;
	private String email;
	private String tel;

	/**
	 * Default Constructor
	 * 
	 * @param customerId
	 * @param name
	 * @param gender
	 * @param birthday
	 * @param nationality
	 * @param passportNo
	 * @param address
	 * @param email
	 * @param tel
	 */
	public CustomerDTO(Long customerId, String name, String gender,
			Date birthday, String nationality, String passportNo,
			String address, String email, String tel) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.nationality = nationality;
		this.passportNo = passportNo;
		this.address = address;
		this.email = email;
		this.tel = tel;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getNationality() {
		return nationality;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getTel() {
		return tel;
	}

	
	public String[] toCSVStringArray() {
		return null;
	}

	public String[] toCSVHeaders() {
		return null;
	}

}
