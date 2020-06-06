package module4;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 *
 * @author Your name here
 * Date: July 17, 2015
 */
public class EarthquakeCityMap extends PApplet {
    // We will use member variables, instead of local variables, to store the data
    // that the setUp and draw methods will need to access (as well as other methods)
    // You will use many of these variables, but the only one you should need to add
    // code to modify is countryQuakes, where you will store the number of earthquakes
    // per country.

    // You can ignore this.  It's to get rid of eclipse warnings
    private static final long serialVersionUID = 1L;

    // IF YOU ARE WORKING OFFILINE, change the value of this variable to true
    private static final boolean offline = false;

    /**
     * This is where to find the local tiles, for working without an Internet connection
     */
    public static String mbTilesString = "blankLight-1-3.mbtiles";


    //feed with magnitude 2.5+ Earthquakes
    private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";


    // The files containing city names and info and country names and info
    private String cityFile = "city-data.json";
    private String countryFile = "countries.geo.json";

    // The map
    private UnfoldingMap map;

    // Markers for each city
    private List<Marker> cityMarkers;
    // Markers for each earthquake
    private List<Marker> quakeMarkers;

    // A List of country markers
    private List<Marker> countryMarkers;

    public void setup() {
        // (1) Initializing canvas and map tiles
        size(900, 700, OPENGL);
        if (offline) {
            map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
            earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
        } else {
            map = new UnfoldingMap(this, 200, 50, 650, 600, new OpenStreetMap.OpenStreetMapProvider());//Google.GoogleMapProvider());
            // IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
            //earthquakesURL = "2.5_week.atom";
        }
        MapUtils.createDefaultEventDispatcher(this, map);
        // FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
        // one of the lines below.  This will work whether you are online or offline
        //earthquakesURL = "test1.atom";
        //earthquakesURL = "test2.atom";
        // WHEN TAKING THIS QUIZ: Uncomment the next line
        //earthquakesURL = "quiz1.atom";
        // (2) Reading in earthquake data and geometric properties
        //     STEP 1: load country features and markers
        List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        //     STEP 2: read in city data
        List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
        cityMarkers = new ArrayList<Marker>();
        for (Feature city : cities) {
            cityMarkers.add(new CityMarker(city));
        }
        //     STEP 3: read in earthquake RSS feed
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        quakeMarkers = new ArrayList<Marker>();
        for (PointFeature feature : earthquakes) {
            //check if LandQuake
            if (isLand(feature)) {
                quakeMarkers.add(new LandQuakeMarker(feature));
            }
            // OceanQuakes
            else {
                quakeMarkers.add(new OceanQuakeMarker(feature));
            }
        }
        // could be used for debugging
        printQuakes();
        // (3) Add markers to map
        //     NOTE: Country markers are not added to the map.  They are used
        //           for their geometric properties
        map.addMarkers(quakeMarkers);
        map.addMarkers(cityMarkers);

    }  // End setup

    public void draw() {
        background(0);
        map.draw();
        addKey();

    }

    // helper method to draw key in GUI
    // TODO: Update this method as appropriate
    private void addKey() {
        // Remember you can use Processing's graphics methods here
        fill(255, 250, 240);
        rect(25, 50, 150, 300);
        fill(0);
        textAlign(LEFT, CENTER);
        textSize(14);
        text("Earthquake Key", 50, 75);
        int triangleSize = 15;                      // размер треуголника
        int triangleX1 = 50;                        // x1 - первая точка треугольника
        int triangleY1 = 110 + triangleSize / 2;    // y1 - первая точка треугольника
                                                    // y2 = y1;
        int dx2 = triangleSize;                     // x2 = x1+dx2
        float dx3 = dx2 / 2;                        // x3 = x1+dx3
        float dy3 = (float) (dx3 * Math.sqrt(3));    // y3 = y1-dy3
        final float X1txt = 75;                     // x1 текст "City Marker"
        final float Y1txt = 110;                    // y1 текст "City Marker"
        final float DYtxt = 30;                     // шаг текста
        fill(color(150,30,30));
        triangle(triangleX1, triangleY1, triangleX1 + dx2, triangleY1, triangleX1 + dx3, triangleY1 - dy3);
        fill(color(255, 255, 255));
        ellipse(triangleX1 + 7, triangleY1 + 25, 15, 15);
        fill(color(255, 255, 255));
        rect(triangleX1, triangleY1 + 48, 15, 15);
        fill(color(0, 255, 0));
        ellipse(triangleX1 + 7, triangleY1 + 115, 15, 15);
        fill(color(0, 0, 255));
        ellipse(triangleX1 + 7, triangleY1 + 145, 15, 15);
        fill(color(255, 0, 0));
        ellipse(triangleX1 + 7, triangleY1 + 175, 15, 15);
        noFill();
        ellipse(triangleX1 + 7, triangleY1 + 205, 15, 15);
        line(triangleX1,triangleY1+200,triangleX1+14,triangleY1+210);
        line(triangleX1+14,triangleY1+200,triangleX1,triangleY1+210);


        fill(0, 0, 0);
        text("City Marker", X1txt, Y1txt);
        text("Land Quake", X1txt, Y1txt + DYtxt);
        text("Ocean Quake", X1txt, Y1txt + 2 * DYtxt);
        text("Size ~ Magnitude", X1txt - 25, Y1txt + 3 * DYtxt);
        text("Shallow", X1txt, Y1txt + 4 * DYtxt);
        text("Intermediate", X1txt, Y1txt + 5* DYtxt);
        text("Deep", X1txt, Y1txt + 6 * DYtxt);
        text("Past month", X1txt, Y1txt + 7 * DYtxt);
    }


    // Checks whether this quake occurred on land.  If it did, it sets the
    // "country" property of its PointFeature to the country where it occurred
    // and returns true.  Notice that the helper method isInCountry will
    // set this "country" property already.  Otherwise it returns false.
    private boolean isLand(PointFeature earthquake) {
//		 Loop over all the country markers.
//		 For each, check if the earthquake PointFeature is in the
//		 country in m.  Notice that isInCountry takes a PointFeature
//		 and a Marker as input.
//		 If isInCountry ever returns true, isLand should return true.
        for (Marker m : countryMarkers) {
            if (isInCountry(earthquake, m)) return true;
            // TODO: Finish this method using the helper method isInCountry
        }
        // not inside any country
        return false;
    }

    /* prints countries with number of earthquakes as
     * Country1: numQuakes1
     * Country2: numQuakes2
     * ...
     * OCEAN QUAKES: numOceanQuakes
     * */
    private void printQuakes() {
        // TODO: Implement this method
        // One (inefficient but correct) approach is to:
        //   Loop over all of the countries, e.g. using
        //        for (Marker cm : countryMarkers) { ... }
        //
        //      Inside the loop, first initialize a quake counter.
        //      Then loop through all of the earthquake
        //      markers and check to see whether (1) that marker is on land
        //     	and (2) if it is on land, that its country property matches
        //      the name property of the country marker.   If so, increment
        //      the country's counter.
        // Here is some code you will find useful:
        //
        //  * To get the name of a country from a country marker in variable cm, use:
        //     String name = (String)cm.getProperty("name");
        //  * If you have a reference to a Marker m, but you know the underlying object
        //    is an EarthquakeMarker, you can cast it:
        //       EarthquakeMarker em = (EarthquakeMarker)m;
        //    Then em can access the methods of the EarthquakeMarker class
        //       (e.g. isOnLand)
        //  * If you know your Marker, m, is a LandQuakeMarker, then it has a "country"
        //      property set.  You can get the country with:
        //        String country = (String)m.getProperty("country");
        Map<String, Integer> map = new TreeMap<>();
        int oceanQuakes = 0;
        for (Marker m : quakeMarkers) {
            System.out.println(m.getProperty("title"));
            String country = (String) m.getProperty("country");
            if (country != null) map.put(country, map.get(country) == null ? 1 : map.get(country) + 1);
            else ++oceanQuakes;
        }
        for (Map.Entry s : map.entrySet()) System.out.println(s.getKey() + ": " + s.getValue());

        System.out.println("OCEAN QUAKES: " + oceanQuakes);

    }

    // helper method to test whether a given earthquake is in a given country
    // This will also add the country property to the properties of the earthquake
    // feature if it's in one of the countries.
    // You should not have to modify this code
    private boolean isInCountry(PointFeature earthquake, Marker country) {
        // getting location of feature
        Location checkLoc = earthquake.getLocation();
        // some countries represented it as MultiMarker
        // looping over SimplePolygonMarkers which make them up to use isInsideByLoc
        if (country.getClass() == MultiMarker.class) {
            // looping over markers making up MultiMarker
            for (Marker marker : ((MultiMarker) country).getMarkers()) {
                // checking if inside
                if (((AbstractShapeMarker) marker).isInsideByLocation(checkLoc)) {
                    earthquake.addProperty("country", country.getProperty("name"));
                    // return if is inside one
                    return true;
                }
            }
        }
        // check if inside country represented by SimplePolygonMarker
        else if (((AbstractShapeMarker) country).isInsideByLocation(checkLoc)) {
            earthquake.addProperty("country", country.getProperty("name"));
            return true;
        }
        return false;
    }

}
