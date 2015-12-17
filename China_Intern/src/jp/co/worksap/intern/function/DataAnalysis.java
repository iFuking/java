package jp.co.worksap.intern.function;

import java.util.List;
import java.util.Calendar;
import java.util.LinkedList;

import jp.co.worksap.intern.constants.Channel;
import jp.co.worksap.intern.entities.reservation.ReservationDTO;
import jp.co.worksap.intern.reader.InputReader;
import jp.co.worksap.intern.util.Utilities;
import jp.co.worksap.intern.writer.IResultWriter;
import jp.co.worksap.intern.writer.ResultWriterImpl;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.entities.report.ReportDTO;

public class DataAnalysis {
	private static List<ReservationDTO> reservationDTOs = InputReader.getReservationDTOs();
	private static enum Period {
		WEEKLY, MONTHLY, YEARLY
	};
	private static Integer dayMillSecond = 1000*3600*24;
	
	private Period readPeriod() {
		Integer pId = Utilities.readInputSelection("Period", 
				new String[] {"WEEKLY", "MONTHLY", "YEARLY"});
		return Period.values()[pId-1];
	}
	
	private Calendar[] setPeriodCaln(Period period) {
		Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		
		start.set(Calendar.HOUR_OF_DAY, 0); start.set(Calendar.MINUTE, 0); start.set(Calendar.SECOND, 0);
		end.set(Calendar.HOUR_OF_DAY, 0); end.set(Calendar.MINUTE, 0); end.set(Calendar.SECOND, 0);
		last.set(Calendar.HOUR_OF_DAY, 0); last.set(Calendar.MINUTE, 0); last.set(Calendar.SECOND, 0);
	
		switch (period) {
		case WEEKLY:
			start.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			end.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			last.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			last.add(Calendar.DATE, -7);
			end.add(Calendar.DATE, 7);
			break;
		case MONTHLY:
			start.set(Calendar.DAY_OF_MONTH, 1);
			end.set(Calendar.DAY_OF_MONTH, 1);
			last.set(Calendar.DAY_OF_MONTH, 1);
			last.add(Calendar.DATE, -30);
			end.add(Calendar.DATE, 30);
			break;
		case YEARLY:
			start.set(Calendar.MONTH, Calendar.JANUARY);
			start.set(Calendar.DAY_OF_MONTH, 1);
			end.set(Calendar.MONTH, Calendar.JANUARY);
			end.set(Calendar.DAY_OF_MONTH, 1);
			last.set(Calendar.MONTH, Calendar.JANUARY);
			last.set(Calendar.DAY_OF_MONTH, 1);
			last.add(Calendar.YEAR, -1);
			end.add(Calendar.YEAR, 1);
			break;
		default:
			break;
		}
		return new Calendar[]{last, start, end};
	}
	
	private List<ReservationDTO> getResvByPeriodCaln(Calendar start, Calendar end) {
		List<ReservationDTO> resvDTOs = InputReader.getReservationDTOs();
		List<ReservationDTO> list = new LinkedList<ReservationDTO>();
		
		Integer startDay = start.get(Calendar.DAY_OF_YEAR);
		Integer endDay = end.get(Calendar.DAY_OF_YEAR);
		for (ReservationDTO dto : resvDTOs) {
			Calendar caln = Calendar.getInstance();
			caln.setTime(dto.getCheckInDate());
			Integer day = caln.get(Calendar.DAY_OF_YEAR);
			if (day>=startDay && day<endDay) {
				list.add(dto);
			}
		}
		return list;
	}
	
	public void imprvRevenueAnalysis() {
		Period period = readPeriod();
		Calendar[] periodCaln = setPeriodCaln(period);
		/*System.out.println(periodCaln[0].get(Calendar.DAY_OF_YEAR));
		System.out.println(periodCaln[1].get(Calendar.DAY_OF_YEAR));
		System.out.println(periodCaln[2].get(Calendar.DAY_OF_YEAR));
		*/
		List<ReservationDTO> lastResv = getResvByPeriodCaln(periodCaln[0], 
				periodCaln[1]);
		List<ReservationDTO> currResv = getResvByPeriodCaln(periodCaln[1], 
				periodCaln[2]);
		
		Float[] lastByRoomType = new Float[RoomType.getRoomTypeNums()];
		Float[] lastByChannel = new Float[Channel.getChannelNums()];
		Float lastTotalRevenue = 0.0f;
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			lastByRoomType[i] = 0.0f;
		}
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			lastByChannel[i] = 0.0f;
		}
		
		for (int i = 0; i < lastResv.size(); ++i) {
			ReservationDTO dto = lastResv.get(i);
			Long days = (dto.getCheckOutDate().getTime()-dto.getCheckInDate().getTime())/dayMillSecond;
			lastByRoomType[dto.getRoomType().toInteger()] += dto.getNumOfRooms()*dto.getUnitPrice()*days;
			lastByChannel[dto.getChannel().toInteger()] += dto.getNumOfRooms()*dto.getUnitPrice()*days;
			lastTotalRevenue += dto.getNumOfRooms()*dto.getUnitPrice()*days;
		}
		
		
		Float[] currByRoomType = new Float[RoomType.getRoomTypeNums()];
		Float[] currByChannel = new Float[Channel.getChannelNums()];
		Float currTotalRevenue = 0.0f;
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			currByRoomType[i] = 0.0f;
		}
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			currByChannel[i] = 0.0f;
		}
		
		for (int i = 0; i < currResv.size(); ++i) {
			ReservationDTO dto = currResv.get(i);
			Long days = (dto.getCheckOutDate().getTime()-dto.getCheckInDate().getTime())/dayMillSecond;
			currByRoomType[dto.getRoomType().toInteger()] += dto.getNumOfRooms()*dto.getUnitPrice()*days;
			currByChannel[dto.getChannel().toInteger()] += dto.getNumOfRooms()*dto.getUnitPrice()*days;
			currTotalRevenue += dto.getNumOfRooms()*dto.getUnitPrice()*days;
		}
		
		
		List<ReportDTO> roomType = new LinkedList<ReportDTO>();
		System.out.println("\n\nRoomType, LastRevenue, CurrRevenue, LastPercentage, CurrPercentage, Growth");
		for (RoomType type : RoomType.values()) {
			Float lastPercentage = 100.0f*lastByRoomType[type.toInteger()]/lastTotalRevenue;
			Float currPercentage = 100.0f*currByRoomType[type.toInteger()]/currTotalRevenue;
			roomType.add(new ReportDTO(type.toString(), Math.round(lastByRoomType[type.toInteger()]), Math.round(currByRoomType[type.toInteger()]),
					String.format("%.2f", lastPercentage)+"%", String.format("%.2f", currPercentage)+"%",
					String.format("%.2f", currPercentage-lastPercentage)+"%"));
			System.out.println(type.toString()+", "+
					lastByRoomType[type.toInteger()]+", "+currByRoomType[type.toInteger()]+", "+
					String.format("%.2f", lastPercentage)+"%, "+String.format("%.2f", currPercentage)+"%, "+
					String.format("%.2f", currPercentage-lastPercentage)+"%");
		}
		writeRevenueRoomTypeFile(roomType);
		
		List<ReportDTO> channel = new LinkedList<ReportDTO>();
		System.out.println("\n\nChannel, LastRevenue, CurrRevenue, LastPercentage, CurrPercentage, Growth");
		for (Channel type : Channel.values()) {
			Float lastPercentage = 100.0f*lastByChannel[type.toInteger()]/lastTotalRevenue;
			Float currPercentage = 100.0f*currByChannel[type.toInteger()]/currTotalRevenue;
			channel.add(new ReportDTO(type.toString(), Math.round(lastByChannel[type.toInteger()]), Math.round(currByChannel[type.toInteger()]),
					String.format("%.2f", lastPercentage)+"%", String.format("%.2f", currPercentage)+"%",
					String.format("%.2f", currPercentage-lastPercentage)+"%"));
			System.out.println(type.toString()+", "+
					lastByChannel[type.toInteger()]+", "+currByChannel[type.toInteger()]+", "+
					String.format("%.2f", lastPercentage)+"%, "+String.format("%.2f", currPercentage)+"%, "+
					String.format("%.2f", currPercentage-lastPercentage)+"%");
		}
		writeRevenueChannelFile(channel);
		return;
	}
	
	private void writeRevenueRoomTypeFile(List<ReportDTO> roomType) {
		IResultWriter writer = new ResultWriterImpl("output/REVENUE_ROOMTYPE.csv");
		writer.writeCsvResult(roomType.toArray(new ReportDTO[]{}));
	}
	
	private void writeRevenueChannelFile(List<ReportDTO> channel) {
		IResultWriter writer = new ResultWriterImpl("output/REVENUE_CHANNEL.csv");
		writer.writeCsvResult(channel.toArray(new ReportDTO[]{}));
	}
	
	public void imprvRoomOccupancyAnalysis() {
		Period period = readPeriod();
		Calendar[] periodCaln = setPeriodCaln(period);
		
		List<ReservationDTO> lastResv = getResvByPeriodCaln(periodCaln[0], 
				periodCaln[1]);
		List<ReservationDTO> currResv = getResvByPeriodCaln(periodCaln[1], 
				periodCaln[2]);
		
		Integer[] lastByRoomType = new Integer[RoomType.getRoomTypeNums()];
		Integer lastTotalNums = 0;
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			lastByRoomType[i] = 0;
		}
		for (int i = 0; i < lastResv.size(); ++i) {
			ReservationDTO resv = lastResv.get(i);
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			lastByRoomType[resv.getRoomType().toInteger()] += resv.getNumOfRooms()*(new Long(days).intValue());
			lastTotalNums += resv.getNumOfRooms()*(new Long(days).intValue());
		}
		
		Integer[] currByRoomType = new Integer[RoomType.getRoomTypeNums()];
		Integer currTotalNums = 0;
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			currByRoomType[i] = 0;
		}
		for (int i = 0; i < currResv.size(); ++i) {
			ReservationDTO resv = currResv.get(i);
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			currByRoomType[resv.getRoomType().toInteger()] += resv.getNumOfRooms()*(new Long(days).intValue());
			currTotalNums += resv.getNumOfRooms()*(new Long(days).intValue());
		}
		
		List<ReportDTO> roomType = new LinkedList<ReportDTO>();
		System.out.println("\n\nRoomType, LastRoomOccupancy, CurrRoomOccupancy, LastPercentage, CurrPercentage, Growth");
		for (RoomType type : RoomType.values()) {
			Float lastPercentage = 100.0f*lastByRoomType[type.toInteger()]/lastTotalNums;
			Float currPercentage = 100.0f*currByRoomType[type.toInteger()]/currTotalNums;
			roomType.add(new ReportDTO(type.toString(), lastByRoomType[type.toInteger()], currByRoomType[type.toInteger()],
					String.format("%.2f", lastPercentage)+"%", String.format("%.2f", currPercentage)+"%",
					String.format("%.2f", currPercentage-lastPercentage)+"%"));
			System.out.println(type.toString()+", "+lastByRoomType[type.toInteger()]+", "+currByRoomType[type.toInteger()]+", "+
					String.format("%.2f", lastPercentage)+"%, "+String.format("%.2f", currPercentage)+"%, "+
					String.format("%.2f", currPercentage-lastPercentage)+"%");
		}
		writeRoomOccupancyFile(roomType);
		return;
	}
	
	private void writeRoomOccupancyFile(List<ReportDTO> roomType) {
		IResultWriter writer = new ResultWriterImpl("output/ROOMTYPE.csv");
		writer.writeCsvResult(roomType.toArray(new ReportDTO[]{}));
	}
	
	public void imprvChannelAnalysis() {
		Period period = readPeriod();
		Calendar[] periodCaln = setPeriodCaln(period);
		
		List<ReservationDTO> lastResv = getResvByPeriodCaln(periodCaln[0], 
				periodCaln[1]);
		List<ReservationDTO> currResv = getResvByPeriodCaln(periodCaln[1], 
				periodCaln[2]);
		
		Integer[] lastByChannel = new Integer[Channel.getChannelNums()];
		Integer lastTotalNums = 0;
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			lastByChannel[i] = 0;
		}
		for (int i = 0; i < lastResv.size(); ++i) {
			ReservationDTO resv = lastResv.get(i);
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			lastByChannel[resv.getChannel().toInteger()] += new Long(days).intValue();
			lastTotalNums += new Long(days).intValue();
		}
		
		Integer[] currByChannel = new Integer[Channel.getChannelNums()];
		Integer currTotalNums = 0;
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			currByChannel[i] = 0;
		}
		for (int i = 0; i < currResv.size(); ++i) {
			ReservationDTO resv = currResv.get(i);
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			currByChannel[resv.getChannel().toInteger()] += new Long(days).intValue();
			currTotalNums += new Long(days).intValue();
		}
		
		List<ReportDTO> channel = new LinkedList<ReportDTO>();
		System.out.println("\n\nChannel, LastChannelOccupancy, CurrChannelOccupancy, LastPercentage, CurrPercentage, Growth");
		for (Channel type : Channel.values()) {
			Float lastPercentage = 100.0f*lastByChannel[type.toInteger()]/lastTotalNums;
			Float currPercentage = 100.0f*currByChannel[type.toInteger()]/currTotalNums;
			channel.add(new ReportDTO(type.toString(), lastByChannel[type.toInteger()], currByChannel[type.toInteger()],
					String.format("%.2f", lastPercentage)+"%", String.format("%.2f", currPercentage)+"%",
					String.format("%.2f", currPercentage-lastPercentage)+"%"));
			System.out.println(type.toString()+", "+lastByChannel[type.toInteger()]+", "+currByChannel[type.toInteger()]+", "+
					String.format("%.2f", lastPercentage)+"%, "+String.format("%.2f", currPercentage)+"%, "+
					String.format("%.2f", currPercentage-lastPercentage)+"%");
		}
		writeChannelFile(channel);
		return;
	}
	
	private void writeChannelFile(List<ReportDTO> channel) {
		IResultWriter writer = new ResultWriterImpl("output/CHANNEL.csv");
		writer.writeCsvResult(channel.toArray(new ReportDTO[]{}));
	}
	
	public void revenueAnalysis() {
		Float[] byRoomType = new Float[RoomType.getRoomTypeNums()];
		Float[] byChannel = new Float[Channel.getChannelNums()];
		Float totalRevenue = 0.0f;
		
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			byRoomType[i] = 0.0f;
		}
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			byChannel[i] = 0.0f;
		}
		for (int i = 0; i < reservationDTOs.size(); ++i) {
			ReservationDTO resv = reservationDTOs.get(i);
			Integer dayMillSecond = 1000*3600*24;
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			byRoomType[resv.getRoomType().toInteger()] += resv.getNumOfRooms()*resv.getUnitPrice()*days;
			byChannel[resv.getChannel().toInteger()] += resv.getNumOfRooms()*resv.getUnitPrice()*days;
			totalRevenue += resv.getNumOfRooms()*resv.getUnitPrice()*days;
		}
		
		System.out.println("\n\nRoomType, Revenue, Percentage");
		for (RoomType type : RoomType.values()) {
			System.out.println(type.toString()+", "+byRoomType[type.toInteger()]+", "+
					String.format("%.2f", 100.0f*byRoomType[type.toInteger()]/totalRevenue)+"%");
		}
		
		System.out.println("\n\nChannel, Revenue, Percentage");
		for (Channel type : Channel.values()) {
			System.out.println(type.toString()+", "+byChannel[type.toInteger()]+", "+
					String.format("%.2f", 100.0f*byChannel[type.toInteger()]/totalRevenue)+"%");
		}
		return;
	}
	
	public void roomOccupancyAnalysis() {
		Integer[] byRoomType = new Integer[RoomType.getRoomTypeNums()];
		Integer totalNums = 0;
		for (int i = 0; i < RoomType.getRoomTypeNums(); ++i) {
			byRoomType[i] = 0;
		}
		for (int i = 0; i < reservationDTOs.size(); ++i) {
			ReservationDTO resv = reservationDTOs.get(i);
			Integer dayMillSecond = 1000*3600*24;
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			byRoomType[resv.getRoomType().toInteger()] += resv.getNumOfRooms()*(new Long(days).intValue());
			totalNums += resv.getNumOfRooms()*(new Long(days).intValue());
		}
		
		System.out.println("\n\nRoomType, RoomOccupancy, Percentage");
		for (RoomType type : RoomType.values()) {
			System.out.println(type.toString()+", "+byRoomType[type.toInteger()]+", "+
					String.format("%.2f", 100.0f*byRoomType[type.toInteger()]/totalNums)+"%");
		}
		return;
	}
	
	public void channelAnalysis() {
		Integer[] byChannel = new Integer[Channel.getChannelNums()];
		Integer totalNums = 0;
		for (int i = 0; i < Channel.getChannelNums(); ++i) {
			byChannel[i] = 0;
		}
		for (int i = 0; i < reservationDTOs.size(); ++i) {
			ReservationDTO resv = reservationDTOs.get(i);
			Integer dayMillSecond = 1000*3600*24;
			Long days = (resv.getCheckOutDate().getTime()-resv.getCheckInDate().getTime())/dayMillSecond;
			byChannel[resv.getChannel().toInteger()] += new Long(days).intValue();
			totalNums += new Long(days).intValue();
		}
		
		System.out.println("\n\nChannel, ChannelOccupancy, Percentage");
		for (Channel type : Channel.values()) {
			System.out.println(type.toString()+", "+byChannel[type.toInteger()]+", "+
					String.format("%.2f", 100.f*byChannel[type.toInteger()]/totalNums)+"%");
		}
		return;
	}
}
