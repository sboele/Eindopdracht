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

    private int numberOfColumns = 5;
    private int maxAttributes = 0;
    public List<List<String>> getBayesData(List<List<String>> ordinalData) {
        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(getPossibleOutcomesWithEmptyColumn(ordinalData));

        for(int i = 0 ; i < getHaarkleuren(ordinalData).size() ; i++)
        {
            List<String> l = new ArrayList<String>();
            l.add(getHaarkleuren(ordinalData).get(i));
            l.add(""+getNumberOfMerkbaar(ordinalData,getHaarkleuren(ordinalData).get(i)));
            l.add(""+getNumberOfGering(ordinalData,getHaarkleuren(ordinalData).get(i)));
            rows.add(l);
        }
        
        for(int i = 0 ; i < getGewichten(ordinalData).size() ; i++)
        {
            rows.get(1+i).add(getGewichten(ordinalData).get(i));
            rows.get(1+i).add(""+getNumberOfMerkbaar(ordinalData,getGewichten(ordinalData).get(i)));
            rows.get(1+i).add(""+getNumberOfGering(ordinalData,getGewichten(ordinalData).get(i)));
        }
        
        for(int i = 0 ; i < getLengtes(ordinalData).size() ; i++)
        {
            rows.get(1+i).add(getLengtes(ordinalData).get(i));
            rows.get(1+i).add(""+getNumberOfMerkbaar(ordinalData,getLengtes(ordinalData).get(i)));
            rows.get(1+i).add(""+getNumberOfGering(ordinalData,getLengtes(ordinalData).get(i)));
        }
        
        for(int i = 0 ; i < getLotions(ordinalData).size() ; i++)
        {
            rows.get(1+i).add(getLotions(ordinalData).get(i));
            rows.get(1+i).add(""+getNumberOfMerkbaar(ordinalData,getLotions(ordinalData).get(i)));
            rows.get(1+i).add(""+getNumberOfGering(ordinalData,getLotions(ordinalData).get(i)));
        }
        
        for(int i = 0 ; i < getPossibleOutcomes(ordinalData).size() ; i++)
        {
            rows.get(1+i).add(getPossibleOutcomes(ordinalData).get(i));
            rows.get(1+i).add(""+getNumberOfMerkbaar(ordinalData,getPossibleOutcomes(ordinalData).get(i)));
            rows.get(1+i).add(""+getNumberOfGering(ordinalData,getPossibleOutcomes(ordinalData).get(i)));
        }
        //rows.add(getDataRows(ordinalData));
        return rows;
    }

    public int getNumberOfMerkbaar(List<List<String>> dataset, String value)
    {
        int result = 0;
        for(int i = 0 ; i < dataset.size() ; i++)
        {
            if(dataset.get(i).contains(value) && dataset.get(i).get(5).equals("merkbaar")) {
                result++;
            }
        }
        return result;
    }
    
    public int getNumberOfGering(List<List<String>> dataset, String value)
    {
        int result = 0;
        for(int i = 0 ; i < dataset.size() ; i++)
        {
            if(dataset.get(i).contains(value) && dataset.get(i).get(5).equals("gering")) {
                result++;
            }
        }
        return result;
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

    public List<List<String>> getDataRows(List<List<String>> dataset) {
        List<List<String>> rows = new ArrayList<List<String>>();

        return rows;
    }

    public List<String> getPossibleOutcomes(List<List<String>> dataset) {
        List<String> outcomes = new ArrayList<String>();
        for (int i = 1; i < dataset.size(); i++) {
            if (!outcomes.contains(dataset.get(i).get(5))) {
                outcomes.add(dataset.get(i).get(5));
            }
        }
        return outcomes;
    }
    public List<String> getHaarkleuren(List<List<String>> dataset) {
        List<String> haarkleuren = new ArrayList<String>();
        for (int i = 1; i < dataset.size(); i++) {
            if (!haarkleuren.contains(dataset.get(i).get(1))) {
                haarkleuren.add(dataset.get(i).get(1));
            }
        }
        return haarkleuren;
    }

    public List<String> getGewichten(List<List<String>> dataset) {
        List<String> gewichten = new ArrayList<String>();
        for (int i = 1; i < dataset.size(); i++) {
            if (!gewichten.contains(dataset.get(i).get(2))) {
                gewichten.add(dataset.get(i).get(2));
            }
        }
        return gewichten;
    }

    public List<String> getLengtes(List<List<String>> dataset) {
        List<String> lengtes = new ArrayList<String>();
        for (int i = 1; i < dataset.size(); i++) {
            if (!lengtes.contains(dataset.get(i).get(3))) {
                lengtes.add(dataset.get(i).get(3));
            }
        }
        return lengtes;
    }

    public List<String> getLotions(List<List<String>> dataset) {
        List<String> lotions = new ArrayList<String>();
        for (int i = 1; i < dataset.size(); i++) {
            if (!lotions.contains(dataset.get(i).get(4))) {
                lotions.add(dataset.get(i).get(4));
            }
        }
        return lotions;
    }

    public List<List<String>> getValues(List<List<String>> dataset) {
        List<List<String>> returnvalue = new ArrayList<List<String>>();
        List<String> list = getHaarkleuren(dataset);
        List<String> temp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        returnvalue.add(temp);

        list = getGewichten(dataset);
        temp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        returnvalue.add(temp);
        
        list = getLengtes(dataset);
        temp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        returnvalue.add(temp);
        
        list = getLotions(dataset);
        temp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        returnvalue.add(temp);
        
        list = getPossibleOutcomes(dataset);
        temp = new ArrayList<String>();
        for (int i = 1; i < list.size(); i++) {
            temp.add(list.get(i));
        }
        returnvalue.add(temp);
        
        return returnvalue;
    }
}
