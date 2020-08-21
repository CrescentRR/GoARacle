import java.util.ArrayList;

public class World implements Comparable<World>{
	String name; //Name of world/location of items
	ArrayList<String> locations; //list of each world's locations
	ArrayList<Pool> pools;

	int[] poolItems; //number of items in each pool
	
	int priorityScore;
	int highestPriorityIndex;
	
	public World(String name, String[] locs) {
		locations=new ArrayList<String>();
		for(String curr:locs)
			locations.add(curr);
		
		priorityScore=0;
		highestPriorityIndex=-1;
		
		this.name=name;
	}//end constructor
	
	public void fixLevel() {
		if(this.name.equals("Levels"))
			for(int i=0; i<poolItems.length; i++)
				poolItems[i]/=3;
	}
	
	public void updatePriority() {
		int highestPriorityNum=0;
		int hpi=0;
		for(int i=0; i<poolItems.length; i++) {
			priorityScore+=poolItems[i]*pools.get(i).priority;
			if(poolItems[i]*pools.get(i).priority>highestPriorityNum) {
				highestPriorityNum=poolItems[i]*pools.get(i).priority;
				hpi=i;
			}
		}
		highestPriorityIndex=hpi;
	}
	
	public String getReportInfo() {
		
		if(this.priorityScore==0) {
			return this.name+" has nothing but shadows.";
		}
		
		String output="";	
		String temp="";
		if(pools.get(highestPriorityIndex).name.contains("'s Choice")) {
			temp=pools.get(highestPriorityIndex).name.substring(0,pools.get(highestPriorityIndex).name.length()-9);
			temp+=" recommends looking ";
		}
		else if(pools.get(highestPriorityIndex).name.equals("Reports")) {
			temp="Ansem the Wise left hints ";
		}
		else if(pools.get(highestPriorityIndex).name.equals("Path of Light")) {
			temp="The Path of Light leads ";
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
			output+="your newfound strength.";
		}
		else {
			if(pools.get(highestPriorityIndex).name.equals("Path of Light"))
				output+="to ";
			else output+="in ";
			output+=this.name;
		}
		
		output+=": Priority Score "+priorityScore;
		
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
