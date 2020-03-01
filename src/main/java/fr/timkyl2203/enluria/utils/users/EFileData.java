package fr.timkyl2203.enluria.utils.users;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EFileData {

    private static Map<String, File> data = new HashMap<>();

    public static void addData(String key, File val){
        data.put(key, val);
    }

    public static boolean isValue(String key){
        return data.containsKey(key);
    }

    public static File getData(String key){
        return data.get(key);
    }

}
