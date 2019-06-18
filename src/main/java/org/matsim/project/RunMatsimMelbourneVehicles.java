package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.scenario.ScenarioUtils;

public class RunMatsimMelbourneVehicles {
	public static void main( String[] args ){

		if ( args.length==0 ) {
			args = new String [] { "scenarios/melbourne_vehicles/config-with-mode-vehicles.xml" } ;
		} else {
			Gbl.assertIf( args[0] != null && !args[0].equals( "" ) );
		}

		Config config = ConfigUtils.loadConfig( args ) ;
		
		config.plansCalcRoute().removeModeRoutingParams( TransportMode.walk );
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;
		
		Controler controler = new Controler( scenario ) ;
		
		
		controler.run();
	}
}
