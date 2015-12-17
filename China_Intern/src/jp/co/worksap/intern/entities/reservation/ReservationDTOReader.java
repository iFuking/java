package jp.co.worksap.intern.entities.reservation;

import java.io.IOException;
import java.util.Date;

import jp.co.worksap.intern.entities.AbstractDTOReader;
import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.room.RoomType;
import jp.co.worksap.intern.util.Utilities;
import jp.co.worksap.intern.constants.Channel;

public class ReservationDTOReader extends AbstractDTOReader<ReservationDTO> {
	private static final int COLUMN_INDEX_ROOM_TYPE = 0;
	private static final int COLUMN_INDEX_NUM_OF_ROOMS = 1;
	private static final int COLUMN_INDEX_CUSTOMER_NAME = 2;
	private static final int COLUMN_INDEX_CUSTOMER_TEL = 3;
	private static final int COLUMN_INDEX_CHANNEL = 4;
	private static final int COLUMN_INDEX_UNIT_PRICE = 5;
	private static final int COLUMN_INDEX_CHECK_IN_DATE = 6;
	private static final int COLUMN_INDEX_CHECK_OUT_DATE = 7;
	
	private String fileAddress = Constants.DEFAULT_CSV_FOLDER
			+ "RESERVATIONS.csv";
	/**
	 * use default file address
	 * 
	 * @throws IOException
	 */
	public ReservationDTOReader() throws IOException {
		super(ReservationDTOReader.class.getName());
		super.init();
	}
	
	/**
	 * use customize file address
	 * 
	 * @param fileAddress
	 * @throws IOException
	 */
	public ReservationDTOReader(final String fileAddress) throws IOException {
		super(ReservationDTOReader.class.getName());
		this.fileAddress = fileAddress;
		super.init();
	}

	protected ReservationDTO convertArrayToDTO(String[] value) throws IOException {
		RoomType roomType = RoomType.valueOf(value[COLUMN_INDEX_ROOM_TYPE]);
		Integer numOfRooms = Integer.valueOf(value[COLUMN_INDEX_NUM_OF_ROOMS]);
		String customerName = value[COLUMN_INDEX_CUSTOMER_NAME];
		String customerTel = value[COLUMN_INDEX_CUSTOMER_TEL];
		Channel channel = Channel.valueOf(value[COLUMN_INDEX_CHANNEL]);
		Float unitPrice = Float.valueOf(value[COLUMN_INDEX_UNIT_PRICE]);
		Date checkInDate = Utilities.parseDateStr(value[COLUMN_INDEX_CHECK_IN_DATE]);
		Date checkOutDate = Utilities.parseDateStr(value[COLUMN_INDEX_CHECK_OUT_DATE]);
		return new ReservationDTO(roomType, numOfRooms, customerName, customerTel, 
				channel, unitPrice, checkInDate, checkOutDate);
	}

	protected String getFileArress() {
		return fileAddress;
	}
}