package jp.co.worksap.intern.function;

import java.util.Scanner;
import java.util.Date;
import java.util.List;

import jp.co.worksap.intern.entities.reservation.ReservationDTO;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.reader.InputReader;
import jp.co.worksap.intern.util.Utilities;
import jp.co.worksap.intern.constants.Channel;
import jp.co.worksap.intern.entities.room.RoomDTO;
import jp.co.worksap.intern.writer.IResultWriter;
import jp.co.worksap.intern.writer.ResultWriterImpl;

public class Reservation {
	private static List<ReservationDTO> reservationDTOs = InputReader.getReservationDTOs();
	
	public void reservation() {
		RoomType roomType = readRoomType();
		Integer numOfRoom = readNumOfRoom();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nPlease enter the customer's name: ");
		String name = scanner.nextLine();
		String telNumber = readTelNumber(scanner);
		
		Channel channel = readChannel();
		Float roomPrice = readRoomPrice(roomType);
		
		Date checkInDate = null;
		while (checkInDate == null) {
			System.out.println("\nPlease enter the check in date (YYYY/MM/DD): ");
			String dateStr = scanner.nextLine();
			checkInDate = Utilities.parseDateStr(dateStr);
			if (checkInDate == null) {
				System.out.println("\nInvalid check in date, check and try again.");
			}
		}
		
		Date checkOutDate = null;
		while (checkOutDate == null) {
			System.out.println("\nPlease enter the check out date (YYYY/MM/DD): ");
			String dateStr = scanner.nextLine();
			checkOutDate = Utilities.parseDateStr(dateStr);
			if (checkOutDate == null) {
				System.out.println("\nInvalid check out date, check and try again.");
			}
		}
		
		reservationDTOs.add(new ReservationDTO(roomType, numOfRoom, name, telNumber, 
				channel, roomPrice, checkInDate, checkOutDate));
		
		writeReservationFile();
		
		System.out.println("\nReservation has been made successfully!");
		return;
	}
	
	private void writeReservationFile() {
		IResultWriter writer = new ResultWriterImpl("files/RESERVATIONS.csv");
		writer.writeCsvResult(reservationDTOs.toArray(new ReservationDTO[]{}));
	}
	
	public static Float readRoomPrice(RoomType roomType) {
		List<RoomDTO> roomDTOs = InputReader.getRoomDTOs();
		for (RoomDTO type : roomDTOs) {
			if (type.getRoomType() == roomType) {
				return type.getPrice();
			}
		}
		return -1f;
	}
	
	private String readTelNumber(Scanner scanner) {
		while (true) {
			System.out.println("\nPlease enter the customer's telephone number: ");
			String telStr = scanner.nextLine();
			if (telStr.length() == 0) {
				System.out.println("\nInvalid telephone number, check and try again.");
				continue;
			}
			
			Boolean isValid = true;
			for (char ch : telStr.toCharArray()) {
				if (ch<'0' || ch>'9') {
					System.out.println("\nInvalid telephone number, check and try again.");
					isValid = false;
					break;
				}
			}
			
			if (isValid) {
				return telStr;
			}
		}
	}
	
	private Channel readChannel() {
		String[] channelType = new String[Channel.values().length];
		for (Channel type : Channel.values()) {
			channelType[type.toInteger()] = type.toString();
		}
		System.out.println("\nPlease select a channel type.");
		Integer typeId = Utilities.readInputSelection("ChannelType", channelType);
		Channel type = Channel.valueOfString(channelType[typeId-1]);
		return type;
	}
	
	private Integer readNumOfRoom() {
		System.out.println("\nPlease select number of rooms to reserve: ");
		Integer numOfRoom = Utilities.readInteger();
		while (numOfRoom <= 0) {
			System.out.println("\nInvalid room numbers, check and try again.");
			System.out.println("\nPlease select number of rooms to reserve: ");
			numOfRoom = Utilities.readInteger();
		}
		return numOfRoom;
	}
	
	private RoomType readRoomType() {
		String[] roomType = new String[RoomType.values().length];
		for (RoomType type : RoomType.values()) {
			roomType[type.toInteger()] = type.toString();
		}
		System.out.println("\nPlease select a room type.");
		Integer typeId = Utilities.readInputSelection("RoomType", roomType);
		RoomType type = RoomType.valueOfString(roomType[typeId-1]);
		return type;
	}
}
