/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sander
 */
// TODO: Total gering/merkbaar in one row
public class Bayes {

    Data data;
    private int numberOfColumns = 6;
    private int maxValuesOrdinal;
    private int maxValuesNumeric;
    List<List<String>> rows;
    
    public Bayes(boolean isUsingRulesForOrdinalData) {
    	data = new Data(isUsingRulesForOrdinalData);
    	numberOfColumns = 6;
        maxValuesOrdinal = data.maxNumberOfUniqueValuesOrdinal();
        maxValuesNumeric = data.maxNumberOfUniqueValuesNumeric();
        rows = new ArrayList<List<String>>();
    }

    public List<List<String>> getNaiveBayesNominal() {
        rows.add(getPossibleOutcomesWithEmptyColumn());
        createBayesTableNominal(false);
        createBayesTableNominal(true);
        return rows;
    }

    public List<List<String>> getNaiveBayesNumeric() {
        rows.add(getPossibleOutcomesWithEmptyColumn());
        createBayesTableNumeric(false);
        return rows;
    }

    public void createBayesTableNominal(boolean summary) {
        for (int i = 0; i < maxValuesOrdinal; i++) {
            List<String> l = new ArrayList<String>();
            for (int j = 1; j < numberOfColumns; j++) {
                if (data.getUniqueValuesForAttributeInOrdinalData(j, false).size() > i) {
                    l.add(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i));
                    if (summary) {
                        if (j == numberOfColumns - 1) {
                            l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + (data.getOrdinalBodyLotionData().size() - 1));
                            l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + (data.getOrdinalBodyLotionData().size() - 1));
                        } else {
                            l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false).get("merkbaar"));
                            l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false).get("gering"));
                        }
                    } else {
                        l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                        l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                    }
                } else {
                    l.add("");
                    l.add("");
                    l.add("");
                }
            }
            rows.add(l);
        }
    }

    public void createBayesTableNumeric(boolean summary) {
        for (int i = 0; i < maxValuesNumeric; i++) {
            List<String> l = new ArrayList<String>();
            for (int j = 1; j < numberOfColumns; j++) {
                if (data.getUniqueValuesForAttributeInNumericData(j, false).size() > i) {
                    l.add(data.getUniqueValuesForAttributeInNumericData(j, false).get(i));
                    l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInNumericData(j, false).get(i)));
                    l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInNumericData(j, false).get(i)));
                } else {
                    l.add("");
                    l.add("");
                    l.add("");
                }
            }
            rows.add(l);
        }
    }

    public List<String> getPossibleOutcomesWithEmptyColumn() {
        List<String> row = new ArrayList<String>();
        for (int i = 0; i < numberOfColumns - 1; i++) {
            row.add("");
            Map<String, Integer> map = data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false);
            for (String key : map.keySet()) {
                row.add(key);
            }
        }
        return row;
    }
}