public class ClassA {
	public int[] array = {2,3,4,5};
	public int wow = 2;
	public ClassB classB;
	
	public ClassA(){}
	public ClassA(ClassB classB)
	{
		this.classB = classB;
	}
}
