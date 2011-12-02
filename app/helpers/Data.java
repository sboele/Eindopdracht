/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Sander
 */
public class Data {

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
    String[][] bodyLotionOrdinalData = new String[][]{
        {"Naam", "Haarkleur", "Lengte", "Gewicht", "Lotion", "Resultaat"},
        {"Sarah", "blond", "middel", "licht", "nee", "merkbaar"},
        {"Alex", "blond", "groot", "normaal", "ja", "gering"},
        {"Diana", "bruin", "klein", "licht", "ja", "gering"},
        {"Anne", "blond", "klein", "licht", "nee", "merkbaar"},
        {"Emily", "rood", "middel", "zwaar", "nee", "merkbaar"},
        {"Peter", "bruin", "middel", "zwaar", "nee", "gering"},
        {"Jan", "bruin", "middel", "normaal", "nee", "gering"},
        {"Sander", "donkerblond", "middel", "normaal", "ja", "merkbaar"},
        {"Ronald", "paars", "klein", "zwaar", "ja", "merkbaar"},
        {"Leonard", "bruin", "middel", "normaal", "ja", "merkbaar"},
        {"Katie", "blond", "klein", "licht", "ja", "gering"}};

    public List<String> getValuesForLikeliHood() {
    	List<String> values = new ArrayList<String>();
    	values.add("bruin");
    	values.add("middel");
    	values.add("normaal");
    	values.add("ja");
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
        return bodyLotionNumericData[rowIndex][getNumberOfAttributes()];
    }

    public List<List<String>> orderByAttribute(int attributeIndex) {
        List<List<String>> result = getNumericBodyLotionData();
        result.remove(0);

        Sorter.sort(result, attributeIndex);

        result.add(0, getAttributes());
        return result;
    }
    
    public Map<String, List<Double>> getNumericAttributesAndValues() {
        Map<String,List<Double>> result = new HashMap<String, List<Double>>();
        for(int i = 0 ; i < getNumberOfAttributes() ; i++) {
            List<Double> values = getNumericValuesForAttributeInNumericData(i);
            if(!values.isEmpty()) {
                result.put(getAttributes().get(i), values);
            }
        }
        System.out.println(result.toString());
        return result;
    }
}
