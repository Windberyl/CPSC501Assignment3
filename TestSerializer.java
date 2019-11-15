import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSerializer
{
	Serializer serialize = new Serializer();
	@Test
	void testSetMap()
	{
		Object obj = new Object();
		
		assertEquals(serialize.setMap(obj), 0);
		assertEquals(serialize.setMap(obj), -1);
	}

}
