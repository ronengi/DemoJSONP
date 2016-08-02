package com.ronengi;


import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by stimpy on 8/2/16.
 * https://jsonp.java.net/download.html
 */
public class Streaming {

    public String createJson() {
        // FileWriter writer = new FileWriter("test.txt");
        // JsonGenerator gen = Json.createGenerator(writer);
        StringWriter sw = new StringWriter();
        JsonGenerator gen = Json.createGenerator(sw);
        gen.writeStartObject()
                .write("firstName", "Duke")
                .write("lastName", "Java")
                .write("age", 18)
                .write("streetAddress", "100 Internet Dr")
                .write("city", "JavaTown")
                .write("state", "JA")
                .write("postalCode", "12345")
                .writeStartArray("phoneNumbers")
                .writeStartObject()
                .write("type", "mobile")
                .write("number", "111-111-1111")
                .writeEnd()
                .writeStartObject()
                .write("type", "home")
                .write("number", "222-222-2222")
                .writeEnd()
                .writeEnd()
                .writeEnd();
        gen.close();
        return sw.toString();
    }


    public JsonParser readJson(String jsonData) throws MalformedURLException, IOException {
        JsonParser parser = Json.createParser(new StringReader(jsonData));
        return parser;
    }


    public JsonParser readJson() throws MalformedURLException, IOException {
        URL url = new URL("https://api.github.com/users/ronengi/repos");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        // JsonParser parser = Json.createParser(new StringReader(jsonData));
        JsonParser parser = Json.createParser(con.getInputStream());
        return parser;
    }


    public void printJson(JsonParser parser) {
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch(event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    System.out.println(event.toString());
                    break;
                case KEY_NAME:
                    System.out.print(event.toString() + " " +
                            parser.getString() + " - ");
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.println(event.toString() + " " +
                            parser.getString());
                    break;
            }
        }
    }

}
