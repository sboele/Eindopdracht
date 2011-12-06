/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import play.Logger;

import play.Logger;

/**
 * 
 * @author Sander
 */
public class Data {
	
	public Data() {
		createOrdinalDataManually();
	}
	
	public Data(boolean isUsingRulesForOrdinalData) {
		if (isUsingRulesForOrdinalData)
			createOrdinalDataWithRules();
		else
			createOrdinalDataManually();
	}

    String[][] bodyLotionNumericData = new String[][]{
        {"Naam", "Haarkleur", "Lengte", "Gewicht", "Lotion", "Resultaat"},
        {"Sarah", "blond", "1.63", "49", "nee", "merkbaar"},
        {"Alex", "blond", "1.85", "70", "ja", "gering"},
        {"Diana", "bruin", "1.50", "51", "ja", "gering"},
        {"Anne", "blond", "1.55", "54", "nee", "merkbaar"},
        {"Emily", "rood", "1.67", "80", "nee", "merkbaar"},
        {"Peter", "bruin", "1.81", "90", "nee", "gering"},
        {"Jan", "bruin", "1.61", "76", "nee", "gering"},
        {"Katie", "blond", "1.53", "43", "ja", "gering"}};

    String[][] bodyLotionOrdinalData = new String[bodyLotionNumericData.length][bodyLotionNumericData[0].length];
    
    private void createOrdinalDataManually() {
    	for (int i = 0; i < bodyLotionNumericData.length; i++) {
    		bodyLotionOrdinalData[i] = bodyLotionNumericData[i].clone();
    		if (i > 0) {
    			bodyLotionOrdinalData[i][2] = ordinalLengte(bodyLotionOrdinalData[i][2]);
    			bodyLotionOrdinalData[i][3] = ordinalGewicht(bodyLotionOrdinalData[i][3]);
    		}
    	}
    }
    
    private void createOrdinalDataWithRules() {
    	List<Double> rulesForLengte = new Discretization().getRules(2);
    	List<Double> rulesForGewicht = new Discretization().getRules(3);
    	for (int i = 0; i < bodyLotionNumericData.length; i++) {
    		bodyLotionOrdinalData[i] = bodyLotionNumericData[i].clone();
    		if (i > 0) {
    			bodyLotionOrdinalData[i][2] = ordinalLengte(bodyLotionOrdinalData[i][2], rulesForLengte);
    			bodyLotionOrdinalData[i][3] = ordinalGewicht(bodyLotionOrdinalData[i][3], rulesForGewicht);
    		}
    	}
    }
    
    private String ordinalLengte(String lengteString) {
    	double lengte = Double.valueOf(lengteString);
    	if (lengte <= 1.60)
    		return "klein";
    	else if (lengte <= 1.85)
    		return "middel";
    	else
    		return "groot";
    }
    
    private String ordinalLengte(String lengteString, List<Double> rulesForLengte) {
    	double lengte = Double.valueOf(lengteString);
    	for (int i = 1; i < rulesForLengte.size(); i++) {
    		if (lengte <= rulesForLengte.get(i))
    			return "Lengte" + i;
    	}
    	return "error";
    }
    
    private String ordinalGewicht(String gewichtString) {
    	double gewicht = Double.valueOf(gewichtString);
    	if (gewicht <= 55.0)
    		return "licht";
    	else if (gewicht <= 80.0)
    		return "normaal";
    	else
    		return "zwaar";
    }
    
    private String ordinalGewicht(String gewichtString, List<Double> rulesForGewicht) {
    	double gewicht = Double.valueOf(gewichtString);
    	for (int i = 1; i < rulesForGewicht.size(); i++) {
    		if (gewicht <= rulesForGewicht.get(i))
    			return "Gewicht" + i;
    	}
    	return "error";
    }

    public List<String> getValuesForLikeliHood() {
    	List<String> values = new ArrayList<String>();
    	values.add("blond");
    	values.add("klein");
    	values.add("licht");
    	values.add("nee");
    	return values;
    }
    
    public List<List<String>> getNumericBodyLotionData() {
        List<List<String>> rows = new ArrayList<List<String>>();

        for (int i = 0; i < bodyLotionNumericData.length; i++) {
            List<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(bodyLotionNumericData[i]));
            rows.add(temp);
        }

        return rows;
    }

    public List<List<String>> getOrdinalBodyLotionData() {
        List<List<String>> rows = new ArrayList<List<String>>();

        for (int i = 0; i < bodyLotionOrdinalData.length; i++) {
            List<String> temp = new ArrayList<String>();
            temp.addAll(Arrays.asList(bodyLotionOrdinalData[i]));
            rows.add(temp);
        }

        return rows;
    }

    public List<String> getValuesForAttributeInNumericData(int attributeIndex, boolean includeHeader) {
        List<String> values = new ArrayList<String>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionNumericData.length; i++) {
            values.add(bodyLotionNumericData[i][attributeIndex]);
        }
        return values;
    }

    public List<Double> getNumericValuesForAttributeInNumericData(int attributeIndex) {
        List<Double> values = new ArrayList<Double>();
        for (int i = 1; i < bodyLotionNumericData.length; i++) {
            try {
                values.add(Double.parseDouble(bodyLotionNumericData[i][attributeIndex]));
            } catch (NumberFormatException nfe) {
                return values;
            }
        }
        return values;
    }
    
    public List<Double> getNumericValuesForAttributeInNumericDataFilteredByResult(int attributeIndex, String result) {
        List<Double> values = new ArrayList<Double>();
        for (int i = 1; i < bodyLotionNumericData.length; i++) {
            try {
                if(bodyLotionNumericData[i][getNumberOfAttributes()-1].equals(result)) {
                    values.add(Double.parseDouble(bodyLotionNumericData[i][attributeIndex]));
                }
            } catch (NumberFormatException nfe) {
                return values;
            }
        }
        return values;
    }

    public List<String> getValuesForAttributeInOrdinalData(int attributeIndex, boolean includeHeader) {
        List<String> values = new ArrayList<String>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionOrdinalData.length; i++) {
            values.add(bodyLotionOrdinalData[i][attributeIndex]);
        }
        return values;
    }

    public List<String> getUniqueValuesForAttributeInNumericData(int attributeIndex, boolean includeHeader) {
        Set<String> possibleValues = new HashSet<String>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionNumericData.length; i++) {
            possibleValues.add(bodyLotionNumericData[i][attributeIndex]);
        }
        return new ArrayList<String>(possibleValues);
    }

    public List<String> getUniqueValuesForAttributeInOrdinalData(int attributeIndex, boolean includeHeader) {
        Set<String> possibleValues = new HashSet<String>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionOrdinalData.length; i++) {
            possibleValues.add(bodyLotionOrdinalData[i][attributeIndex]);
        }
        return new ArrayList<String>(possibleValues);
    }

    public Map<String, Integer> getGroupedValuesForAttributeInNumericData(int attributeIndex, boolean includeHeader) {
        Map<String, Integer> groupedValues = new HashMap<String, Integer>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionNumericData.length; i++) {
            if (groupedValues.containsKey(bodyLotionNumericData[i][attributeIndex])) {
                groupedValues.put(bodyLotionNumericData[i][attributeIndex], groupedValues.get(bodyLotionNumericData[i][attributeIndex]) + 1);
            } else {
                groupedValues.put(bodyLotionNumericData[i][attributeIndex], 1);
            }
        }
        return groupedValues;
    }

    public Map<String, Integer> getGroupedValuesForAttributeInOrdinalData(int attributeIndex, boolean includeHeader) {
        Map<String, Integer> groupedValues = new HashMap<String, Integer>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionOrdinalData.length; i++) {
            if (groupedValues.containsKey(bodyLotionOrdinalData[i][attributeIndex])) {
                groupedValues.put(bodyLotionOrdinalData[i][attributeIndex], groupedValues.get(bodyLotionOrdinalData[i][attributeIndex]) + 1);
            } else {
                groupedValues.put(bodyLotionOrdinalData[i][attributeIndex], 1);
            }
        }
        return groupedValues;
    }

    public int getStartIndex(boolean includeHeader) {
        return includeHeader ? 0 : 1;
    }

    public int getNumberOfMerkbaar(String value) {
        int result = 0;
        for (int i = 0; i < getOrdinalBodyLotionData().size(); i++) {
            if (getOrdinalBodyLotionData().get(i).contains(value) && bodyLotionOrdinalData[i][5].equals("merkbaar")) {
                result++;
            }
        }
        return result;
    }

    public int getNumberOfGering(String value) {
        int result = 0;
        for (int i = 0; i < getOrdinalBodyLotionData().size(); i++) {
            if (getOrdinalBodyLotionData().get(i).contains(value) && bodyLotionOrdinalData[i][5].equals("gering")) {
                result++;
            }
        }
        return result;
    }

    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<String>();
        attributes.addAll(Arrays.asList(bodyLotionNumericData[0]));
        return attributes;
    }

    public int getIndexOfAttribute(String attribute) {
        return getAttributes().indexOf(attribute);
    }

    public int maxNumberOfUniqueValuesOrdinal() {
        int result = 0;
        for (int i = 1; i < getOrdinalBodyLotionData().get(0).size(); i++) {
            Map map = getGroupedValuesForAttributeInOrdinalData(i, false);
            if (map.size() > result) {
                result = map.size();
            }
        }
        return result;
    }

    public int maxNumberOfUniqueValuesNumeric() {
        int result = 0;
        for (int i = 1; i < getNumericBodyLotionData().get(0).size(); i++) {
            Map map = getGroupedValuesForAttributeInNumericData(i, false);
            if (map.size() > result) {
                result = map.size();
            }
        }
        return result;
    }

    public boolean isNumericAttribute(int attributeIndex) {
        return !getNumericValuesForAttributeInNumericData(attributeIndex).isEmpty();
    }

    public int getNumberOfAttributes() {
        return getAttributes().size();
    }

    public String getResultForRow(int rowIndex) {
        Logger.info("rowIndex: %s",rowIndex);
        return bodyLotionOrdinalData[rowIndex][getNumberOfAttributes()];
    }

    public List<List<String>> getOrderedNumericDataByAttribute(int attributeIndex) {
        List<List<String>> result = getNumericBodyLotionData();
        result.remove(0);
        Sorter.sort(result, attributeIndex);
        result.add(0, getAttributes());
        return result;
    }
    
    public List<Double> getNumericValuesForAttributeInSortedNumericData(int attributeIndex) {
        List<Double> values = new ArrayList<Double>();
        List<List<String>> orderedNumericData = getOrderedNumericDataByAttribute(attributeIndex);
        
        //Skip first row, because that one contains the names of the attributes
        for (List<String> row : orderedNumericData.subList(1, orderedNumericData.size())) {
        	try {
                values.add(Double.parseDouble(row.get(attributeIndex)));
            } catch (NumberFormatException nfe) {
            	Logger.info("NumberFormatException: %s", nfe);
                System.exit(-1);
            }
        }
        return values;
    }
    
    public List<String> getMerkbaarOrGeringBySortedAttribute(int attributeIndex) {
    	List<String> merkbaarOrGeringBySortedValues = new ArrayList<String>();
    	List<List<String>> orderedNumericData = getOrderedNumericDataByAttribute(attributeIndex);
    	
    	//Skip first row, because that one contains the names of the attributes
        for (List<String> row : orderedNumericData.subList(1, orderedNumericData.size())) {
        	merkbaarOrGeringBySortedValues.add(row.get(row.size()-1));
        }
        
    	return merkbaarOrGeringBySortedValues;
    }
    
    public Map<String, Map<String, List<Double>>> getNumericAttributesAndValues() {
        Map<String, Map<String, List<Double>>> result = new HashMap<String, Map<String, List<Double>>>();
        for(int i = 0 ; i < getUniqueValuesForAttributeInOrdinalData(getNumberOfAttributes()-1, false).size() ; i++) {
            for(int j = 0 ; j < getNumberOfAttributes() ; j++) {
                List<Double> values = getNumericValuesForAttributeInNumericDataFilteredByResult(j,getUniqueValuesForAttributeInNumericData(getNumberOfAttributes()-1, false).get(i));
                if(!values.isEmpty()) {
                    Map<String, List<Double>> temp;
                    if(!result.containsKey(getAttributes().get(j)))
                        temp = new HashMap<String, List<Double>>();
                    else
                        temp = result.get(getAttributes().get(j));
                    temp.put(getUniqueValuesForAttributeInNumericData(getNumberOfAttributes()-1, false).get(i), values);
                    result.put(getAttributes().get(j), temp);
                }
            }
        }
        return result;
    }
    
    public Map<String, List<Integer>> getValuesAndTimesOccuring(int attributeIndex) {
        Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
        for(int i = 1 ; i < getOrdinalBodyLotionData().size() ; i++) {
                String value = getOrdinalBodyLotionData().get(i).get(attributeIndex);
                String result = getOrdinalBodyLotionData().get(i).get(getNumberOfAttributes()-1);
                if(result.equals("gering")) {
                    List<Integer> total = results.get(value);
                    if(total == null) {
                        total = new ArrayList<Integer>();
                        total.add(0);
                        total.add(0);
                    }
                    int count = total.get(0)+1;
                    total.set(0, count);
                    results.put(value, total);
                }
                else if(result.equals("merkbaar")) {
                    List<Integer> total = results.get(value);
                    if(total == null) {
                        total = new ArrayList<Integer>();
                        total.add(0);
                        total.add(0);
                    }
                    int count = total.get(1)+1;
                    total.set(1, count);
                    results.put(value, total);
                }
                
        }
        return results;
    }
    
    public Map<String, List<Integer>> getValuesAndTimesOccuringInNumericData(int attributeIndex) {
        Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
        for(int i = 1 ; i < getNumericBodyLotionData().size() ; i++) {
                String value = getNumericBodyLotionData().get(i).get(attributeIndex);
                String result = getNumericBodyLotionData().get(i).get(getNumberOfAttributes()-1);
                if(result.equals("gering")) {
                    List<Integer> total = results.get(value);
                    if(total == null) {
                        total = new ArrayList<Integer>();
                        total.add(0);
                        total.add(0);
                    }
                    int count = total.get(0)+1;
                    total.set(0, count);
                    results.put(value, total);
                }
                else if(result.equals("merkbaar")) {
                    List<Integer> total = results.get(value);
                    if(total == null) {
                        total = new ArrayList<Integer>();
                        total.add(0);
                        total.add(0);
                    }
                    int count = total.get(1)+1;
                    total.set(1, count);
                    results.put(value, total);
                }
                
        }
        return results;
    }
}
