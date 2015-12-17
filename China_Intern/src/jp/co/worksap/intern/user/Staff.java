package jp.co.worksap.intern.user;

import jp.co.worksap.intern.function.Reservation;
import jp.co.worksap.intern.util.Utilities;

public class Staff implements User {
	
	public void entry() {
		String[] staffFuncs = {
			"Reservation", 
			"Confirmation", 
			"Cancel reservation",
			"Check in",
			"Check out",
			"Customer black list",
			"Alert for housekeeping",
			"Exit"
		};
		
		Reservation resv = new Reservation();
		while (true) {
			System.out.println("\nPlease select a business flow function. ");
			Integer funcId = Utilities.readInputSelection("Func", staffFuncs);
			
			switch (funcId) {
			case 1:
				resv.reservation();
				break;
			case 8:
				return;
			default:
				System.out.println("This business flow function is under developing.");
				System.out.println("Please choose another one. ");
				break;
			}
				
		}
	}
}
