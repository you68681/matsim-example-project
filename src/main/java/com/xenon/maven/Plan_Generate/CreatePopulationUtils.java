package com.xenon.maven.Plan_Generate;
 
import java.util.Random;
import java.util.Scanner;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.population.io.PopulationWriter;
 
public class CreatePopulationUtils {
 
	public static Coord coordSW;		// (Minimum x, y)
	public static Coord coordNE;		// (Maximum x, y)
	
	private static void setMaxMinCoord(Network network){
		// set boundaries coordSW, coordNE
		for (Id<Node> nodeId : network.getNodes().keySet()){
			Coord nodeCoord = network.getNodes().get(nodeId).getCoord();
			if(coordSW == null||coordNE == null){
				coordSW = nodeCoord;
				coordNE = nodeCoord;
				continue;
			}
			coordSW = new Coord(coordSW.getX()<nodeCoord.getX()?coordSW.getX():nodeCoord.getX(), 
			coordSW.getY()<nodeCoord.getY()?coordSW.getY():nodeCoord.getY());
			coordNE = new Coord(coordNE.getX()>nodeCoord.getX()?coordNE.getX():nodeCoord.getX(), 
			coordNE.getY()>nodeCoord.getY()?coordNE.getY():nodeCoord.getY());
		}
	}
	
	private static Coord getRandomCoord(){
		// get random coordinate
		double x, y;
		x = coordSW.getX()+(coordNE.getX() - coordSW.getX())*Math.random();
		y = coordSW.getY()+(coordNE.getY() - coordSW.getY())*Math.random();
		return new Coord(x, y);
	
	}
	
	public static void main(String[] args){
		
		Config config = ConfigUtils.createConfig();
		Scenario scenario = ScenarioUtils.createScenario(config);
/*		
		Scanner in =new Scanner(System.in);
		
		System.out.println("input map file path");
		String dir = in.nextLine();
		System.out.println("input plan output file path and name");
		String filePath = in.nextLine();
*/		
		// input network.xml path
		new MatsimNetworkReader(scenario.getNetwork()).readFile("./original-input-data/Map/melbourne.xml");
		
//		new MatsimNetworkReader(scenario.getNetwork()).readFile(dir);
		setMaxMinCoord(scenario.getNetwork());
		fillScenario(scenario);
		
		// plans.xml output file path 
		String filePath = "./original-input-data/Plans/new_melbourne_500.xml";
		new PopulationWriter(scenario.getPopulation()).write(filePath);
		System.out.println("Done writing file to"+filePath);
	}
	
	
	private static Population fillScenario(Scenario scenario) {
		Population population = scenario.getPopulation();
		
		// create 500 agent
		for (int i = 0; i < 500; i++) {
			Coord coord = getRandomCoord();
			Coord coordWork = getRandomCoord();
			createOnePerson(scenario, population, i, coord, coordWork);
		}
		
		return population;
	}
	
	private static void createOnePerson(Scenario scenario, Population population, int i, Coord coord, Coord coordWork) {
	
		Person person = population.getFactory().createPerson(Id.createPersonId("p_"+i));
		
		Plan plan = population.getFactory().createPlan();
		
		
		
		Activity home = population.getFactory().createActivityFromCoord("home", coord);
		home.setEndTime(randomTime(8*60*60, 60*60));
		
		plan.addActivity(home);
		
		Boolean flag=rand();
		System.out.println(flag);
		if (flag) {
			Leg hinweg = population.getFactory().createLeg("car");
			plan.addLeg(hinweg);
		}else {
			Leg hinweg = population.getFactory().createLeg("walk");
			plan.addLeg(hinweg);
		}
		
		Activity work = population.getFactory().createActivityFromCoord("work", coordWork);
		work.setEndTime(randomTime(19*60*60, 3*60*60));
		plan.addActivity(work);
		
		flag=rand();
		if (flag) {
			Leg hinweg = population.getFactory().createLeg("car");
			plan.addLeg(hinweg);
		}else {
			Leg hinweg = population.getFactory().createLeg("walk");
			plan.addLeg(hinweg);
		}
		
		Activity home2 = population.getFactory().createActivityFromCoord("home", coord);
		plan.addActivity(home2);
		
		person.addPlan(plan);
		population.addPerson(person);
	}
	
	public static Boolean rand() {
		
		   Random r = new Random();
	       int n5 = r.nextInt(10);
	       if(n5 < 3){ 
	           return true;
	        }else {
	          return false;
	        }
		
	}
	
	private static int randomTime(int normalTime, int variance){
		// generating random time in accordance with Gauss Distribution
		Random rand = new Random();
		return (int)(variance*rand.nextGaussian()+normalTime);
	}
}
