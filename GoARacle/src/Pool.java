import java.util.ArrayList;

public class Pool {
		
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

