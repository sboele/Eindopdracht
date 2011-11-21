/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
        {"Sarah", "blond", "1,63", "49", "nee", "merkbaar"},
        {"Alex", "blond", "1,85", "70", "ja", "gering"},
        {"Diana", "bruin", "1,50", "51", "ja", "gering"},
        {"Anne", "blond", "1,55", "54", "nee", "merkbaar"},
        {"Emily", "rood", "1,67", "80", "nee", "merkbaar"},
        {"Peter", "bruin", "1,81", "90", "nee", "gering"},
        {"Jan", "bruin", "1,61", "76", "nee", "gering"},
        {"Katie", "blond", "1,53", "43", "ja", "gering"}};

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
        {"Katie", "blond", "klein", "licht", "ja", "gering"}};

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

    public List<String> getValuesForAttributeInNumericData(int attributeIndex, boolean includeHeader) {
        List<String> values = new ArrayList<String>();
        for (int i = getStartIndex(includeHeader); i < bodyLotionNumericData.length; i++) {
            values.add(bodyLotionNumericData[i][attributeIndex]);
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
}
