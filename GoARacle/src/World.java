import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class World implements Comparable<World>{
	String name; //Name of world/location of items
	ArrayList<String> locations; //list of each world's locations
	ArrayList<Pool> pools;

	int[] poolItems; //number of items in each pool
	
	int priorityScore;
	int foundScore;
	int highestPriorityIndex;
	
	public World(String name, String[] locs) {
		locations=new ArrayList<String>();
		for(String curr:locs)
			locations.add(curr);
		
		priorityScore=0;
		foundScore=0;
		highestPriorityIndex=-1;
		
		this.name=name;
	}//end constructor
	

	
	public void updatePriority(boolean hidePoL) {
		int highestPriorityNum=0;
		int hpi=0;
		
		ArrayList<Integer> randomizeEqual=new ArrayList<>(); 
		
		for(int i=0; i<poolItems.length; i++) {
			priorityScore+=poolItems[i]*pools.get(i).priority;
			if(poolItems[i]*pools.get(i).priority>highestPriorityNum) {
				if(hidePoL&&pools.get(i).name.equals("Path of Light"))
					continue;
				highestPriorityNum=poolItems[i]*pools.get(i).priority;
				hpi=i;
				randomizeEqual.clear();
				randomizeEqual.add(hpi);
			}
			else if(poolItems[i]*pools.get(i).priority==highestPriorityNum) {
				randomizeEqual.add(i);
			}
		}
		
		if(randomizeEqual.size()>1) {
			Collections.shuffle(randomizeEqual,new Random(13));
			highestPriorityIndex=randomizeEqual.get(0);
		}
		
		else highestPriorityIndex=hpi;
	}
	
	public String getReportInfo(ArrayList<Integer> checklist, int index) {
		
		if(this.priorityScore==0) {
			return this.name+" has nothing but shadows.";
		}
		
		String output="";	
		String temp="";
		
		
		if(pools.get(highestPriorityIndex).name.equals("Path of Light")) {
			temp="The Path of Light leads ";
		}

		else if(pools.get(highestPriorityIndex).name.equals("Reports")) {
			temp="DiZ left hints ";
		}
		else if(pools.get(highestPriorityIndex).name.contains("'s Choice")) {
			temp=pools.get(highestPriorityIndex).name.substring(0,pools.get(highestPriorityIndex).name.length()-9);
			temp+=" wants you to look ";
		}
		
		output+=temp;
		
		if(this.name.equals("Levels")) {
			if(pools.get(highestPriorityIndex).name.equals("Path of Light"))
				output+="to ";
			else output+="within ";
			output+="your heart.";
		}
		else if(this.name.equals("Forms")) {
			if(pools.get(highestPriorityIndex).name.equals("Path of Light"))
				output+="to ";
			else output+="in ";
			output+="forms.";
		}
		else {
			if(pools.get(highestPriorityIndex).name.equals("Path of Light"))
				output+="to ";
			else output+="in ";
			
			if(this.name.equals("Simulated Twilight Town"))
				output+="Sim. Twilight Town";
			else if(this.name.equals("Disney Castle and Timeless River"))
				output+="Disney Castle and TR";
			else output+=this.name;
		}
		
		int tempScore=0;
		foundScore=0;
		
		for(int i=0; i<checklist.size(); i++) {
			tempScore+=checklist.get(i)*pools.get(i).priority;
			foundScore+=checklist.get(i)*pools.get(i).priority;
		}
		
		output+=" - Priority Score "+tempScore+"/"+priorityScore+" ("+((tempScore*100)/priorityScore)+"%)";
		
		
		return output;

			
	}//end getReportInfo
	
	public int compareTo(World world) {
		
		if(this.priorityScore==world.priorityScore)
			return 0;
		else if(this.priorityScore>world.priorityScore)
			return -1;
		else return 1;
		
	}
	
}
