import java.util.List;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;

import org.jdom.*;
public class Deserializer {
	protected IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
	protected void setMap(String key, Object obj)
	{
		map.put(key, obj);
	}
	protected Object getMap(String key)
	{
		return map.get(key);
	}
	public Object deserialize(Document document) throws Exception
	{
		Element root = document.getRootElement();
		List<Element> children = root.getChildren();
		
		for(int i = 0; i < children.size(); i++)
		{
			deserializeObject(children.get(i));
		}
		for(int i = 0; i < children.size(); i++)
		{
			deserializeFields(children.get(i));
		}
		ClassC cc = (ClassC) getMap("0");
		return getMap("0");
	}
	
	protected void deserializeObject(Element e) throws Exception
	{
		Class c = Class.forName(e.getAttributeValue("class"));
		
		if(c.isArray()) 
		{
			Class type = c.getComponentType();
			String length = e.getAttributeValue("length");
			
			Object obj = Array.newInstance(type, Integer.parseInt(length));
			setMap(e.getAttributeValue("id"), obj);
		}
		else
		{
			Constructor constructor = c.getDeclaredConstructor(null);
			
			constructor.setAccessible(true);
			Object obj = constructor.newInstance(null);
			setMap(e.getAttributeValue("id"), obj);
		}
	}
	
	protected void deserializeFields(Element e) throws Exception
	{
		Class c = Class.forName(e.getAttributeValue("class"));
		
		List<Element> list = e.getChildren();
		
		for(int i = 0; i < list.size(); i++)
		{
			Element element = list.get(i);
			String type = element.getAttributeValue("declaringclass");
			try
			{
				Class declare = Class.forName(type);
				Content value = element.getChild("reference").getContent(0);
				Field field = c.getDeclaredField(element.getAttributeValue("name"));
				field.setAccessible(true);
				Object obj = getMap(value.getValue());
				field.set(getMap(e.getAttributeValue("id")), obj);
			}
			catch(ClassNotFoundException exception)
			{
				Content value = element.getChild("value").getContent(0);
				Field field = c.getDeclaredField(element.getAttributeValue("name"));
				field.setAccessible(true);
				
				Object obj = getMap(e.getAttributeValue("id"));
				if(field.getType() == int.class)
				{
					field.set(obj, Integer.parseInt(value.getValue()) + 1);
				}
				System.out.println(field.get(obj));
			}
		}
	}
}
