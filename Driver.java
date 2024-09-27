import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws IOException {
	
	double[] c1 = {6, -2, 5};
	int[] ce1 = {0,1,3};
	Polynomial p1 = new Polynomial(c1, ce1);
	double[] c2 = {4,3,2,7};
	int[] ce2 = {0,4,2,3};
	Polynomial p2 = new Polynomial(c2, ce2);
	Polynomial p3 = p1.add(p2);
	System.out.println(p2.evaluate(2));
	
	for(int i = 0; i < p3.exponents.length; i++) {
		System.out.print(p3.exponents[i] + " ");
	}
	System.out.println();
	for(int i = 0; i < p3.exponents.length; i++) {
		System.out.print(p3.nonzero_coeff[i] + " ");
	}
	
	double[] c4 = {6, 1, 5};
	int[] ce4 = {0, 1, 3};
	Polynomial p4 = new Polynomial(c4, ce4);
	if(p4.hasRoot(-1)) {
		System.out.println();
		System.out.println("-1 is a root");
	}
	
	double[] c5 = {3,4,3};
	int[] ce5 = {2,1,0};
	Polynomial p5 = new Polynomial(c5, ce5);
	double[] c6 = {4,3,8};
	int[] ce6 = {5,7,1};
	Polynomial p6 = new Polynomial(c6, ce6);
	Polynomial p7 = p5.multiply(p6);
	for(int i = 0; i < p7.exponents.length; i++) {
		System.out.print(p7.exponents[i] + " ");
	}
	System.out.println();
	for(int i = 0; i < p7.exponents.length; i++) {
		System.out.print(p7.nonzero_coeff[i] + " ");
	}
	System.out.println();
	File f = new File("/Users/evanc/repos/cscb07/labs/b07lab1/test.txt");
	Polynomial p8 = new Polynomial(f);
	for(int i = 0; i < p8.exponents.length; i++) {
		System.out.print(p8.nonzero_coeff[i] + " ");
	}
	System.out.println();
	for(int i = 0; i < p8.exponents.length; i++) {
		System.out.print(p8.exponents[i] + " ");
	}
	p8.saveToFile("/Users/evanc/repos/cscb07/labs/b07lab1/abc.txt");
	}	
}
