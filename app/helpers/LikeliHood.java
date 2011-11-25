package helpers;

import java.util.List;

public class LikeliHood {
	
	public double getLikeliHood(List<String> attributes, boolean forYes) {
		return 0.0;
	}
	
	public double getProbability(double likeliHoodYes, double likeliHoodNo, boolean forYes) {
		double probability = 0.0;
		if (forYes)
			probability = likeliHoodYes / (likeliHoodYes + likeliHoodNo);
		else
			probability = likeliHoodNo / (likeliHoodYes + likeliHoodNo);
		return probability;
	}

}
