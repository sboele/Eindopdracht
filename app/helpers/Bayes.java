/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sander
 */
public class Bayes {

    Data data = new helpers.Data();
    private int numberOfColumns = 6;
    private int maxValues = data.maxNumberOfUniqueValuesOrdinal();
    List<List<String>> rows = new ArrayList<List<String>>();

    public List<List<String>> getBayesData() {
        rows.add(getPossibleOutcomesWithEmptyColumn());
        createBayesTable(false);
        createBayesTable(true);
        return rows;
    }

    public void createBayesTable(boolean summary) {
        for (int i = 0; i < maxValues; i++) {
            List<String> l = new ArrayList<String>();
            for (int j = 1; j < numberOfColumns; j++) {
                if (data.getUniqueValuesForAttributeInOrdinalData(j, false).size() > i) {
                    l.add(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i));
                    if (summary) {
                        l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false).get("merkbaar"));
                        l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)) + "/" + data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false).get("gering"));
                    } else {
                        l.add("" + data.getNumberOfMerkbaar(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                        l.add("" + data.getNumberOfGering(data.getUniqueValuesForAttributeInOrdinalData(j, false).get(i)));
                    }
                }
            }
            rows.add(l);
        }
    }

    public List<String> getPossibleOutcomesWithEmptyColumn() {
        List<String> row = new ArrayList<String>();
        for (int i = 0; i < numberOfColumns - 1; i++) {
            row.add("");
            Iterator it = data.getGroupedValuesForAttributeInOrdinalData(numberOfColumns - 1, false).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                row.add(pairs.getKey().toString());
            }
        }
        return row;
    }
}