package jp.co.worksap.intern.user;

import jp.co.worksap.intern.function.DataAnalysis;
import jp.co.worksap.intern.util.Utilities;

public class Manager implements User {
	
	public void entry() {
		String[] managerFuncs = new String[] {
				"Revenue/Profit analysis as per room types, channels, date",
				"Room occupancy analysis in different types and date",
				"Channels analysis",
				"Exit"
		};
		
		DataAnalysis alys = new DataAnalysis();
		while (true) {
			System.out.println("\nPlease select a analysis function.");
			Integer funcId = Utilities.readInputSelection("Func", managerFuncs);
			
			switch (funcId) {
			case 1:
				alys.imprvRevenueAnalysis();
				break;
			case 2:
				alys.imprvRoomOccupancyAnalysis();
				break;
			case 3:
				alys.imprvChannelAnalysis();
				break;
			case 4:
				return;
			default:
				System.out.println("This analysis function is under developing.");
				System.out.println("Please choose another one.");
				break;
			}
		}
	}
}
