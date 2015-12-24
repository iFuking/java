package jp.co.worksap.intern.main;

import jp.co.worksap.intern.entities.staff.StaffDTO;
import jp.co.worksap.intern.reader.InputReader;
import jp.co.worksap.intern.util.Utilities;
import jp.co.worksap.intern.user.Manager;
import jp.co.worksap.intern.user.Staff;

/**
 * Main entry function of the project.
 * 
 * @author dirk
 */

public class Main {
	public static void main(String[] args) {
		System.out.println("\n\n#==================================#");
		System.out.println("#                                  #");
		System.out.println("#  Welcome to Hotel Intelligence!  #");
		System.out.println("#                                  #");
		System.out.println("#==================================#\n\n");
		
		StaffDTO staff;
		while (true) {
			System.out.println("Please enter your staff id: ");
			Long staffId = new Long(Utilities.readInteger());
			staff = InputReader.getStaffDTOByStaffId(staffId);
			if (staff == null) {
				System.out.println("Invalid staff id, check and try again.");
				continue;
			}
			break;
		}
		
		System.out.println("\nLogin as "+staff.getPosition()+", welcome "+staff.getName());
		switch (staff.getPosition()) {
		case manager:
			new Manager().entry();
			break;
		case staff:
			new Staff().entry();
			break;
		default:
			System.out.println("This posotion function is under developing.");
			System.out.println("Please choose another one.");
			break;
		}
	}
}
