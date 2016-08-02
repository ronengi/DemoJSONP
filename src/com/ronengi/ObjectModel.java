package com.ronengi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.json.*;


import javax.net.ssl.HttpsURLConnection;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

/**
 * Created by stimpy on 8/2/16.
 * https://jsonp.java.net/download.html
 */
public class ObjectModel {

    public void writeJson(JsonStructure tree) {
        StringWriter stWriter = new StringWriter();

        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.write(tree);
        }
        // jsonWriter.close();

        String jsonData = stWriter.toString();
        System.out.println(jsonData);
    }


    public JsonStructure createJson() {
        JsonObject tree = Json.createObjectBuilder()
                .add("firstName", "Duke")
                .add("lastName", "Java")
                .add("age", 18)
                .add("streetAddress", "100 Internet Dr")
                .add("city", "JavaTown")
                .add("state", "JA")
                .add("postalCode", "12345")
                .add("phoneNumbers", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("type", "mobile")
                                .add("number", "111-111-1111"))
                        .add(Json.createObjectBuilder()
                                .add("type", "home")
                                .add("number", "222-222-2222")))
                .build();

        return tree;
    }


    public JsonStructure readJson() throws MalformedURLException, IOException {
        URL url = new URL("https://api.github.com/users/ronengi/repos");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JsonReader jReader = Json.createReader(br);

        //JsonReader jReader = Json.createReader(new FileReader("json.txt"));

        JsonStructure tree = jReader.read();

        return tree;

        /*
        Scanner scan = new Scanner(br);
        //scan.useDelimiter("\\{|\\[");
        while (scan.hasNext()) {
            System.out.println(scan.nextLine());
        }
        */

    }


    public void navigateTree(JsonValue tree, String key) {
        if (key != null)
            System.out.print("Key " + key + ": ");
        switch(tree.getValueType()) {
            case OBJECT:
                System.out.println("OBJECT");
                JsonObject object = (JsonObject) tree;
                for (String name : object.keySet())
                    navigateTree(object.get(name), name);
                break;
            case ARRAY:
                System.out.println("ARRAY");
                JsonArray array = (JsonArray) tree;
                for (JsonValue val : array)
                    navigateTree(val, null);
                break;
            case STRING:
                JsonString st = (JsonString) tree;
                System.out.println("STRING " + st.getString());
                break;
            case NUMBER:
                JsonNumber num = (JsonNumber) tree;
                System.out.println("NUMBER " + num.toString());
                break;
            case TRUE:
            case FALSE:
            case NULL:
                System.out.println(tree.getValueType().toString());
                break;
        }
    }

}
