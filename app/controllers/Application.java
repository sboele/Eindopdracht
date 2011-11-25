package controllers;

import play.*;
import play.mvc.*;

import helpers.*;
import java.util.List;

public class Application extends Controller {

	public static void index() {
		List<List<String>> numericTable = new Data().getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Data().getOrdinalBodyLotionData();
		List<List<String>> oneRModelTable = new OneRModel().getOneRModel();
		String oneRModelBestRule = new OneRModel().getAttributeWithLeastErrors();
		List<List<String>> bayesTableNominal = new Bayes().getNaiveBayesNominal();
                List<List<String>> bayesTableNumeric = new Bayes().getNaiveBayesNumeric();
		render(numericTable, ordinalTable, oneRModelTable, oneRModelBestRule, bayesTableNominal, bayesTableNumeric);
	}
}