package jp.co.worksap.intern.entities.room;

import java.io.IOException;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.constants.PriceUnitType;
import jp.co.worksap.intern.entities.AbstractDTOReader;

public class RoomDTOReader extends AbstractDTOReader<RoomDTO>{
	
	private static final int COLUMN_INDEX_ROOM_ID = 0;
	private static final int COLUMN_INDEX_TYPE = 1;
	private static final int COLUMN_INDEX_PRICE = 2;
	private static final int COLUMN_INDEX_PRICE_UNIT = 3;
	
	private String fileAddress = Constants.DEFAULT_CSV_FOLDER+"ROOM_MST.csv";
	
	/**
	 * use default file address
	 * 
	 * @throws IOException
	 */
	public RoomDTOReader() throws IOException {
		super(RoomDTOReader.class.getName());
		super.init();
	}

	/**
	 * use customize file address
	 * 
	 * @param fileAddress
	 * @throws IOException
	 */
	public RoomDTOReader(final String fileAddress) throws IOException {
		super(RoomDTOReader.class.getName());
		this.fileAddress = fileAddress;
		super.init();
	}

	@Override
	protected String getFileArress() {
		return fileAddress;
	}

	@Override
	protected RoomDTO convertArrayToDTO(String[] value) throws IOException {

		Integer roomId = Integer.valueOf(value[COLUMN_INDEX_ROOM_ID]);
		RoomType roomType = RoomType.valueOfString(value[COLUMN_INDEX_TYPE]);
		Float price = Float.valueOf(value[COLUMN_INDEX_PRICE]);
		PriceUnitType priceUnit = PriceUnitType.valueOfString(value[COLUMN_INDEX_PRICE_UNIT]);
		
		RoomDTO dto = new RoomDTO(roomId, roomType, price, priceUnit);
		return dto;
	}
}
