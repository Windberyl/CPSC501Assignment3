public class ClassD {
	ClassC[] classC = new ClassC[10];
	
	public ClassD()
	{
		for(int i = 0; i < 10; i++)
		{
			classC[i] = new ClassC();
		}
	}
}
