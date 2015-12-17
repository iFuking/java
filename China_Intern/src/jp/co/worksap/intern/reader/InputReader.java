package jp.co.worksap.intern.reader;

import java.util.List;
import java.util.HashMap;
import java.io.IOException;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.entities.staff.StaffDTOReader;
import jp.co.worksap.intern.entities.reservation.ReservationDTO;
import jp.co.worksap.intern.entities.reservation.ReservationDTOReader;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.entities.room.RoomDTOReader;

public class InputReader {
	
	private static List<StaffDTO> staffDTOs;
	private static HashMap<Long, StaffDTO> id2Staff;
	private static List<ReservationDTO> reservationDTOs;
	private static List<RoomDTO> roomDTOs;
	
	public static List<StaffDTO> getStaffDTOs() {
		if(staffDTOs == null) {
			try {
				staffDTOs = new StaffDTOReader().getValues();
				id2Staff = new HashMap<Long, StaffDTO>();
				for(StaffDTO s: staffDTOs)
					id2Staff.put(s.getStaffId(), s);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return staffDTOs;
	}
	
	public static StaffDTO getStaffDTOByStaffId(Long id){
		getStaffDTOs();
		return id2Staff.get(id);
	}
	
	public static List<ReservationDTO> getReservationDTOs(){
		if(reservationDTOs == null){
			try {
				reservationDTOs = new ReservationDTOReader().getValues();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return reservationDTOs;
	}
	
	public static List<RoomDTO> getRoomDTOs() {
		if (roomDTOs == null) {
			try {
				roomDTOs = new RoomDTOReader().getValues();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return roomDTOs;
	}
	
}
