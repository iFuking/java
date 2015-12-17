package jp.co.worksap.intern.entities.hotel;

import java.io.IOException;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.AbstractDTOReader;

public class HotelMstDTOReader extends AbstractDTOReader<HotelMstDTO> {
	private static final int COLUMN_INDEX_HOTEL_ID = 0;
	private static final int COLUMN_INDEX_HOTEL_NAME = 1;
	private static final int COLUMN_INDEX_ADDRESS = 2;
	private static final int COLUMN_INDEX_TEL = 3;
	private static final int COLUMN_INDEX_STARS = 4;

	private String fileAddress = Constants.DEFAULT_CSV_FOLDER + "HOTEL_MST.csv";

	/**
	 * use default file address
	 * 
	 * @throws IOException
	 */
	public HotelMstDTOReader() throws IOException {
		super(HotelMstDTOReader.class.getName());
		super.init();
	}

	/**
	 * use customize file address
	 * 
	 * @param fileAddress
	 * @throws IOException
	 */
	public HotelMstDTOReader(final String fileAddress) throws IOException {
		super(HotelMstDTOReader.class.getName());
		this.fileAddress = fileAddress;
		super.init();
	}

	@Override
	protected String getFileArress() {
		return fileAddress;
	}

	@Override
	protected HotelMstDTO convertArrayToDTO(String[] value) throws IOException {
		Long hotelId = Long.valueOf(value[COLUMN_INDEX_HOTEL_ID]);
		String hotelName = value[COLUMN_INDEX_HOTEL_NAME];
		String address = value[COLUMN_INDEX_ADDRESS];
		String tel = value[COLUMN_INDEX_TEL];
		Integer stars = Integer.valueOf(value[COLUMN_INDEX_STARS]);

		HotelMstDTO dto = new HotelMstDTO(hotelId,hotelName,address,tel,stars);
		return dto;
	}
}
