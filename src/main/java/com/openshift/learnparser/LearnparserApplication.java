package com.openshift.learnparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.System.*;

@SpringBootApplication
public class LearnparserApplication {
    @Autowired
    private static Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(LearnparserApplication.class, args);

        if(args.length == 0 || !args[0].startsWith("--json")) {
            err.println("Usage java -jar JARNAME --json=PathToJson");
            exit(1);
        }
        JSONParser parser = new JSONParser();
        HashMap <String, Integer> scenarios = new HashMap<String, Integer>();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(args[0].substring(7)));

            for(Object o : a) {
                JSONObject scenario = (JSONObject) o;
                if(scenarios.containsKey(scenario.get("scenario_id"))) {
                    String key = (String) scenario.get("scenario_id");
                    Integer current = new Integer((String)scenario.get("started"));
                    Integer newvalue = (scenarios.get(key)) + current;



                    scenarios.put(key, newvalue );
                } else {
                    String key = (String) scenario.get("scenario_id");
                    Integer value = new Integer((String) scenario.get("started"));
                    scenarios.put(key, value);
                }
            }
            Iterator<Map.Entry<String, Integer>> iterator = scenarios.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                System.out.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
