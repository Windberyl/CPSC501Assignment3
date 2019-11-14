import java.util.IdentityHashMap;
import java.lang.reflect.*;
import org.jdom.*;

public class Serializer {
	private IdentityHashMap<Object, String> map = new IdentityHashMap<Object, String>();
	
	private Element root = new Element("serialized");
	private Document document = new Document(root);
	
	private void setMap(Object obj)
	{
		map.put(obj, Integer.toString(map.size()));
	}
	
	private String getMap(Object obj)
	{
		return map.get(obj);
	}
	
	public Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Class c = obj.getClass();
		setMap(obj);
		
		Element e = new Element("Object");
		e.setAttribute(new Attribute("class", c.getName()));
		e.setAttribute(new Attribute("id", getMap(obj)));
		
		Field[] field = c.getDeclaredFields();
		for(int i = 0; i < field.length; i++)
		{
			field[i].setAccessible(true);
			
			Element f = new Element("Field");
			f.setAttribute(new Attribute("name", field[i].getName()));
			f.setAttribute(new Attribute("declaringclass", field[i].getType().getName()));
			
			if(field[i].getType().isPrimitive())
			{
				Element value = new Element("Value");
				value.addContent(field[i].get(obj).toString());
				f.setContent(value);
			}
			else
			{
				Object reference = field[i].get(obj);
			}
			e.addContent(f);
		}
		
		root.addContent(e);
		return document;
	}
}
