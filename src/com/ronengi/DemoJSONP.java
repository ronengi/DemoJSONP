package com.ronengi;

import javax.json.JsonStructure;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by stimpy on 8/2/16.
 * https://jsonp.java.net/download.html
 */
public class DemoJSONP {

    public static void main(String[] args) throws IOException {
        streamingDemo();
        // objectModelDemo();
    }


    public static void streamingDemo() throws IOException {
        Streaming sDemo = new Streaming();

        // sDemo.printJson(sDemo.readJson());
        sDemo.printJson(sDemo.readJson(sDemo.createJson()));
    }


    public static void objectModelDemo() throws IOException {
        ObjectModel oDemo = new ObjectModel();

        JsonStructure tree = oDemo.createJson();
        // JsonStructure tree = oDemo.readJson();

        oDemo.navigateTree(tree, null);
        // oDemo.writeJson(tree);

    }


}
