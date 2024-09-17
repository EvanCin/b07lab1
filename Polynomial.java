public class Polynomial {
	double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
	}
	public Polynomial(double[] coeff) {
		coefficients = new double[coeff.length];
		for(int i = 0; i < coeff.length; i++) {
			coefficients[i] = coeff[i];
		}
	}
	public Polynomial add(Polynomial p) {
		double[] sum;
		int len;
		if(coefficients.length >= p.coefficients.length) {
			len = p.coefficients.length;
			sum = new double[coefficients.length];
			
			for(int i = len; i < coefficients.length; i++) {
				sum[i] = coefficients[i];
			}
		} else {
			len = coefficients.length;
			sum = new double[p.coefficients.length];
			
			for(int i = len; i < p.coefficients.length; i++) {
				sum[i] = p.coefficients[i];
			}
		}
		for(int i = 0; i < len; i++) {
			sum[i] = coefficients[i] + p.coefficients[i];
		}
		
		Polynomial sum_p = new Polynomial(sum);
		return sum_p;
	}
	public double evaluate(double x) {
		double polynomial_result = 0;
		for(int i = 0; i < coefficients.length; i++) {
			polynomial_result += coefficients[i]*Math.pow(x, i);
		}
		return polynomial_result;
	}
	public boolean hasRoot(double root_val) {
		return evaluate(root_val) == 0;
	}
}