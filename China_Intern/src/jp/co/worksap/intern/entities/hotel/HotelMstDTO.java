package jp.co.worksap.intern.entities.hotel;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class HotelMstDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3711231132225687846L;
	private Long hotelId;
	private String hotelName;
	private String address;
	private String tel;

	/**
	 * Default Constructor 
	 * 
	 * @param hotelId
	 * @param hotelName
	 * @param address
	 * @param tel
	 */
	public HotelMstDTO(Long hotelId, String hotelName, String address,
			String tel, int stars) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.address = address;
		this.tel = tel;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public String getAddress() {
		return address;
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
