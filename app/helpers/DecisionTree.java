/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.smartcardio.ATR;
import play.Logger;

/**
 *
 * @author Sander
 */
public class DecisionTree {

    Data data;
    double infoResultaat = 0.0;
    int totaal;
    
    public DecisionTree() {
        data = new Data();
    }

    public Map<String, Double> getInfoAndGain() {
        Map<String, Double> infoAndGain = new HashMap<String, Double>();
        Map<String, List<Integer>> attributesAndResults = data.getAttributesAndResults(data.getNumberOfAttributes()-1);
        totaal = attributesAndResults.get("merkbaar").get(1) + attributesAndResults.get("gering").get(0);
        infoResultaat = getInfo(attributesAndResults.get("merkbaar").get(1), attributesAndResults.get("gering").get(0));
        Logger.info("infoResultaat: %s",infoResultaat);
        for(int i = 0 ; i < data.getNumberOfAttributes()-1 ; i++) {
            if(data.getUniqueValuesForAttributeInOrdinalData(i, false).size() < data.getNumericBodyLotionData().size()) {
                attributesAndResults = data.getAttributesAndResults(i);
                double info = 0.0;
                for(String key : attributesAndResults.keySet()) {
                    List<Integer> l = attributesAndResults.get(key);
                    Logger.info("Key: %s List: %s",key,l);
                    double numberOfGering = l.get(0);
                    double numberOfMerkbaar = l.get(1);
                    info += ((numberOfGering + numberOfMerkbaar)/totaal)*getInfo(numberOfMerkbaar, numberOfGering);
                }
                Logger.info("Info: %s",info);
                infoAndGain.put(data.getAttributes().get(i), infoResultaat-info);
            }
        }
        Logger.info("Info And Gain: %s",infoAndGain);
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
        Logger.info("Merkbaar: %s - Gering: %s - Total: %s", numberOfMerkbaar, numberOfGering, total);

        if (numberOfMerkbaar == 0 || numberOfGering == 0) {
            return info;
        }
        if (numberOfMerkbaar == numberOfGering) {
            return 1.0;
        }

        info = (-numberOfMerkbaar * Math.log10(numberOfMerkbaar) / logfactor - numberOfGering * Math.log10(numberOfGering) / logfactor + total * Math.log10(total) / logfactor) / (total);
        Logger.info("Info: %s bits", info);
        
        return info;
    }
}
