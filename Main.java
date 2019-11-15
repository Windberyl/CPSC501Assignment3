import java.lang.reflect.Field;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
public class Main {
	public static void main(String args[]) throws Exception
	{
		ClassB classB = new ClassB();
		ClassA classA = new ClassA(classB);
		classB.setClassA(classA);
		
		ClassD classD = new ClassD();
		ClassC classC = new ClassC();
		
		Serializer serial = new Serializer();
		Deserializer deserial = new Deserializer();
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		Document document = serial.serialize(classD);
		String string = out.outputString(document);
		System.out.println(string);
		
		Object object = deserial.deserialize(document);
	}
}
