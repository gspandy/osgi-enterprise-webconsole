import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;


public class LearningTest {
	
	@Test
	public void testMe() throws Exception {
		ObjectMapper om = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		map.put("key2", "ccc");
		om.writeValue(System.out, map);
	}

}
