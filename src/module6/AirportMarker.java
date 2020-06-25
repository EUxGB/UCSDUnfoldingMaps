package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * A class to represent AirportMarkers on a world map.
 *
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	public static int i =0;

	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());

	}

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 20, 20);




	}
	public String getName()
	{
		return getStringProperty("name");
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {

		// show rectangle with title
		// show routes

		String id = "ID : " + getId();
		//String name = getName() + getStringProperty("city");
		String name = getStringProperty("country") + " " + getStringProperty("city") + " " + getStringProperty("code");

		pg.pushStyle();
		pg.fill(255,255,255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x,y-8*radius,Math.max(pg.textWidth(id),pg.textWidth(name))+6,8*radius);
		pg.fill(0,0,0);
		pg.textAlign(PConstants.LEFT,PConstants.TOP);
		pg.text(id,x+3,y-7*radius);
		pg.text(name,x+3,y-4*radius);


		pg.popStyle();


		 // show rectangle with title

		// show routes


	}



}
