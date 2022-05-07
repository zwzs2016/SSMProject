package collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class MapTest {
    @Test
    public void maptest(){
        Map<String,String> stringMap=new HashMap<>();
        Map<String,String> stringtable=new Hashtable<>();
        stringMap.put("one","a");
        stringMap.put("two","b");
        stringMap.put(null,null);
        stringtable.put("three","c");
        stringtable.put("four","d");
        //System.out.println(stringMap["one"]);
        for (String m:stringMap.values()){
            System.out.println(m);
        }
        for (String m:stringMap.keySet()){
            System.out.println(m);
        }
        for (Map.Entry m:stringMap.entrySet()){
            System.out.println(m.getKey()+":"+m.getValue());
        }
        for (Iterator<Map.Entry<String,String>> it=stringMap.entrySet().iterator();it.hasNext();){
            System.out.println(it.next());
        }

    }
}
