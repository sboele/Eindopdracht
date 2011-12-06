package controllers;

import play.Logger;
import play.mvc.*;

import helpers.*;

import java.util.List;
import java.util.Map;

public class Application extends Controller {

	public static void index() {
		//Data tables
		List<List<String>> numericTable = new Data(false).getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Data(false).getOrdinalBodyLotionData();
		
		//1R Model
		List<List<String>> oneRModelTable = new OneRModel(false).getOneRModel();
		String oneRModelBestRule = new OneRModel(false).getAttributeWithLeastErrors();
		
		//Bayes Table
		List<List<String>> bayesTableNominal = new Bayes(false).getNaiveBayesNominal();
		List<List<String>> bayesTableNumeric = new Bayes(false).getNaiveBayesNumeric();
		
		//LikeliHood and Probability
		List<String> valuesForLikeliHood = new Data(false).getValuesForLikeliHood();
		double likeliHoodForMerkbaar = new LikeliHood(false).getLikeliHoodForMerkbaar(valuesForLikeliHood);
		double likeliHoodForGering = new LikeliHood(false).getLikeliHoodForGering(valuesForLikeliHood);
		double probabilityForMerkbaar = new Statistics().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, true);
		double probabilityForGering = new Statistics().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, false);
		
		//Statistical Info        
		Map<String, List<String>> statisticInfo = new StatisticInfo(false).getStatisticInfo();

		//Decision Tree        
		Map<String, Double> infoAndGain = new DecisionTree(false).getInfoAndGain();
		String highestGain = new DecisionTree(false).getHighestGain();
		
		
		//Discretization
		List<String> rulesForLengte = new Discretization().getRulesInHtml("Lengte");
		List<String> rulesForGewicht = new Discretization().getRulesInHtml("Gewicht");
		
		//Data table after Discretization
		List<List<String>> ordinalTableAfterDiscretization = new Data(true).getOrdinalBodyLotionData();
		
		//1R Model after Discretization
		List<List<String>> oneRModelTableAfterDiscretization = new OneRModel(true).getOneRModel();
		String oneRModelBestRuleAfterDiscretization = new OneRModel(true).getAttributeWithLeastErrors();
		
		//Bayes Table after Discretization
		List<List<String>> bayesTableNominalAfterDiscretization = new Bayes(true).getNaiveBayesNominal();
		List<List<String>> bayesTableNumericAfterDiscretization = new Bayes(true).getNaiveBayesNumeric();

		//Decision Tree        
		Map<String, Double> infoAndGainAfterDiscretization = new DecisionTree(true).getInfoAndGain();
		String highestGainAfterDiscretization = new DecisionTree(true).getHighestGain();
                
		render(numericTable, ordinalTable, oneRModelTable, oneRModelBestRule, bayesTableNominal, bayesTableNumeric, 
				valuesForLikeliHood, likeliHoodForMerkbaar, likeliHoodForGering, probabilityForMerkbaar,
				probabilityForGering, statisticInfo,infoAndGain, highestGain, rulesForLengte, rulesForGewicht, 
				ordinalTableAfterDiscretization, oneRModelTableAfterDiscretization, oneRModelBestRuleAfterDiscretization,
				bayesTableNominalAfterDiscretization, bayesTableNumericAfterDiscretization, infoAndGainAfterDiscretization,highestGainAfterDiscretization);
		}
}