import java.util.*; //For ArrayList
import java.io.*; //For BufferedReader and FileReader
import javax.swing.*; //For file selection

/*
 * To Do:
 * 
 * -Discuss balance
 * -Develop hint checking system
 * 
 * -Improvements:
 * 		Make case in readPnach for user not selecting their pnach file
 * 
 */



public class GoARacle {
	
	static ArrayList<String> locationCodes; //All locations
	static ArrayList<String> poolCodes; //All pool items
	static ArrayList<World> worlds; //All locations by world
	static ArrayList<Pool> pools; //All pool items by pool
	static Report[] reports;
	
	public static void main(String[] args) throws IOException {
		
		locationCodes=new ArrayList<>();
		poolCodes=new ArrayList<>();
	
		pools=generatePools("Pools.txt");
		worlds=generateWorlds("Locations.txt");
		reports=new Report[13];
		for(int i=0; i<13; i++) {
			reports[i]=new Report(i+1);
		}
		
	
	
	/*for(World curr:worlds) {
		System.out.println(curr.name+": "+curr.locations.size());
	}
	System.out.println();
	
	for(Pool curr:pools) {
		System.out.println(curr.name+": "+curr.items.size());
	}*/
	
	readPnach();
	
	for(World world:worlds) {
		System.out.print(world.name+": ");
		for(int pool:world.poolItems)
			System.out.print(pool+" ");
		System.out.println();
	}
	System.out.println();
	
	for(World world:worlds)
		world.getReportInfo();
		
	}//end main method
	
	public static class World{
		
		String name; //Name of world/location of items
		ArrayList<String> locations; //list of each world's locations
		

		int[] poolItems; //number of items in each pool
		
		ArrayList<Integer> priorityScore;
		
		public World(String name, String[] locs) {
			locations=new ArrayList<String>();
			for(String curr:locs)
				locations.add(curr);
			poolItems=new int[pools.size()];
			for(int i=0; i<poolItems.length; i++)
				poolItems[i]=0;
			
			priorityScore=new ArrayList<>();
			
			for(int i=0; i<poolItems.length; i++) {
				priorityScore.add(pools.get(i).priority*poolItems[i]);
			}
			
			this.name=name;
		}//end constructor
		
		public void fixLevel() {
			if(this.name.equals("Levels"))
				for(int i=0; i<poolItems.length; i++)
					poolItems[i]/=3;
		}
		
		public void updatePriority() {
			for(int i=0; i<priorityScore.size(); i++)
				priorityScore.set(i, pools.get(i).priority*poolItems[i]);
		}
		
		public void getReportInfo() {
			
			
			int index=0;
			
			for(int i=1; i<priorityScore.size(); i++) {
				if(priorityScore.get(i)>priorityScore.get(index))
					index=i;
			}
			
			System.out.print(pools.get(index).name+" can be found ");
			
			if(this.name.equals("Levels"))
				System.out.println("from within.");
			else if(this.name.equals("Forms"))
				System.out.println("from extra strength.");
			else System.out.println("in "+this.name+".");
				
		}
		
	}//end World class
	
	public static class Pool{
		
		String name;
		ArrayList<String> items;
		int priority;
		
		public Pool(String name, String[] items, int priority) {
			this.items=new ArrayList<String>();
			for(String curr:items)
				this.items.add(curr);
			
			this.name=name;
			this.priority=priority;
		}//end constructor
		
	}//end Pool class
	
	
	
	public static class Report{
		
		int num; //Report number
		String loc; //location name (World name only, so far)
		
		public Report(int num) {
			this.num=num;
			loc="";
		}//end constructor
		
		
	}//end Report class
	
	public static ArrayList<World> generateWorlds(String locationFile) throws FileNotFoundException{
		
		//Generates check locations from Locations.txt, saving it within an ArrayList of Worlds
		
		
		ArrayList<World> output=new ArrayList<>();
		
		try {
			BufferedReader input=new BufferedReader(new FileReader(locationFile));
			
			String text="";
			String name="";
			ArrayList<String> locs=new ArrayList<>();
			
			
			//BufferedReader reads txt file line by line.
			
			while((text=input.readLine())!=null) { //declaration statement in while condition causes the file to read the next line as it checks
				
				
				
				if(!text.equals("")) {
					
					if(text.charAt(0)=='['||text.charAt(0)=='{') {
						if(!name.equals("")) {
							
							//convert ArrayList to String[]
							String[] temp=new String[locs.size()];
							for(int i=0; i<temp.length; i++)
								temp[i]=locs.get(i);
							output.add(new World(name,temp));
							locs.clear();
						}//end name inequality if
						name=text.substring(1, text.length()-1);
					}//end '[' if
					
					
					else {
						locs.add(text);
						locationCodes.add(text);
					}
					
				}//end normal reading if
				
			}
			
			
			
		}catch(IOException fnf) {
			throw new FileNotFoundException("File not found");
		}
		
		
		return output;
		
	}//end generateWorlds
	
	public static ArrayList<Pool> generatePools(String poolFile) throws FileNotFoundException{
		
		ArrayList<Pool> output=new ArrayList<>();
		
		try {
			
			BufferedReader input=new BufferedReader(new FileReader(poolFile));
			
			String text="";
			String name="";
			int priority=0;
			ArrayList<String> items=new ArrayList<>();
			
			while((text=input.readLine())!=null) {
				
				if(text.charAt(0)=='['||text.charAt(0)=='{') {
					if(!name.equals("")) {
						String[] temp=new String[items.size()];
						for(int i=0; i<temp.length; i++)
							temp[i]=items.get(i);
						output.add(new Pool(name, temp, priority));
					}
					name=text.substring(1,text.length()-1);
					priority=Integer.parseInt(input.readLine());
					items.clear();
				}
				else {
					items.add("0000"+text);
					poolCodes.add("0000"+text);
				}
				
			}//end while
			
		}catch(IOException fnf) {
			throw new FileNotFoundException("File not found");
		}
		
		return output;
		
	}//end generatePools
	
	public static void readPnach() throws IOException{
		
		try {
		
		
		
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("Select pnach");
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			BufferedReader input=new BufferedReader(new FileReader(jfc.getSelectedFile()));
			
			String text="";
			
			while((text=input.readLine())!=null) {
				String[] split=text.split(",");
				
				//System.out.println(split.length);
				if(split.length!=5)
					continue;
				else {
					//System.out.println(locationCodes.contains(split[2])+" "+poolCodes.contains(split[4]));
					if(locationCodes.contains(split[2])&&poolCodes.contains(split[4])) {
						for(World world:worlds) {
							//System.out.println(world.locations.contains(split[2]));
							if(world.locations.contains(split[2])) {
								for(Pool pool:pools) {
									//System.out.println(pool.items.contains(split[4]));
									if(pool.items.contains(split[4])) {
										world.poolItems[pools.indexOf(pool)]++;
										if(pool.name.equals("Reports")) {
											reports[pool.items.indexOf(split[4])].loc=world.name;
										}//end Reports check
									}
								}
							}//end world location check
						}//end world world world
					}//end locationCodes and poolCodes check
				}//end else
			}//end while
			
		}//end of OpenDialog if
		
		for(World world:worlds) {
			world.fixLevel();
			world.updatePriority();
		}
		
		}catch(IOException io) {
			throw new IOException("Problem reading pnach file.");
		}
		
	}
	
}//end GoARacle class


