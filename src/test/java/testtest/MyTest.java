package testtest;

import java.util.HashMap;
import java.util.Map;

import com.slave.autobean.MainEntry;

public class MyTest {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("prjPath", "c:/_auto_slave");
		map.put("packagename", "com.billy.jee.slave");
		map.put("entityname", "bean");
		map.put("controllername", "bo");
		map.put("servicename", "service");
		map.put("daoname", "persistence");
		
		MainEntry.main(map);
	}
}
