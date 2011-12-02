package controllers;

import play.mvc.*;

import helpers.*;

import java.util.List;
import java.util.Map;

public class Application extends Controller {

	public static void index() {
		//Data tables
		List<List<String>> numericTable = new Data().getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Data().getOrdinalBodyLotionData();
		
		//1R Model
		List<List<String>> oneRModelTable = new OneRModel().getOneRModel();
		String oneRModelBestRule = new OneRModel().getAttributeWithLeastErrors();
		
		//Bayes Table
		List<List<String>> bayesTableNominal = new Bayes().getNaiveBayesNominal();
		List<List<String>> bayesTableNumeric = new Bayes().getNaiveBayesNumeric();
		
		//LikeliHood and Probability
		List<String> valuesForLikeliHood = new Data().getValuesForLikeliHood();
		double likeliHoodForMerkbaar = new LikeliHood().getLikeliHoodForMerkbaar(valuesForLikeliHood);
		double likeliHoodForGering = new LikeliHood().getLikeliHoodForGering(valuesForLikeliHood);
		double probabilityForMerkbaar = new Statistics().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, true);
		double probabilityForGering = new Statistics().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, false);
		
                // Statistical Info
                
                Map<String, List<String>> statisticInfo = new StatisticInfo().getStatisticInfo();
                
		render(numericTable, ordinalTable, oneRModelTable, oneRModelBestRule, bayesTableNominal, bayesTableNumeric, 
				valuesForLikeliHood, likeliHoodForMerkbaar, likeliHoodForGering, probabilityForMerkbaar,
                                probabilityForGering, statisticInfo);}
}