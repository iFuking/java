package jp.co.worksap.intern.writer;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.util.Calendar;

import jp.co.worksap.intern.constants.Channel;
import jp.co.worksap.intern.entities.reservation.ReservationDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.function.Reservation;

public class WriteExp {
	
	public static void main(String[] args) {
		writeReservation();
	}
	
	private static void writeReservation() {
		List<ReservationDTO> resv = new LinkedList<ReservationDTO>();
		Random rand = new Random(System.currentTimeMillis());
		IResultWriter writer = new ResultWriterImpl("files/RESERVATIONS.csv");
		Calendar caln1 = Calendar.getInstance();
		Calendar caln2 = Calendar.getInstance();
		
		RoomType[] roomTypes = RoomType.values();
		Channel[] channels = Channel.values();
		String[] telHeader = {
				"134", "135", "136", "137", "138", "139", 
				"150", "151", "152", "158", "159", 
				"157", "182", "187", 
				"130", "131", "132", "155", "156", 
				"133", "153", "180", "189"
		};
		for (int i = 0; i < 50; ++i) {
			for (int j = 0; j < roomTypes.length; ++j) {
				Calendar checkIn = (Calendar) caln1.clone();
				Calendar checkOut = (Calendar) caln1.clone();
				checkOut.add(Calendar.DATE, rand.nextInt(3)+1);
				Channel ch = channels[rand.nextInt(channels.length)];
				Float price = Reservation.readRoomPrice(roomTypes[j]);
				Integer numOfRoom = rand.nextInt(5)+1;
				String name = "c"+rand.nextInt(1000);
				String tel = telHeader[rand.nextInt(telHeader.length)]+rand.nextInt(100000000);
				
				resv.add(new ReservationDTO(roomTypes[j], numOfRoom, name, tel, ch, 
						price, checkIn.getTime(), checkOut.getTime()));
			}
			caln1.add(Calendar.DATE, -3);
		}
		
		for (int i = 0; i < 50; ++i) {
			for (int j = 0; j < roomTypes.length; ++j) {
				Calendar checkIn = (Calendar) caln2.clone();
				Calendar checkOut = (Calendar) caln2.clone();
				checkOut.add(Calendar.DATE, rand.nextInt(3)+1);
				Channel ch = channels[rand.nextInt(channels.length)];
				Float price = Reservation.readRoomPrice(roomTypes[j]);
				Integer numOfRoom = rand.nextInt(5)+1;
				String name = "c"+rand.nextInt(1000);
				String tel = telHeader[rand.nextInt(telHeader.length)]+rand.nextInt(100000000);
				
				resv.add(new ReservationDTO(roomTypes[j], numOfRoom, name, tel, ch, 
						price, checkIn.getTime(), checkOut.getTime()));
			}
			caln2.add(Calendar.DATE, 3);
		}
		writer.writeCsvResult(resv.toArray(new ReservationDTO[]{}));
	}
}
