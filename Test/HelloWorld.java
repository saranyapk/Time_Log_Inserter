import java.io.Serializable;


public abstract class HelloWorld implements Serializable
{
    int a;
    
    public static void main(String args[])
    {
        print();
    }
    
    public static void print()
    {
		System.out.println("Hello World!!!");
    }
}