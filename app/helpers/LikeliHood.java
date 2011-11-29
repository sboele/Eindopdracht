package helpers;

import java.util.ArrayList;
import java.util.List;

import play.Logger;

public class LikeliHood {
	
	Data data;
	
	public LikeliHood() {
		data = new Data();
	}
	
	public double getLikeliHoodForMerkbaar(List<String> values) {
		List<Double> numbers = new ArrayList<Double>();
		double totalMerkbaar = data.getGroupedValuesForAttributeInOrdinalData(data.getNumberOfAttributes()-1, false).get("merkbaar");
		for (String value : values)
			numbers.add(data.getNumberOfMerkbaar(value)/totalMerkbaar);
		return calculateLikeliHood(numbers);
	}
	
	public double getLikeliHoodForGering(List<String> values) {
		List<Double> numbers = new ArrayList<Double>();
		double totalGering = data.getGroupedValuesForAttributeInOrdinalData(data.getNumberOfAttributes()-1, false).get("gering");
		for (String value : values)
			numbers.add(data.getNumberOfGering(value)/totalGering);
		return calculateLikeliHood(numbers);
	}
	
	private double calculateLikeliHood(List<Double> numbers) {
		double likelihood = 1.0;
		for(double number : numbers)
			likelihood = likelihood * number;
		return likelihood;
	}

}
