package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.gbl.Gbl;
import org.matsim.core.scenario.ScenarioUtils;

class RunMatsimMelbournePlan{

	public static void main( String[] args ){

		if ( args.length==0 ) {
			args = new String [] { "scenarios/melbourne/config.xml" } ;
		} else {
			Gbl.assertIf( args[0] != null && !args[0].equals( "" ) );
		}

		Config config = ConfigUtils.loadConfig( args ) ;
		
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;
		
		Controler controler = new Controler( scenario ) ;
		
		
		controler.run();
	}

}
