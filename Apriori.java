import java.util.*;
import java.io.*;
import java.security.Key;

public class Apriori {
    

    public static void main(String[] args) throws FileNotFoundException {
        int count = 0;
        Map<String, Integer> canditateSet = new HashMap<String, Integer>();

        Scanner read_file = new Scanner(new File("browsingdata.txt"));

        while (read_file.hasNextLine()) {
            String line = read_file.nextLine();
            count++;
            Scanner input = new Scanner(line);
            while (input.hasNext()) {
                String key = input.next();
                if (!canditateSet.containsKey(key)) {
                    canditateSet.put(key, 1);
                } else {
                    canditateSet.put(key, canditateSet.get(key) + 1);

                }

            }
            input.close();

        }
        System.out.println();
        System.out.println("Total Entries:" + count);
        
        System.out.println(canditateSet);

        read_file.close();

       
        Map<String, Integer> singleFrequentItem = getSingleFrequentItemSet(canditateSet, 100);
        System.out.println();
        Set<String> candidateKey = getcandidateSetC2(singleFrequentItem);
        Map<String, Integer> PiarSet = findC2SupportCount(candidateKey);
        findFreqPairSet(PiarSet, 100);

    }

    public static Map<String, Integer> getSingleFrequentItemSet(Map<String, Integer> ItemSet, int supportMin) {
        
        int count = 0;
        Map<String, Integer> freqItem = new HashMap<String, Integer>();
        
        
        for (Map.Entry<String, Integer> entry : ItemSet.entrySet()) {
            if (entry.getValue() >= supportMin) {
                count++;
                freqItem.put(entry.getKey(), entry.getValue());
            }
            

}      
        System.out.println();
        System.out.println("Single frequent itemset entries : " + count);
       
        for (Map.Entry<String, Integer> entry : freqItem.entrySet())

    System.out.println("Item = " + entry.getKey() + ", Count= " + entry.getValue());
        
        return freqItem;
}



    public static Set<String> getcandidateSetC2(Map<String, Integer> freqItem) {
        ArrayList<String> Items = new ArrayList<String>(freqItem.keySet());// saving freqent items in to this (just the
                                                                           // key)
        Set<String> c2ItemSet = new HashSet<String>();

        System.out.println("candidate pairs:");
        for (int i = 0; i < Items.size(); i++) {
            for (int j = i + 1; j < Items.size(); j++) {
                c2ItemSet.add("(" + Items.get(i) + ", " + Items.get(j) + ")");
                
            }

        }

        return c2ItemSet;
    }

    public static Map<String, Integer> findC2SupportCount(Set<String> candidateKey) throws FileNotFoundException {

        Map<String, Integer> pairSet = new HashMap<String, Integer>();
        

        for (String pair : candidateKey) {
            String first = pair.substring(1, 9);
            String second = pair.substring(11, 19);

            pairSet.put(pair, ((findPartial(second, findPartial(first))).size()));
        }

        System.out.println(pairSet);
        return pairSet;
    }

    public static void findFreqPairSet(Map<String, Integer> pairSet,int minSupport) {
        Map<String, Integer> freqPairSet = new HashMap<String, Integer>();
        System.out.println("");
        System.out.println("Frequent Item Pairs:");
        for (Map.Entry<String, Integer> entry : pairSet.entrySet()) {
         if (entry.getValue() >= minSupport) {
             freqPairSet.put(entry.getKey(), entry.getValue());
            
             System.out.println("Item = " + entry.getKey() + ", Count= " + entry.getValue());

           }
    
}
    }

    public static Set<String> findPartial(String item) throws FileNotFoundException {
        Set<String> partial = new HashSet<String>();
        Scanner read_file = new Scanner(new File("browsingdata.txt"));
        while (read_file.hasNextLine()) {
            String basket = read_file.nextLine();
            if (basket.contains(item)) {
                partial.add(basket);
            }
        }
        read_file.close();

        return partial;
    }

    public static Set<String> findPartial(String item, Set<String> partialSet) {
        Set<String> partial = new HashSet<String>();
        for (String basket : partialSet) {
            if (basket.contains(item)) {
                partial.add(basket);
            }
        }

        return partial;
    }
}
