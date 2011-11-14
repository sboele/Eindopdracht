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
public class Helper {

	String[][] bodyLotionData = new String[][] {
			{ "Sarah", "blond", "1,63", "49", "nee", "merkbaar" },
			{ "Alex", "blond", "1,85", "70", "ja", "gering" },
			{ "Diana", "bruin", "1,50", "51", "ja", "gering" },
			{ "Anne", "blond", "1,55", "54", "nee", "merkbaar" },
			{ "Emily", "rood", "1,67", "80", "nee", "merkbaar" },
			{ "Peter", "bruin", "1,81", "90", "nee", "gering" },
			{ "Jan", "bruin", "1,61", "76", "nee", "gering" },
			{ "Katie", "bruin", "1,53", "43", "ja", "gering" } };

	public List<List<String>> getBodyLotionData() {
		List<List<String>> rows = new ArrayList<List<String>>();

		for (int i = 0; i < bodyLotionData.length; i++) {
			List<String> temp = new ArrayList<String>();
			for (int j = 0; j < bodyLotionData[i].length; j++) {
				temp.add(bodyLotionData[i][j]);
			}
			rows.add(temp);
		}
		
		return rows;
	}
}
