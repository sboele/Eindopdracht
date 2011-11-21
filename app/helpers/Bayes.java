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
public class Bayes {

    private int numberOfColumns = 6;
    private int maxAttributes = 0;
    Data data = new helpers.Data();

    public List<List<String>> getBayesData(List<List<String>> ordinalData) {
        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(getPossibleOutcomesWithEmptyColumn(ordinalData));

        for (int i = 0; i < 3; i++) {
            List<String> l = new ArrayList<String>();
            for (int j = 1; j < numberOfColumns; j++) {
                if (data.getUniqueValuesForAttributeInOrdinalData(j, false).size() != i) {
                    l.add(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i));
                    l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                    l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                }
            }
            rows.add(l);
        }
        
        for (int test = 0; test < 3; test++) {
            List<String> l = new ArrayList<String>();
            for (int k = 1; k < numberOfColumns; k++) {
                if (data.getUniqueValuesForAttributeInOrdinalData(k, false).size() != test) {
                    l.add(data.getUniqueValuesForAttributeInOrdinalData(k, false).get(test));
                    l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(k, false).get(test))+"/"+data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns-1, false).get("merkbaar"));
                    l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(k, false).get(test))+"/"+data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns-1, false).get("gering"));
                }
            }
            rows.add(l);
        }
        
        return rows;
    }

    public List<String> getAttributes(List<List<String>> dataset) {
        return dataset.get(0);
    }

    public List<String> getPossibleOutcomesWithEmptyColumn(List<List<String>> dataset) {
        List<String> row = new ArrayList<String>();
        for (int i = 1; i < dataset.get(0).size(); i++) {
            row.add("");
            row.add("Merkbaar");
            row.add("Gering");
        }
        return row;
    }
}
