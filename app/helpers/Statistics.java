package helpers;

import java.util.List;

public class Statistics {
	
	public double getProbabilityDensity(double x, double mean, double standardDeviation) {
        double macht = -1 * (Math.pow(x-mean, 2) / (2 * Math.pow(standardDeviation, 2)));
        double fx = 1.0 / (Math.sqrt(2 * Math.PI) * standardDeviation) * Math.pow(Math.E,macht);
        return fx;
	}
	
	public double getMean(List<Double> numbers) {
		double total = 0.0;
		for (double number : numbers)
			total += number;
		return total/numbers.size();
	}
	
	public double getStandardDeviation(List<Double> numbers) {
		double mean = getMean(numbers);
		double numerator = 0.0;
		double denominator = numbers.size() - 1;
		for (double number: numbers)
			numerator += Math.pow(number - mean, 2);
		return Math.sqrt(numerator/denominator);
	}
	
	public double getProbability(double likeliHoodMerkbaar, double likeliHoodGering, boolean forMerkbaar) {
		double probability = 0.0;
		if (forMerkbaar)
			probability = likeliHoodMerkbaar / (likeliHoodMerkbaar + likeliHoodGering);
		else
			probability = likeliHoodGering / (likeliHoodMerkbaar + likeliHoodGering);
		return probability*100;
	}

}
