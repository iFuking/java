package jp.co.worksap.intern.entities.customer;

import java.io.IOException;
import java.util.Date;

import jp.co.worksap.intern.constants.Constants;
import jp.co.worksap.intern.entities.AbstractDTOReader;
import jp.co.worksap.intern.util.Utilities;

public class CustomerDTOReader extends AbstractDTOReader<CustomerDTO> {
	private static final int COLUMN_INDEX_CUSTOMER_ID = 0;
	private static final int COLUMN_INDEX_NAME = 1;
	private static final int COLUMN_INDEX_GENDER = 2;
	private static final int COLUMN_INDEX_BIRTHDAY = 3;
	private static final int COLUMN_INDEX_NATIONALITY = 4;
	private static final int COLUMN_INDEX_PASSPORT_NO = 5;
	private static final int COLUMN_INDEX_ADDRESS = 6;
	private static final int COLUMN_INDEX_EMAIL = 7;
	private static final int COLUMN_INDEX_TEL = 8;

	private String fileAddress = Constants.DEFAULT_CSV_FOLDER
			+ "CUSTOMER_MST.csv";

	/**
	 * use default file address
	 * 
	 * @throws IOException
	 */
	public CustomerDTOReader() throws IOException {
		super(CustomerDTOReader.class.getName());
		super.init();
	}

	/**
	 * use customize file address
	 * 
	 * @param fileAddress
	 * @throws IOException
	 */
	public CustomerDTOReader(final String fileAddress) throws IOException {
		super(CustomerDTOReader.class.getName());
		this.fileAddress = fileAddress;
		super.init();
	}

	@Override
	protected String getFileArress() {
		return fileAddress;
	}

	@Override
	protected CustomerDTO convertArrayToDTO(String[] value) throws IOException {

		Long customerId = Long.valueOf(value[COLUMN_INDEX_CUSTOMER_ID]);
		String name = value[COLUMN_INDEX_NAME];
		String gender = value[COLUMN_INDEX_GENDER];
		Date birthday = Utilities.parseDateStr(value[COLUMN_INDEX_BIRTHDAY]);
		String nationality = value[COLUMN_INDEX_NATIONALITY];
		String passportNo = value[COLUMN_INDEX_PASSPORT_NO];
		String address = value[COLUMN_INDEX_ADDRESS];
		String email = value[COLUMN_INDEX_EMAIL];
		String tel = value[COLUMN_INDEX_TEL];

		CustomerDTO dto = new CustomerDTO(customerId, name, gender, birthday,
				nationality, passportNo, address, email, tel);
		return dto;
	}
}
