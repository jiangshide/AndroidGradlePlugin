package com.android.jsdplugin;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * created by jiangshide on 2/25/21.
 * email:18311271399@163.com
 */
public class MainTest {
    public static void test() {

        Map<String, String> map = Collections.synchronizedMap(new LinkedHashMap<String, String>() {
            @Override
            protected boolean removeEldestEntry(Entry<String, String> eldest) {
                return size() > 5;
            }
        });

        for (int i = 0; i < 10; i++) {
            map.put("jsd" + i, "z->" + i);
        }
    }
}
