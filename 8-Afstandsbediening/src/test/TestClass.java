package test;

import org.fluttercode.datafactory.impl.DataFactory;

public class TestClass {

	public static void main(String[] args){
		DataFactory f = new DataFactory();
		
		String name = f.getFirstName();
		
		System.out.println(name);
		
		

	}

}
