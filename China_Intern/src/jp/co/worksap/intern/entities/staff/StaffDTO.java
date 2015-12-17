package jp.co.worksap.intern.entities.staff;

import jp.co.worksap.intern.entities.ICsvMasterDTO;

public class StaffDTO implements ICsvMasterDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8538243149394207296L;
	
	private Long staffId;
	private String name;
	private String gender;
	private RankType rank;
	private PositionType position;
	private Long hotelId;
	
	private static final String[] headers = new String[]{
		"staffId", "name", "gender", "rank", "position", "hotelId"
	};

	/**
	 * Default Constructor
	 * 
	 * @param staffId
	 * @param name
	 * @param gender
	 * @param rank
	 * @param position
	 * @param hotelId
	 */
	public StaffDTO(Long staffId, String name, String gender,
			RankType rank, PositionType position, Long hotelId) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.gender = gender;
		this.rank = rank;
		this.position = position;
		this.hotelId = hotelId;
	}

	public Long getStaffId() {
		return staffId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public RankType getRank() {
		return rank;
	}

	public PositionType getPosition() {
		return position;
	}

	public Long getHotelId() {
		return hotelId;
	}

	
	public String[] toCSVStringArray() {
		String[] strs = new String[headers.length];
		strs[0] = Long.toString(staffId);
		strs[1] = name;
		strs[2] = gender;
		strs[3] = rank.toString();
		strs[4] = position.toString();
		strs[5] = Long.toString(hotelId);
		return strs;
	}
	
	public String[] toCSVHeaders() {
		return headers;
	}

}
