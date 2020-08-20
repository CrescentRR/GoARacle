import java.util.ArrayList;

public class World {
	String name; //Name of world/location of items
	ArrayList<String> locations; //list of each world's locations
	ArrayList<Pool> pools;

	int[] poolItems; //number of items in each pool
	
	int[] priorityScore;
	
	public World(String name, String[] locs) {
		locations=new ArrayList<String>();
		for(String curr:locs)
			locations.add(curr);
		
		
		
		this.name=name;
	}//end constructor
	
	public void fixLevel() {
		if(this.name.equals("Levels"))
			for(int i=0; i<poolItems.length; i++)
				poolItems[i]/=3;
	}
	
	
	public void getReportInfo() {
		
		
		int index=0;
		

		
		System.out.print(pools.get(index).name+" can be found ");
		
		if(this.name.equals("Levels"))
			System.out.println("from within.");
		else if(this.name.equals("Forms"))
			System.out.println("from extra strength.");
		else System.out.println("in "+this.name+".");
			
	}
}
