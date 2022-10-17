package org.example;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Json {
    public static JSONArray parse(String src) throws ParseException, IOException, FileNotFoundException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(src));
        JSONObject jsonObject = (JSONObject)obj;
        JSONArray data = (JSONArray)jsonObject.get("map");
        return data;
    }
}
