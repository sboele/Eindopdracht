package helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.Logger;

public class Discretization {
	
	Data data;
	
	public Discretization() {
		data = new Data();
	}
	
	public List<Double> getRules(int attributeIndex) {
		return determineRules(partitioning(attributeIndex), attributeIndex);
	}
	
	/*
	 * Step 1: Loop through values until you've found 1 value 3 times
	 * Step 2: Continue looping until you've found a different value, that's the end of the group. Go back to step 1
	 * 
	 * If you've come to the last item in the list, put all the items who are not in a group yet in the last group.
	 */
	private List<List<Integer>> partitioning(int attributeIndex) {
		List<String> merkbaarOrGering = data.getMerkbaarOrGeringBySortedAttribute(attributeIndex);
		List<List<Integer>> groups = new ArrayList<List<Integer>>();
		int timesMerkbaar = 0;
		int timesGering = 0;
		int beginIndexGroup = 0;
		String groupValue = new String();
		
		for (int i = 0; i < merkbaarOrGering.size(); i++) {
			if (groupValue.isEmpty()) {
				if (merkbaarOrGering.get(i).contains("merkbaar"))
					timesMerkbaar++;
				else
					timesGering++;
				
				if (timesMerkbaar == 3 || timesGering == 3)
					groupValue = (timesMerkbaar == 3 ? "merkbaar" : "gering");
			}
			else {
				if (!merkbaarOrGering.get(i).contains(groupValue) || i == merkbaarOrGering.size()-1) {
					addGroup(groups, beginIndexGroup, (merkbaarOrGering.get(i).contains(groupValue) && i == merkbaarOrGering.size()-1) ? i : i-1);
					
					beginIndexGroup = i;
					timesMerkbaar = (merkbaarOrGering.get(i).contains("merkbaar") ? 1 : 0);
					timesGering = (merkbaarOrGering.get(i).contains("gering") ? 1 : 0);
					groupValue = new String();
				}	
			}
			
			if (i == merkbaarOrGering.size()-1)
				addGroup(groups, beginIndexGroup, (merkbaarOrGering.get(i).contains(groupValue) && i == merkbaarOrGering.size()-1) ? i : i-1);
		}
		return groups;
	}
	
	private void addGroup(List<List<Integer>> groups, int beginIndex, int endIndex) {
		List<Integer> indicesOfBorder = new ArrayList<Integer>();
		indicesOfBorder.add(beginIndex);
		indicesOfBorder.add(endIndex);
		groups.add(indicesOfBorder);
	}
	
	private List<Double> determineRules(List<List<Integer>> groups, int attributeIndex) {
		List<Double> numericValues = data.getNumericValuesForAttributeInSortedNumericData(attributeIndex);
		List<Double> rules = new ArrayList<Double>();
		Logger.info("Groups: " + groups);
		Logger.info("Numeric Values: " + numericValues);
		for (int i = 0; i < groups.size()-1; i++) {
			rules.add(calculateRule(numericValues, groups.get(i).get(1), groups.get(i+1).get(0), attributeIndex));
		}
		Logger.info("Rules: " + rules);
		return rules;
	}
	
	/*
	 * 
	 * If borders are even
	 *  Check if the times the value appears is even
	 *  If even add value to the previous group
	 *  If odd check if the values the most Merkbaar or Gering, then add to that group (previous or next)
	 * Else
	 *  Get mean of the borders
	 * 
	 */
	private Double calculateRule(List<Double> numericValues, int indexFirstBorder, int indexSecondBorder, int attributeIndex) {
		double rule = 0.0;
		double firstBorder = numericValues.get(indexFirstBorder);
		double secondBorder = numericValues.get(indexSecondBorder);
		Logger.info("fb: %s sb: %s", firstBorder, secondBorder);
		if (firstBorder == secondBorder) {
			int timesValueAppears = data.getGroupedValuesForAttributeInNumericData(attributeIndex, false).get(String.valueOf(firstBorder));
			if (timesValueAppears % 2 == 0) {
				return calculateRule(numericValues, indexFirstBorder + timesValueAppears - 1, indexSecondBorder + timesValueAppears - 1, attributeIndex);
			}
			else {
				Logger.info("oneven");
			}
		}
		else {
			rule = (firstBorder + secondBorder) / 2.0;
		}
		return rule;
	}

}
