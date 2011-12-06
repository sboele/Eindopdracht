package helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.Logger;

public class Discretization {
	
	Data data;
	
	public Discretization() {
		data = new Data(false);
	}
	
	public List<Double> getRules(int attributeIndex) {
		return determineRules(partitioning(attributeIndex), attributeIndex);	
	}
	
	public List<String> getRulesInHtml(String attribute) {
		List<String> rulesInHtml = new ArrayList<String>();
		List<Double> rules = getRules(new Data(false).getIndexOfAttribute(attribute));		
		for(int i = 0; i < rules.size()-1; i++) {
			String rule = attribute + i + " -> rule: " + rules.get(i) + " - " + rules.get(i+1);
			rulesInHtml.add(rule);
		}
		return rulesInHtml;
	}
	
	/*
	 * Step 1: Loop through values until you've found 1 value 3 times
	 * Step 2: Continue looping until you've found a different value, that's the end of the group. Go back to step 1
	 * 
	 * If you've come to the last item in the list, put all the items who are not in a group yet in the last group.
	 * 
	 * The partitions returned are not always the 'real' partitions. Because when the rules are determined it checks if
	 * the partition borders have the same value (f.e. 1.62), if that is the case the borders of partitions are moved.
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
				if (!merkbaarOrGering.get(i).contains(groupValue)) {
					addGroup(groups, beginIndexGroup, (merkbaarOrGering.get(i).contains(groupValue) && i == merkbaarOrGering.size()-1) ? i : i-1);
					
					beginIndexGroup = i;
					timesMerkbaar = (merkbaarOrGering.get(i).contains("merkbaar") ? 1 : 0);
					timesGering = (merkbaarOrGering.get(i).contains("gering") ? 1 : 0);
					groupValue = new String();
				}	
			}
			
			if (i == merkbaarOrGering.size()-1) {
				if (merkbaarOrGering.get(i).contains(groupValue)) {
					addGroup(groups, beginIndexGroup, i);				
				}
				else {
					addGroup(groups, beginIndexGroup, i-1);
					addGroup(groups, i, i);
				}
			}
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

		rules.add(numericValues.get(0));
		
		for (int i = 0; i < groups.size()-1; i++)
			rules.add(calculateRule(numericValues, groups.get(i).get(1), groups.get(i+1).get(0), attributeIndex));
		
		rules.add(numericValues.get(numericValues.size()-1));
		return rules;
	}
	
	private Double calculateRule(List<Double> numericValues, int indexFirstBorder, int indexSecondBorder, int attributeIndex) {
		double firstBorder = numericValues.get(indexFirstBorder);
		double secondBorder = numericValues.get(indexSecondBorder);
		if (firstBorder == secondBorder)
			return calculateRuleForEvenBorders(numericValues, indexFirstBorder, indexSecondBorder, attributeIndex);
		else
			return (firstBorder + secondBorder) / 2.0;
	}
	
	/*
	 * If borders are even
	 *  Check if the times the value appears is even
	 *  If even, add value to the previous group
	 *  If odd, check if the values have the most Merkbaar or Gering, then add to that group (previous or next)
	 * Else
	 *  Get mean of the borders
	 *  
	 * indexFirstBorder = last index of group on the left
	 * indexSecondBorder = first index of group on the right
	 */
	private Double calculateRuleForEvenBorders(List<Double> numericValues, int indexFirstBorder, int indexSecondBorder, int attributeIndex) {
		double firstBorder = numericValues.get(indexFirstBorder);
		int timesValueAppears = data.getGroupedValuesForAttributeInNumericData(attributeIndex, false).get(String.valueOf(firstBorder));
		if (timesValueAppears % 2 == 0) {
			//Add to previous group, so the border is one position further (or more if there are 4 of the same values, then it has to move 3 positions)
			return calculateRule(numericValues, indexFirstBorder + stepsToRight(indexFirstBorder, numericValues), indexSecondBorder + stepsToRight(indexFirstBorder, numericValues), attributeIndex);
		}
		else {
			List<Integer> timesOccuring = data.getValuesAndTimesOccuringInNumericData(attributeIndex).get(String.valueOf(firstBorder));
			//Gering
			if (timesOccuring.get(0) > timesOccuring.get(1)) {
				//Group on the left
				if (isGroupGeringOnLeftSide(indexFirstBorder, attributeIndex))
					return calculateRule(numericValues, indexFirstBorder + stepsToRight(indexFirstBorder, numericValues), indexSecondBorder + stepsToRight(indexFirstBorder, numericValues), attributeIndex);
				//Group on the right
				else
					return calculateRule(numericValues, indexFirstBorder - stepsToLeft(indexFirstBorder, numericValues), indexSecondBorder - stepsToLeft(indexFirstBorder, numericValues), attributeIndex);
			}
			//Merkbaar
			else {
				//Group on the left
				if (!isGroupGeringOnLeftSide(indexFirstBorder, attributeIndex))
					return calculateRule(numericValues, indexFirstBorder + stepsToRight(indexFirstBorder, numericValues), indexSecondBorder + stepsToRight(indexFirstBorder, numericValues), attributeIndex);
				//Group on the right
				else
					return calculateRule(numericValues, indexFirstBorder - stepsToLeft(indexFirstBorder, numericValues), indexSecondBorder - stepsToLeft(indexFirstBorder, numericValues), attributeIndex);
			}
		}
	}
	
	private boolean isGroupGeringOnLeftSide(int lastBorder, int attributeIndex) {
		List<String> merkbaarOrGering = data.getMerkbaarOrGeringBySortedAttribute(attributeIndex);
		List<List<Integer>> partitions = partitioning(attributeIndex);
		for (List<Integer> partition : partitions){
			if (partition.get(partition.size()-1) == lastBorder) {				
				int timesMerkbaar = 0;
				int timesGering = 0;
				for (int i = partition.get(0) ; i < partition.get(1) ; i++) {
					if (merkbaarOrGering.get(i).contains("merkbaar"))
						timesMerkbaar++;
					else
						timesGering++;
				}
				return timesGering > timesMerkbaar ? true : false;
			}
		}
		return false;
	}
	
	/*
	 * If a border has more then 2 of the same values, you've to calculate how many steps it needs to go 
	 * to the left. For example is you've 3 of the same values and you want to add them to the right group, 
	 * then the border needs to go to the left. But you were either comparing the first two or 
	 * the last to. In the first case your border needs to take 1 step, in the second case it needs 
	 * 2 steps.
	 */
	private int stepsToLeft(int indexFirstBorder, List<Double> numericValues) {
		double firstBorder = numericValues.get(indexFirstBorder);
		int steps = 0;
		for (int i = indexFirstBorder - 1; i <= 0; i--) {
			if (numericValues.get(i) == firstBorder)
				steps++;
			else
				return steps;
		}
		return steps;
	}
	
	private int stepsToRight(int indexFirstBorder, List<Double> numericValues) {
		double firstBorder = numericValues.get(indexFirstBorder);
		int steps = 0;
		for (int i = indexFirstBorder + 1; i < numericValues.size(); i++) {
			if (numericValues.get(i) == firstBorder) 
				steps++;
			else
				return steps;
		}
		return steps;
	}
}
