/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Sander
 */
public class Sorter {

    public static void sort(final List<List<String>> toSort, final int onColumn) {
        Collections.sort(toSort, new Comparator() {

            public int compare(List<String> a, List<String> b) {
                return a.get(onColumn).compareTo(b.get(onColumn));
            }

            @Override
            public int compare(Object o1, Object o2) {
                List<String> a = (List<String>) o1;
                List<String> b = (List<String>) o2;
                return compare(a, b);
            }
        });
    }
}
