import java.util.*;//For ArrayList
import java.awt.*;
import java.awt.event.*;
import java.io.*; //For BufferedReader and FileReader
import javax.swing.*; //For file selection
import javax.swing.filechooser.*; //For only showing PNACH files on file choice
import javax.swing.plaf.metal.MetalToggleButtonUI;

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



public class GoARacle extends JPanel implements ActionListener, MouseListener{
	
	static ArrayList<String> locationCodes; //All locations
	static ArrayList<String> poolCodes; //All pool items
	static ArrayList<World> worlds; //All locations by world
	static ArrayList<Pool> pools; //All pool items by pool
	static Report[] reports;
	
	
	JFrame frame;
	
	JButton pnachButton;
	JPanel pnachPanel;
	
	JToggleButton[] hintButtons;
	JPanel hintPanel;
	
	public GoARacle() throws FileNotFoundException {
		
		locationCodes=new ArrayList<String>();
		poolCodes=new ArrayList<String>();
		reports=new Report[13];
		for(int i=0; i<13; i++) {
			reports[i]=new Report(i);
		}
		worlds=generateWorlds("Locations.txt");
		pools=generatePools("Pools.txt");
		
		
		
		frame=new JFrame("GoARacle");
		frame.add(this);
		frame.setSize(1000,1250);
		
		
		pnachButton=new JButton();
		pnachButton.addActionListener(this);
		pnachButton.setPreferredSize(new Dimension(10,93));
		pnachButton.setText("Select Seed");
		pnachButton.setFont(new Font("Arial", Font.ITALIC, 50));
		
		pnachPanel=new JPanel();
		pnachPanel.setLayout(new BorderLayout());
		pnachPanel.add(pnachButton,BorderLayout.NORTH);
		
		frame.add(pnachPanel,BorderLayout.NORTH);
		
		hintPanel=new JPanel();
		hintPanel.setLayout(new GridLayout(13,1));
		
		hintButtons=new JToggleButton[13];
		
		for(int i=0; i<13; i++) {
			hintButtons[i]=new JToggleButton();
			hintButtons[i].addMouseListener(this);
			hintButtons[i].setPreferredSize(new Dimension(1,1));
			hintButtons[i].setBackground(Color.WHITE);
			hintButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			hintButtons[i].setText("Secret Ansem Report #"+(i+1));
			hintButtons[i].setFont(new Font("Arial",Font.PLAIN,30));
			hintPanel.add(hintButtons[i]);
		}
		
		
		frame.add(hintPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}//end GoARacle
	
	/*
	 * 	locationCodes=new ArrayList<>();
		poolCodes=new ArrayList<>();
	
		pools=generatePools("Pools.txt");
		worlds=generateWorlds("Locations.txt");
		reports=new Report[13];
		for(int i=0; i<13; i++) {
			reports[i]=new Report(i+1);
		}
		
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
		
	 */
	
	public static void main(String[] args) throws IOException {
		GoARacle goARacle=new GoARacle();
	}//end main method
	

	
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
				
				for(World world:worlds) {
					world.pools=output;
					world.poolItems=new int[output.size()];
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
		jfc.setDialogTitle("Select Seed File");
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		//jfc.addChoosableFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		jfc.setFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		
		if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			
			
			if(!jfc.getSelectedFile().getName().contains("pnach")) {
				readPnach();
				return;
			}
				
			
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
		}
		
		}catch(IOException io) {
			throw new IOException("Problem reading pnach file.");
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==pnachButton) {
			try {
				readPnach();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}//end pnachButton
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		for(int i=0; i<13; i++) {
			if(e.getSource()==hintButtons[i]) {
				
			}//end source if
		}//end outer for loop
	}//end mouseReleased

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}//end GoARacle class
