package jp.co.worksap.intern.entities.reservation;

import java.util.Date;

import jp.co.worksap.intern.entities.ICsvMasterDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.constants.Channel;
import jp.co.worksap.intern.util.Utilities;

public class ReservationDTO implements ICsvMasterDTO {
	
	/**
	 * 
	 */

	private static final long serialVersionUID = -5538243149394207296L;
	
	private RoomType roomType;
	private Integer numOfRooms;
	private String customerName;
	private String customerTel;
	private Channel channel;
	private Float unitPrice;
	private Date checkInDate;
	private Date checkOutDate;
	
	private static final String[] headers = new String[] {
			"roomType", "numOfRooms", "customerName", 
			"customerTel", "channel", "unitPrice", 
			"checkInDate", "checkOutDate"
		};
	
	public ReservationDTO(RoomType roomType, Integer numOfRooms,
			String customerName, String customerTel,
			Channel channel, Float unitPrice, Date checkInDate,
			Date checkOutDate) {
		this.roomType = roomType;
		this.numOfRooms = numOfRooms;
		this.customerName = customerName;
		this.customerTel = customerTel;
		this.channel = channel;
		this.unitPrice = unitPrice;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	public RoomType getRoomType() {
		return roomType;
	}
	public Integer getNumOfRooms() {
		return numOfRooms;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public Channel getChannel() {
		return channel;
	}
	public Float getUnitPrice() {
		return unitPrice;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	
	public String[] toCSVStringArray() {
		String[] csvStr = new String[headers.length];
		csvStr[0] = roomType.toString();
		csvStr[1] = Integer.toString(numOfRooms);
		csvStr[2] = customerName;
		csvStr[3] = customerTel;
		csvStr[4] = channel.toString();
		csvStr[5] = Float.toString(unitPrice);
		csvStr[6] = Utilities.formatDate(checkInDate);
		csvStr[7] = Utilities.formatDate(checkOutDate);
		return csvStr;
	}
	
	public String[] toCSVHeaders() {
		return headers;
	}
}
