package realtimeweb.util;

import java.util.HashMap;
import java.util.Map;

public final class RealtimeWebUtil {
	public static Integer nullToInt(Integer value){
		if(value==null){
			return -1;
		}else{
			return value;
		}
	}
}

