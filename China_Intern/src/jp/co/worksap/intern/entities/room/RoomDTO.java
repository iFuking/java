package jp.co.worksap.intern.entities.room;

import jp.co.worksap.intern.entities.ICsvMasterDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.constants.PriceUnitType;

public class RoomDTO implements ICsvMasterDTO {
	
	private static final long serialVersionUID = -5538243149394207296L;
	
	private Integer roomId;
	private RoomType roomType;
	private Float price;
	private PriceUnitType priceUnit;
	
	public RoomDTO(Integer roomId, RoomType roomType, Float price, 
			PriceUnitType priceUnit) {
		this.roomId = roomId;
		this.roomType = roomType;
		this.price = price;
		this.priceUnit = priceUnit;
	}
	
	public Integer getRoomId() {
		return roomId;
	}
	
	public RoomType getRoomType() {
		return roomType;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public PriceUnitType getPriceUnit() {
		return priceUnit;
	}
	
	
	public String[] toCSVHeaders() {
		return null;
	}
	
	public String[] toCSVStringArray() {
		return null;
	}
	
}
