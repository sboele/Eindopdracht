package controllers;

import play.*;
import play.mvc.*;

import helpers.*;
import java.util.List;

public class Application extends Controller {

	public static void index() {
		List<List<String>> numericTable = new Data().getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Data().getOrdinalBodyLotionData();
		List<List<String>> oneRModelTable = new OneRModel().getOneRModel(ordinalTable);
		List<List<String>> bayesTable = new Bayes().getBayesData(ordinalTable);
		render(numericTable, ordinalTable, oneRModelTable, bayesTable);
	}
}