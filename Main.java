import java.util.Scanner;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
public class Main {
	static Scanner scanner = new Scanner(System.in);
	
	static Serializer serial = new Serializer();
	static Deserializer deserial = new Deserializer();
	public static void main(String args[]) throws Exception
	{
		ClassB classB = new ClassB();
		ClassA classA = new ClassA(classB);
		classB.setClassA(classA);
		
		ClassD classD = new ClassD();
		ClassC classC = new ClassC();
		
		int choice = -1;
		System.out.println("Pick an object to create");
		System.out.println("classA classB classC classD");
		while(true)
		{
			String line = scanner.nextLine();
			if(line.equals("classA"))
			{
				choice = 0;
				break;
			}
			else if(line.equals("classB"))
			{
				choice = 1;
				break;
			}
			if(line.equals("classC"))
			{
				choice = 2;
				break;
			}
			if(line.equals("classD"))
			{
				choice = 3;
				break;
			}
			else System.out.println("Wrong Choice, try again");
		}
		Document document = new Document();
		if(choice == 0)
		{
			document = serial.serialize(classA);
		}
		else if(choice == 1)
		{
			document = serial.serialize(classB);
		}
		else if(choice == 2)
		{
			document = serial.serialize(classC);
		}
		else if(choice == 3)
		{
			document = serial.serialize(classD);
		}
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		String string = out.outputString(document);
		System.out.print(string);
		
		Object object = deserial.deserialize(document);
		
		if(choice == 0)
		{
			ClassA a = (ClassA) object;
			System.out.print("int[] array is: ");
			for(int i = 0; i < a.array.length; i++)
			{
				System.out.printf("%d ", a.array[i]);
			}
			System.out.println();
			System.out.print("int wow is: ");
			System.out.println(a.wow);
			System.out.print("ClassB classB hashcode is: ");
			System.out.println(a.classB.hashCode());
		}
		else if(choice == 1)
		{
			ClassB b = (ClassB) object;
			System.out.print("int wow is: ");
			System.out.println(b.getWow());
			System.out.print("ClassA classA hashcode is: ");
			System.out.println(b.classA.hashCode());
		}
		else if(choice == 2)
		{
			ClassC c = (ClassC) object;
			System.out.print("int wow is: ");
			System.out.println(c.getWow());
		}
		else if(choice == 3)
		{
			ClassD d = (ClassD) object;
			System.out.println("ClassC array:");
			for(int i = 0; i < d.classC.length; i++)
			{
				System.out.println("Object hashcode at index: " + Integer.toString(i));
				System.out.println("\t" + Integer.toString(d.classC[i].hashCode()));
			}
		}
	}
}
