package cn.com.jtang.healthcloud.pojo;

import cn.com.jtang.healthcloud.pojo.User;
import cn.com.jtang.healthcloud.pojo.Report;
import cn.com.jtang.healthcloud.pojo.Relation;
import cn.com.jtang.healthcloud.pojo.Cache;
import cn.com.jtang.healthcloud.dao.ManageUser;
import cn.com.jtang.healthcloud.dao.ManageReport;
import cn.com.jtang.healthcloud.dao.ManageCache;
import java.util.List;

public class Test {
	private void testManageUser() {
		ManageUser manager = new ManageUser();
		
		List<User> users = manager.listUser(1, 15);
		for (int i = 0; i < users.size(); ++i) {
			System.out.println(users.get(i).getUserId() + ", " + users.get(i).getOpenId());
		}
		return;
	}
	
	private void testManageReport() {
		ManageReport manager = new ManageReport();
		
		String openId = "Alizee";
		Relation rel = new Relation();
		rel.setOpenId("Alizee").setReportId("0x4000").setDeviceId("mac").setTimestamp("20150910");
		Report rep = new Report();
		rep.setReportId("0x4000").setTimestamp("20150909").setAdvice("run");
		manager.addOrUpdateReport(openId, rel, rep);
		
		
		Report report = manager.selectWesternMedicineReport("0x4000");
		report = manager.selectWesternMedicineReport("0x1000");
		
		
		List<Report> reportList = manager.listWesternMedicineReport("Alizee");
		return;
	}
	
	private void testManageCache() {
		ManageCache manager = new ManageCache();
		
		/*Cache cache = new Cache();
		cache.setReportId("0x7fffffff").setContent("json爱");
		manager.addCache(cache);*/
		
		Cache cache = manager.selectCache("dick");
		cache = manager.selectCache("0x7fffffff");
		return;
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		test.testManageUser();
		return;
	}
}
