package controllers;

import play.*;
import play.mvc.*;

import helpers.*;
import java.util.List;

public class Application extends Controller {

	public static void index() {
		List<List<String>> numericTable = new Helper().getNumericBodyLotionData();
		List<List<String>> ordinalTable = new Helper().getOrdinalBodyLotionData();
		render(numericTable, ordinalTable);
	}
}