import java.util.Queue;
import java.util.LinkedList;
import java.util.IdentityHashMap;
import java.lang.reflect.*;
import org.jdom.*;

public class Serializer {
	protected IdentityHashMap<Object, String> map = new IdentityHashMap<Object, String>();
	protected Queue<Object> list = new LinkedList<Object>();
	protected Element root = new Element("serialized");
	protected Document document = new Document(root);
	
	protected int setMap(Object obj)
	{
		if(!map.containsKey(obj))
		{
			map.put(obj, Integer.toString(map.size()));
			return 0;
		}
		else return -1;
	}
	
	protected String getMap(Object obj)
	{
		if(!map.containsKey(obj))
		{
			return "-1";
		}
		return map.get(obj);
	}
	
	protected void setList(Object obj)
	{
		list.add(obj);
	}
	
	protected Object getList()
	{
		return list.poll();
	}
	
	public Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		setList(obj);
		if(setMap(obj) == -1) return document;
		while(!list.isEmpty())
		{
			root.addContent(serializeObject(getList()));
		}
		return document;
	}
	
	protected Element serializeObject(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		Class c = obj.getClass();
		
		Element e = new Element("object");
		e.setAttribute(new Attribute("class", c.getName()));
		e.setAttribute(new Attribute("id", getMap(obj)));
		
		if(c.isArray())
		{
			e.setAttribute(new Attribute("length", Integer.toString(Array.getLength(obj))));
			for(int i = 1; i < Array.getLength(obj); i++)
			{
				e.addContent(serializeArray(i, obj));
			}
		}
		else
		{
			Field[] field = c.getDeclaredFields();
			for(int i = 0; i < field.length; i++)
			{
				e.addContent(serializeField(field[i], obj));
			}
		}
		return e;
	}
	
	protected Element serializeField(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		field.setAccessible(true);
		
		Element f = new Element("field");
		f.setAttribute(new Attribute("name", field.getName()));
		f.setAttribute(new Attribute("declaringclass", field.getType().getName()));
		
		if(field.getType().isPrimitive())
		{
			Element value = new Element("value");
			value.addContent(field.get(obj).toString());
			f.setContent(value);
		}
		else
		{
			Object reference = field.get(obj);
			if(!(setMap(reference) == -1)) setList(reference);
			Element value = new Element("reference");
			value.addContent(getMap(reference));
			f.setContent(value);
		}
		
		return f;
	}
	
	private Element serializeArray(int index, Object obj)
	{
		Object arrayValue = Array.get(obj, index);
		if(arrayValue.getClass().isPrimitive() || arrayValue.getClass().getName().contains("java.lang"))
		{
			Element a = new Element("value");
			a.addContent(arrayValue.toString());
			return a;
		}
		else
		{
			Element a = new Element("reference");
			if(!(setMap(arrayValue) == -1)) setList(arrayValue);
			a.addContent(getMap(arrayValue));
			return a;
		}
	}
}
