package helpers;

public class Statistics {
	
	public double probabilityDensity(double x, double mean, double standardDeviation) {
        double macht = -1 * (Math.pow(x-mean, 2) / (2 * Math.pow(standardDeviation, 2)));
        double fx = 1.0 / (Math.sqrt(2 * Math.PI) * standardDeviation) * Math.pow(Math.E,macht);
        return fx;
	}
	
	public double mean() {
		return 0.0;
	}
	
	public double standardDeviation() {
		return 0.0;
	}

}
