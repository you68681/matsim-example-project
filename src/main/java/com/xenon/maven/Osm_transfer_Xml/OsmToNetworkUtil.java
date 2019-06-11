package com.xenon.maven.Osm_transfer_Xml;
 
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.io.OsmNetworkReader;
import org.matsim.core.network.algorithms.NetworkCleaner;
import java.io.UncheckedIOException;
import java.util.Scanner;

import com.xenon.maven.Osm_transfer_Xml.WGS84toMercator;
 
public class OsmToNetworkUtil {
	public static void main(String[] args){
/*		
		Scanner in =new Scanner(System.in);
		
		System.out.println("input map file path");
		String dir = in.nextLine();
		System.out.println("input osm file");
		String osmFile = in.nextLine();
		System.out.println("input xml file");
		String networkFile = in.nextLine();

*/		
		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.loadScenario(config);
		CoordinateTransformation trans = new WGS84toMercator();
		OsmNetworkReader osmReader = new OsmNetworkReader(scenario.getNetwork(), trans);
		String dir = "./Map/";			// put the input .osm map in this path
		String osmFile = "melbourne.osm";				// input .osm map
		String networkFile = "melbourne.xml";		// output .xml network
		try{
			osmReader.parse(dir+osmFile);
			new NetworkCleaner().run(scenario.getNetwork());
			new NetworkWriter(scenario.getNetwork()).write(dir+networkFile);
			System.out.println("Done writing!");
			System.out.println("Please find network file in "+dir+networkFile);
		}
			catch(UncheckedIOException e){
			e.toString();
		}
	}
}
