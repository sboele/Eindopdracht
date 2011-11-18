/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sander
 */
public class Data {

	String[][] bodyLotionNumericData = new String[][] {
			{ "Sarah", "blond", "1,63", "49", "nee", "merkbaar" },
			{ "Alex", "blond", "1,85", "70", "ja", "gering" },
			{ "Diana", "bruin", "1,50", "51", "ja", "gering" },
			{ "Anne", "blond", "1,55", "54", "nee", "merkbaar" },
			{ "Emily", "rood", "1,67", "80", "nee", "merkbaar" },
			{ "Peter", "bruin", "1,81", "90", "nee", "gering" },
			{ "Jan", "bruin", "1,61", "76", "nee", "gering" },
			{ "Katie", "bruin", "1,53", "43", "ja", "gering" } };

	/*
	 * 
	 * LENGTE
	 * Klein: 	<= 1,60
	 * Middel: 	> 1,60 && < 1,85
	 * Groot:	>= 1,85
	 * 
	 * GEWICHT
	 * Licht: 	<= 55
	 * Normaal: > 55 && < 80
	 * Zwaar: 	>= 80
	 * 
	 */
	String[][] bodyLotionOrdinalData = new String[][] {
			{ "Sarah", "blond", "middel", "licht", "nee", "merkbaar" },
			{ "Alex", "blond", "groot", "normaal", "ja", "gering" },
			{ "Diana", "bruin", "klein", "licht", "ja", "gering" },
			{ "Anne", "blond", "klein", "licht", "nee", "merkbaar" },
			{ "Emily", "rood", "middel", "zwaar", "nee", "merkbaar" },
			{ "Peter", "bruin", "middel", "zwaar", "nee", "gering" },
			{ "Jan", "bruin", "middel", "normaal", "nee", "gering" },
			{ "Katie", "bruin", "klein", "licht", "ja", "gering" } };

	public List<List<String>> getNumericBodyLotionData() {
		List<List<String>> rows = new ArrayList<List<String>>();

		for (int i = 0; i < bodyLotionNumericData.length; i++) {
			List<String> temp = new ArrayList<String>();
			for (int j = 0; j < bodyLotionNumericData[i].length; j++) {
				temp.add(bodyLotionNumericData[i][j]);
			}
			rows.add(temp);
		}

		return rows;
	}

	public List<List<String>> getOrdinalBodyLotionData() {
		List<List<String>> rows = new ArrayList<List<String>>();

		for (int i = 0; i < bodyLotionOrdinalData.length; i++) {
			List<String> temp = new ArrayList<String>();
			for (int j = 0; j < bodyLotionOrdinalData[i].length; j++) {
				temp.add(bodyLotionOrdinalData[i][j]);
			}
			rows.add(temp);
		}

		return rows;
	}
}
