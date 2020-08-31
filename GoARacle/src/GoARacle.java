import java.util.*;//For ArrayList
import java.awt.*;
import java.awt.event.*;
import java.io.*; //For BufferedReader and FileReader
import javax.swing.*; //For file selection
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*; //For only showing PNACH files on file choice


public class GoARacle extends JPanel implements ActionListener, MouseListener, ChangeListener{
	
	static ArrayList<String> locationCodes; //All locations
	static ArrayList<String> poolCodes; //All pool items
	static ArrayList<World> worlds; //All locations by world
	static ArrayList<Pool> pools; //All pool items by pool
	static Report[] reports;
	static ArrayList<World> worldOrder;
	static int[] attempts;
	
	static ArrayList<ArrayList<Integer>> checklists;
	static ArrayList<ArrayList<JSpinner>> spinners;
	
	JFrame frame;
	
	JButton pnachButton;
	JButton poolButton;
	JPanel topPanel;
	JButton aboutButton;
	JButton markButton;
	JPanel botPanel;
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
		worlds=generateWorlds();
		pools=generatePools();
		
		attempts=new int[worlds.size()-1];
		for(int i=0; i<attempts.length; i++) {
			attempts[i]=0;
		}
		
		checklists=new ArrayList<ArrayList<Integer>>();
		spinners=new ArrayList<ArrayList<JSpinner>>();
		
		for(int i=0; i<worlds.size()-1; i++) {
			checklists.add(new ArrayList<Integer>());
			spinners.add(new ArrayList<JSpinner>());
			for(int p=0; p<pools.size(); p++) {
				checklists.get(i).add(0);
				spinners.get(i).add(new JSpinner());
			}
		}
		
		frame=new JFrame("GoARacle Beta by CrescentRR");
		frame.add(this);
		frame.setSize(1000,1250);
		
		
		pnachButton=new JButton();
		pnachButton.addActionListener(this);
		pnachButton.setPreferredSize(new Dimension(10,46));
		pnachButton.setText("Select Seed");
		pnachButton.setFont(new Font("Arial", Font.ITALIC, 25));
		
		poolButton=new JButton();
		poolButton.addActionListener(this);
		poolButton.setPreferredSize(new Dimension(10,46));
		poolButton.setText("Coming Soon...");
		poolButton.setFont(new Font("Arial", Font.ITALIC, 25));
		poolButton.setEnabled(false); //REMOVE THIS WHEN IMPLEMENTING
		
		topPanel=new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(pnachButton,BorderLayout.NORTH);
		topPanel.add(poolButton,BorderLayout.CENTER);
		
		aboutButton=new JButton();
		aboutButton.addActionListener(this);
		aboutButton.setPreferredSize(new Dimension(10,46));
		aboutButton.setText("About");
		aboutButton.setFont(new Font("Arial", Font.ITALIC, 25));
		
		markButton=new JButton();
		markButton.addActionListener(this);
		markButton.setPreferredSize(new Dimension(10,46));
		markButton.setText("Mark Off");
		markButton.setFont(new Font("Arial", Font.ITALIC, 25));
		markButton.setEnabled(false);
		
		botPanel=new JPanel();
		botPanel.setLayout(new BorderLayout());
		botPanel.add(aboutButton, BorderLayout.NORTH);
		botPanel.add(markButton, BorderLayout.CENTER);
		
		pnachPanel=new JPanel();
		pnachPanel.setLayout(new BorderLayout());
		/*pnachPanel.add(pnachButton,BorderLayout.SOUTH);
		pnachPanel.add(aboutButton,BorderLayout.CENTER);*/
		pnachPanel.add(topPanel,BorderLayout.NORTH);
		pnachPanel.add(botPanel,BorderLayout.CENTER);
		
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
			hintButtons[i].setFont(new Font("Arial",Font.PLAIN,20));
			
			hintButtons[i].setEnabled(false);
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
	
	public static ArrayList<World> generateWorlds() throws FileNotFoundException{
		
		//Generates check locations from Locations.txt, saving it within an ArrayList of Worlds
		
		
		ArrayList<World> output=new ArrayList<>();
		
		try {
			BufferedReader input=new BufferedReader(new FileReader("resources/Locations.txt"));
			
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
	
	public static ArrayList<Pool> generatePools() throws FileNotFoundException{
		
		ArrayList<Pool> output=new ArrayList<>();
		
		try {
			
			BufferedReader input=new BufferedReader(new FileReader("resources/Pools.txt"));
			
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
	
	public void readPnach() throws IOException{
		
		try {
		
	
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("Select Seed File");
		jfc.setCurrentDirectory(new File(System.getProperty("user.home")+"/Desktop"));
		
		//jfc.addChoosableFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		jfc.setFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		
		if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			
			
			if(!jfc.getSelectedFile().getName().contains("pnach")) {
				readPnach();
				return;
			}
			

			
			
			for(int i=0; i<13; i++) {
				hintButtons[i].setEnabled(true);
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
			pnachButton.setEnabled(false);
			markButton.setEnabled(true);
		}//end of OpenDialog if
		
		for(World world:worlds) {
			//world.fixLevel();
			world.updatePriority();
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
			
			/*for(World world:worlds) {
				System.out.print(world.name+": ");
				for(int pool:world.poolItems)
					System.out.print(pool+" ");
				System.out.println();
			}*/
			
			
			
			
			worldOrder=new ArrayList<World>();
			
			for(World world:worlds)
				worldOrder.add(world);
			
			worldOrder.remove(worldOrder.size()-1);
			
			int index;
			int highest;
			World temp;
			for(int i=0; i<worldOrder.size()-1; i++) {
				index=i;
				highest=worldOrder.get(index).priorityScore;
				for(int p=i+1; p<worldOrder.size(); p++) {
					if(worldOrder.get(p).priorityScore>highest) {
						index=p;
						highest=worldOrder.get(p).priorityScore;
					}
				}
				temp=worldOrder.get(i);
				worldOrder.set(i, worldOrder.get(index));
				worldOrder.set(index, temp);
			}
			
			
		}//end pnachButton
		
		else if(e.getSource()==aboutButton) {
			JDialog aboutBox=new JDialog(frame, "About");
			aboutBox.setSize(500,500);
			
			String text="";
			String line="";
			try {
				BufferedReader file=new BufferedReader(new FileReader("resources/Pool Meaning.txt"));
				
				while((line=file.readLine())!=null) {
					text+=line+"\n";
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			JTextArea aboutBlurb=new JTextArea(text);
			JScrollPane scroll=new JScrollPane(aboutBlurb, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			aboutBlurb.setEditable(false);
			aboutBlurb.setFont(new Font("Arial",Font.PLAIN,20));
			aboutBox.add(scroll);
			aboutBox.setVisible(true);
		}//end aboutButton
		
		else if(e.getSource()==markButton) {
			
			String[] allWorlds=new String[worlds.size()-1];
			
			for(int i=0; i<allWorlds.length; i++) {
				allWorlds[i]=worlds.get(i).name;
			}
			
			String answer=(String) JOptionPane.showInputDialog(null, "What world are you checking?", //Creates pop-up window for location check
					"World Check-Off",JOptionPane.QUESTION_MESSAGE,null, allWorlds, allWorlds[0]);
			
			JDialog spinnerBox = null; 
			
			/*
			 * TO DO:
			 * Make spinners fit in box
			 * Make labels
			 * Other shit
			 * 
			 * 
			 */
			
			for(int w=0; w<worlds.size()-1; w++) {
				if(answer.equals(worlds.get(w).name)) {
					spinnerBox=new JDialog(frame, answer+" Checklist");
					spinnerBox.setSize(500, 100*pools.size());
					spinnerBox.setLayout(new GridLayout(checklists.get(0).size(),2,3,3));
					for(int p=0; p<checklists.get(w).size(); p++) {
						int min=0;
						int max=30;
						SpinnerNumberModel snm=new SpinnerNumberModel();
						snm.setValue(checklists.get(w).get(p));
						snm.setMinimum(0);
						snm.setMaximum(30);
						snm.setStepSize(1);
						spinners.get(w).set(p,addLabeledSpinner(pools.get(p).name,snm));
						spinners.get(w).get(p).addChangeListener(this);
						JLabel label=new JLabel(pools.get(p).name);
						spinnerBox.add(label);
						spinnerBox.add(spinners.get(w).get(p));
					}
				}
				
			}
			
			
			
			spinnerBox.setVisible(true);
			
		
			
		}//end markButton
		
	}

	private JSpinner addLabeledSpinner(String name, SpinnerNumberModel spinnerNumberModel) {
		// TODO Auto-generated method stub
		
		JLabel label=new JLabel(name);
		//goARacle.add(label);
		JSpinner spinner=new JSpinner(spinnerNumberModel);
		label.setLabelFor(spinner);
		//goARacle.add(spinner);
		
		return spinner;
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
			if(e.getSource()==hintButtons[i]&&hintButtons[i].isEnabled()&&attempts[i]!=3) {
				
				String[] choices=new String[worlds.size()];
				
				String correctAnswer=reports[i].loc;
				
				for(int p=0; p<choices.length; p++) {
					choices[p]=worlds.get(p).name;
				}
				
				String answer=(String) JOptionPane.showInputDialog(null, "Where did you find Report #"+(i+1)+"?", //Creates pop-up window for location check
						"Report #"+(i+1)+"Check: Attempt #"+(attempts[i]+1)+"/3",JOptionPane.QUESTION_MESSAGE,null, choices, choices[0]);
				
				if(answer.equals(correctAnswer)) {
				
				hintButtons[i].setText(worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i)))));
				hintButtons[i].setEnabled(false);
				}//end check for correct answer
				else {
					attempts[i]++;
				}
			}//end source if
		}//end outer for loop
		frame.revalidate();
	}//end mouseReleased

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
		for(int w=0; w<spinners.size(); w++) {
			for(int p=0; p<spinners.get(w).size(); p++) {
				if(e.getSource()==spinners.get(w).get(p)) {
					checklists.get(w).set(p,(int)spinners.get(w).get(p).getValue());
					System.out.println(worlds.get(w).name+" "+pools.get(p).name+" "+checklists.get(w).get(p));
				}
			}
		}
		
		for(int i=0; i<hintButtons.length; i++) {
			if(!hintButtons[i].isEnabled()&&attempts[i]!=3) {
				hintButtons[i].setText(worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i)))));
			}
		}
		
		frame.revalidate();
	}
	
}//end GoARacle class



//Code for Checklist, needs to be reworked
/*
 * 			int index=0;
			int i=0;
			int p=0;

 */


