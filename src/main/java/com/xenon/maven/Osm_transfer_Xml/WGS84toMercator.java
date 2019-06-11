package com.xenon.maven.Osm_transfer_Xml;
 
import org.matsim.api.core.v01.Coord;
import org.matsim.core.utils.geometry.CoordinateTransformation;
 
public class WGS84toMercator implements CoordinateTransformation {
 
	@Override
	public Coord transform(Coord coord) {
		double x = coord.getX() *20037508.342789/180;
		double y = Math.log(Math.tan((90+coord.getY())*Math.PI/360))/(Math.PI/180);
		y = y *20037508.34789/180;
		return new Coord(x, y);
	}
}