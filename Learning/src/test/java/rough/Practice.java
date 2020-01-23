package rough;

import org.testng.annotations.Test;

public class Practice {

	@Test(groups = { "Smoke", "Sanity" })
	public void method1() {

		System.out.println("Method1");
	}

	@Test(groups = { "Smoke" }, enabled = true)
	public void method2() {

		System.out.println("Method2");
	}

	@Test(groups = { "Sanity" })
	public void method3() {

		System.out.println("Method3");
	}

	@Test(groups = { "Smoke", "Sanity" })
	public void method4() {

		System.out.println("Method4");
	}
	
	public static void main(String[] args) {
		
		Practice2 obj= new Practice2();
		
		
	}

}
