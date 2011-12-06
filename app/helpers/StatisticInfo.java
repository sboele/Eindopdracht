/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sander
 */
public class StatisticInfo {
    Data data;
    Statistics statistics;
    
    public StatisticInfo(boolean isUsingRulesForOrdinalData) {
        data = new Data(isUsingRulesForOrdinalData);
        statistics = new Statistics();
    }
    
    public Map<String, List<String>> getStatisticInfo()
    {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        Map<String, Map<String, List<Double>>> numericAttributesAndValues = data.getNumericAttributesAndValues();
        for(String keyx : numericAttributesAndValues.keySet()) {
            Map<String, List<Double>> resultAndValues = numericAttributesAndValues.get(keyx);
            for(String keyy : resultAndValues.keySet()){
                double sd = statistics.getStandardDeviation(resultAndValues.get(keyy));
                double mean = statistics.getMean(resultAndValues.get(keyy));
                List<String> temp = new ArrayList<String>();
                temp.add("Gemiddelde: "+mean);
                temp.add("Standaard Deviatie: "+sd);
                temp.add("Getallen: "+resultAndValues.get(keyy).toString());
                result.put("Attribuut: "+keyx+" -- Resultaat: "+keyy, temp);
            }
        }
        return result;
    }
}
