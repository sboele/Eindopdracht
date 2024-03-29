/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import play.Logger;

/**
 *
 * @author Sander
 */
public class DecisionTree {

    Data data;
    double infoResultaat;
    int totaal;
    
    public DecisionTree(boolean isUsingRulesForOrdinalData) {
        data = new Data(isUsingRulesForOrdinalData);
        infoResultaat = 0.0;
    }

    public Map<String, Double> getInfoAndGain() {
        Map<String, Double> infoAndGain = new HashMap<String, Double>();
        Map<String, List<Integer>> attributesAndResults = data.getValuesAndTimesOccuring(data.getNumberOfAttributes()-1);
        totaal = attributesAndResults.get("merkbaar").get(1) + attributesAndResults.get("gering").get(0);
        infoResultaat = getInfo(attributesAndResults.get("merkbaar").get(1), attributesAndResults.get("gering").get(0));
        for(int i = 0 ; i < data.getNumberOfAttributes()-1 ; i++) {
            if(data.getUniqueValuesForAttributeInOrdinalData(i, false).size() < data.getNumericBodyLotionData().size()-1) {
                attributesAndResults = data.getValuesAndTimesOccuring(i);
                double info = 0.0;
                for(String key : attributesAndResults.keySet()) {
                    List<Integer> l = attributesAndResults.get(key);
                    double numberOfGering = l.get(0);
                    double numberOfMerkbaar = l.get(1);
                    info += ((numberOfGering + numberOfMerkbaar)/totaal)*getInfo(numberOfMerkbaar, numberOfGering);
                }
                infoAndGain.put(data.getAttributes().get(i), infoResultaat-info);
            }
        }
        return infoAndGain;
    }
    
    public String getHighestGain() {
        double highestD = 0;
        String highestS = "";
        for(String key : getInfoAndGain().keySet()) {
            if(getInfoAndGain().get(key) > highestD) {
                highestD = getInfoAndGain().get(key);
                highestS = key;
            }
        }
        return highestS;
    }
    
    public double getInfo(double numberOfMerkbaar, double numberOfGering) {
        
        double info = 0.0;
        double total = numberOfGering + numberOfMerkbaar;
        double logfactor = Math.log10(2);

        if (numberOfMerkbaar == 0 || numberOfGering == 0) {
            return info;
        }
        if (numberOfMerkbaar == numberOfGering) {
            return 1.0;
        }

        info = (-numberOfMerkbaar * Math.log10(numberOfMerkbaar) / logfactor - numberOfGering * Math.log10(numberOfGering) / logfactor + total * Math.log10(total) / logfactor) / (total);
        
        return info;
    }
}
