package com.config;
import java.util.Map;
public class ConfigReaderTest {
    public static void main(String[] args) {
        ConfigReader conf = new ConfigReader();
        
        // System.out.println("ID size: " + conf.getFieldSize("ID"));
        // System.out.println("Name size: " + conf.getFieldSize("Name"));
        // System.out.println("Phone size: " + conf.getFieldSize("Phone"));
        // System.out.println("Total size: " + conf.getFieldSize("Total"));
        
        int total = 0;
        Map<String, Integer> fields = conf.getFields();
        for (Map.Entry<String, Integer> entry : fields.entrySet()) {
            System.out.println(entry.getKey() + " size: " + entry.getValue());
            total += entry.getValue();
        }

        System.out.println("Total size: " + (total));

        System.out.println(conf.getRecordSize());
    }
}