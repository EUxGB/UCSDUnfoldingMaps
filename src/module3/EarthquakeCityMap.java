package module3;
//Java utilities libraries

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
//import java.util.Collections;
//import java.util.Comparator;
//Processing library
//Unfolding libraries
//Parsing library

/**
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 *
 * @author Your name here
 * Date: July 17, 2015
 */
public class EarthquakeCityMap extends PApplet {

    // Less than this threshold is a light earthquake
    public static final float THRESHOLD_MODERATE = 5;
    // Less than this threshold is a minor earthquake
    public static final float THRESHOLD_LIGHT = 4;
    // You can ignore this.  It's to keep eclipse from generating a warning.
    private static final long serialVersionUID = 1L;
    // IF YOU ARE WORKING OFFLINE, change the value of this variable to true
    private static final boolean offline = false;
    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";
    int colorTHRESHOLD_MODERATE = color(255, 255, 0);
    int colorHRESHOLD_LIGHT = color(150, 150, 0);
    int colorAnother = color(64, 44, 199);
    // The map
    private UnfoldingMap map;
    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

    public void setup() {
        size(950, 600, OPENGL);
        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";    // Same feed, saved Aug 7, 2015, for working offline
        } else {
            map = new UnfoldingMap(this, 200, 50, 700, 500, new OpenStreetMap.OpenStreetMapProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);
        // The List you will populate with new SimplePointMarkers
        List<Marker> markers = new ArrayList<Marker>();
        //Use provided parser to collect properties for each earthquake
        //PointFeatures have a getLocation method
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        //TODO (Step 3): Add a loop here that calls createMarker (see below)
        // to create a new SimplePointMarker for each PointFeature in
        // earthquakes.  Then add each new SimplePointMarker to the
        // List markers (so that it will be added to the map in the line below)
        for (PointFeature earthquake : earthquakes) {
            markers.add(createMarker(earthquake));

        }
        // Add the markers to the map so that they are displayed
        map.addMarkers(markers);
    }

    /* createMarker: A suggested helper method that takes in an earthquake
     * feature and returns a SimplePointMarker for that earthquake
     *
     * In step 3 You can use this method as-is.  Call it from a loop in the
     * setp method.
     *
     * TODO (Step 4): Add code to this method so that it adds the proper
     * styling to each marker based on the magnitude of the earthquake.
     */
    private SimplePointMarker createMarker(PointFeature feature) {
        // To print all of the features in a PointFeature (so you can see what they are)
        // uncomment the line below.  Note this will only print if you call createMarker
        // from setup
        //System.out.println(feature.getProperties());
        // Create a new SimplePointMarker at the location given by the PointFeature
        SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
        Object magObj = feature.getProperty("magnitude");
        float mag = Float.parseFloat(magObj.toString());
        System.out.println(Float.parseFloat(feature.getProperty("magnitude").toString()));
        // Here is an example of how to use Processing's color method to generate
        // an int that represents the color yellow.
        // TODO (Step 4): Add code below to style the marker's size and color
        // according to the magnitude of the earthquake.
        // Don't forget about the constants THRESHOLD_MODERATE and
        // THRESHOLD_LIGHT, which are declared above.
        // Rather than comparing the magnitude to a number directly, compare
        // the magnitude to these variables (and change their value in the code
        // above if you want to change what you mean by "moderate" and "light")
        if (mag < THRESHOLD_LIGHT) {
            marker.setRadius(12);
            marker.setColor(colorAnother);
        } else if (mag >= THRESHOLD_MODERATE) {
            marker.setRadius(18);
            marker.setColor(colorTHRESHOLD_MODERATE);
        } else {
            marker.setColor(colorHRESHOLD_LIGHT);
            marker.setRadius(15);


        }
        // Finally return the marker
        return marker;
    }

    public void draw() {
        background(204, 153, 204);
        map.draw();
        addKey();

    }

    // helper method to draw key in GUI
    // TODO: Implement this method to draw the key
    private void addKey() {
        noStroke();
        fill(204, 102, 204);
        rect(30, 50, 150, 370, 10);
        fill(204, 51, 204);
        rect(35, 100, 140, 30, 10);
        rect(35, 145, 140, 30, 10);
        rect(35, 190, 140, 30, 10);
        rect(35, 235, 140, 30, 10);
        rect(35, 300, 140, 30, 10);
        fill(colorTHRESHOLD_MODERATE);
        noStroke();
        ellipse(50, 115, 20, 20);
        fill(colorHRESHOLD_LIGHT);
        noStroke();
        ellipse(50, 160, 20, 20);
        fill(colorAnother);
        noStroke();
        ellipse(50, 205, 20, 20);
        fill(colorAnother);
        noStroke();
        ellipse(50, 205, 20, 20);
        textSize(16);
        fill(255);
        textAlign(LEFT);
        text("MAGNITUDE", 70, 90);
        textSize(14);
        text("     > 5", 70, 122);
        text("from 4 to 5", 70, 167);
        text("     < 4", 70, 212);
        String time = String.valueOf(hour());
        text(time + ":", 70, 255);
        time = String.valueOf(minute());
        text(time + ":", 100, 255);
        time = String.valueOf(second());
        text(time, 130, 255);
        fill(145, 1, 1);
		text("***SHAKE***", 55, 320);
		textSize(10);
		text("***Click right button***", 50, 345);
		// Remember you can use Processing's graphics methods here
	}

    public void mousePressed() {

		if (mousePressed && (mouseButton == RIGHT)){
        noLoop();
        double longitude = 180 - (Math.random() * 360);
        double latitude = 90 - (Math.random() * 180);
        SimplePointMarker markerRnd = new SimplePointMarker(new Location(longitude, latitude));
        markerRnd.setColor(color(145, 1, 1));
        markerRnd.setRadius(20);
        map.addMarker(markerRnd);}
    }

    public void mouseReleased() {
        loop();
    }

}
