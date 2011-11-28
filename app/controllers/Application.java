package controllers;

import play.*;
import play.mvc.*;

import helpers.*;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

	public static void index() {
		List<List<String>> numericTable = new Data().getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Data().getOrdinalBodyLotionData();
		
		List<List<String>> oneRModelTable = new OneRModel().getOneRModel();
		String oneRModelBestRule = new OneRModel().getAttributeWithLeastErrors();
		
		List<List<String>> bayesTableNominal = new Bayes().getNaiveBayesNominal();
		List<List<String>> bayesTableNumeric = new Bayes().getNaiveBayesNumeric();
		
		List<String> valuesForLikeliHood = new Data().getValuesForLikeliHood();
		double likeliHoodForMerkbaar = new LikeliHood().getLikeliHoodForMerkbaar(valuesForLikeliHood);
		double likeliHoodForGering = new LikeliHood().getLikeliHoodForGering(valuesForLikeliHood);
		double probabilityForMerkbaar = new LikeliHood().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, true);
		double probabilityForGering = new LikeliHood().getProbability(likeliHoodForMerkbaar, likeliHoodForGering, false);
		render(numericTable, ordinalTable, oneRModelTable, oneRModelBestRule, bayesTableNominal, bayesTableNumeric, 
				valuesForLikeliHood, likeliHoodForMerkbaar, likeliHoodForGering, probabilityForMerkbaar, probabilityForGering);}
}