import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
	double[] nonzero_coeff;
	int[] exponents;
	
	public Polynomial() {
		nonzero_coeff = null;
		exponents = null;
	}
	public Polynomial(double[] nonzero_coeff, int[] exponents) {
		this.nonzero_coeff = new double[nonzero_coeff.length];
		this.exponents = new int[exponents.length];
		for(int i = 0; i < nonzero_coeff.length; i++) {
			this.nonzero_coeff[i] = nonzero_coeff[i];
			this.exponents[i] = exponents[i];
		}
	}
	public Polynomial(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s = br.readLine();
		br.close();
		if(s.equals("0") || s.isBlank()) {
			nonzero_coeff = null;
			exponents = null;
			return;
		}
		//find len of arr
		int len_arr = 1;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '+' || s.charAt(i) == '-') {
				len_arr++;
			}
		}
		String[] substr = new String[len_arr];
		int substr_index = 0;
		int last_index = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '+') {
				substr[substr_index] = s.substring(last_index, i);
				substr_index++;
				last_index = i+1;
			}else if(s.charAt(i) == '-') {
				substr[substr_index] = s.substring(last_index, i);
				substr_index++;
				last_index = i;
			}
		}
		substr[substr_index] = s.substring(last_index, s.length());
		nonzero_coeff = new double[len_arr];
		exponents = new int[len_arr];
		for(int i = 0; i < len_arr; i++) {
			if(!substr[i].contains("x")) {
				nonzero_coeff[i] = Double.parseDouble(substr[i]);
				exponents[i] = 0;
			} else {
				nonzero_coeff[i] = Double.parseDouble(substr[i].substring(0, substr[i].indexOf("x")));
				exponents[i] = Integer.parseInt(substr[i].substring(substr[i].indexOf("x")+1));
			}
		}
	}
	public Polynomial add(Polynomial p) {
		if(nonzero_coeff == null) {return p;}
		if(p.nonzero_coeff == null) {
			if(nonzero_coeff == null) {return new Polynomial();}
			return new Polynomial(nonzero_coeff, exponents);
		}
		
		int num_same_exp = 0;
		//find overlapping exponents
		for(int i = 0; i < exponents.length; i++) {
			for(int j = 0; j < p.exponents.length; j++) {
				if(exponents[i] == p.exponents[j]) {
					num_same_exp++;
				}
			}
		}
		int len_arr = exponents.length+p.exponents.length-num_same_exp;
		double[] new_nonzero_coeff = new double[len_arr];
		int[] new_exponents = new int[len_arr];
		//fill the new arrays
		for(int i = 0; i < exponents.length; i++) {
			new_exponents[i] = exponents[i];
			new_nonzero_coeff[i] = nonzero_coeff[i];
		}
		for(int i = exponents.length; i < len_arr; i++) {
			new_exponents[i] = -1;
		}
		
		for(int i = 0; i < p.exponents.length; i++) {
			for(int j = 0; j < len_arr; j++) {
				if(p.exponents[i] == new_exponents[j]) {
					new_nonzero_coeff[j] += p.nonzero_coeff[i];
					break;
				} else if(new_exponents[j] == -1){
					new_exponents[j] = p.exponents[i];
					new_nonzero_coeff[j] = p.nonzero_coeff[i];
					break;
				}
			}
		}
		return new Polynomial(new_nonzero_coeff, new_exponents);
	}
	public double evaluate(double x) {
		double polynomial_result = 0;
		for(int i = 0; i < exponents.length; i++) {
			polynomial_result += nonzero_coeff[i]*Math.pow(x, exponents[i]);
		}
		return polynomial_result;
	}
	public boolean hasRoot(double root_val) {
		return evaluate(root_val) == 0;
	}
	
	public Polynomial multiply(Polynomial p) {
		double[] new_nonzero_coeff = new double[2*(exponents.length+p.exponents.length)];
		int[] new_exponents = new int[2*(exponents.length+p.exponents.length)];
		for(int i = 0; i < new_exponents.length; i++) {
			new_exponents[i] = -1;
		}
		//Multiply the two polynomial
		double coeff_val = 0;
		int exp_val = 0;
		for(int i = 0; i < exponents.length; i++) {
			for(int j = 0; j < p.exponents.length; j++) {
				coeff_val = nonzero_coeff[i]*p.nonzero_coeff[j];
				exp_val = exponents[i]+p.exponents[j];
				for(int k = 0; k < new_exponents.length; k++) {
					if(exp_val == new_exponents[k]) {
						new_nonzero_coeff[k] += coeff_val;
						break;
					}
					if(new_exponents[k] == -1) {
						new_exponents[k] = exp_val;
						new_nonzero_coeff[k] = coeff_val;
						break;
					}
				}
			}
		}
		//Find end of the new arrays
		int len_arr = new_exponents.length;
		for(int i = 0; i < new_exponents.length; i++) {
			if(new_exponents[i] == -1) {
				len_arr = i;
				break;
			}
		}
		double[] final_coeff = new double[len_arr];
		int[] final_exponents = new int[len_arr];
		for(int i = 0; i < len_arr; i++) {
			final_coeff[i] = new_nonzero_coeff[i];
			final_exponents[i] = new_exponents[i];
		}
		return new Polynomial(final_coeff, final_exponents);
	}
	
	public void saveToFile(String file_name) throws IOException {
		FileWriter fw = new FileWriter(file_name, false);
		String result = "";
		for(int i = 0; i < exponents.length-1; i++) {
			if(exponents[i] == 0) {
				result += nonzero_coeff[i];
			} else {
				result = result + nonzero_coeff[i] + "x" + exponents[i];
			}
			if(nonzero_coeff[i+1] > 0) {
				result += "+";
			}
		}
		if(exponents[exponents.length-1] == 0) {
			result += nonzero_coeff[exponents.length-1];
		} else {
			result = result + nonzero_coeff[exponents.length-1] + "x" + exponents[exponents.length-1];
		}
		fw.write(result);
		fw.close();
	}
	
}