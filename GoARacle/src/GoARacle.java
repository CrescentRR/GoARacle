import java.util.*;//For ArrayList
import java.awt.*;
import java.awt.event.*;
import java.io.*; //For BufferedReader and FileReader
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*; //For file selection
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*; //For only showing PNACH files on file choice
import javax.swing.plaf.basic.BasicButtonUI;


public class GoARacle extends JPanel implements ActionListener, MouseListener, ChangeListener{
	
	
	static String title="GoARacle v1.6.0 by CrescentRR";
	
	boolean hidePoL;
	
	private static final long serialVersionUID = 1L;
	static ArrayList<String> locationCodes; //All locations
	static ArrayList<String> poolCodes; //All pool items
	static ArrayList<World> worlds; //All locations by world
	static ArrayList<Pool> pools; //All pool items by pool
	static Report[] reports;
	static ArrayList<World> worldOrder;
	static int[] attempts;
	
	static ArrayList<ArrayList<Integer>> checklists;
	static ArrayList<ArrayList<JSpinner>> spinners;
	
	static ArrayList<String> hoverPoolText;
	
	JFrame spinnerBox;
	JComboBox<String> spinnerWorld;
	JLabel spinnerPoints;
	ArrayList<JLabel> spinnerLabels;
	
	JTextArea popoutText;
	JFrame popoutFrame;
	
	ColorToggleButton[] copyButtons;
	ColorToggleButton copyButtonExtra;
	int lastUpdatedButton;
	
	
	JFrame frame;
	
	ColorToggleButton pnachButton;
	ColorToggleButton popoutButton;
	//JPanel topPanel;
	ColorToggleButton aboutButton;
	ColorToggleButton markButton;
	//JPanel botPanel;
	JPanel pnachPanel;
	
	ColorToggleButton[] hintButtons;
	JPanel hintPanel;
	
	JButton seedButton;
	JButton trackerButton;
	
	public GoARacle() throws FileNotFoundException {
		
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		locationCodes=new ArrayList<String>();
		poolCodes=new ArrayList<String>();
		reports=new Report[14];
		for(int i=0; i<14; i++) {
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
		
		String[] temp=new String[worlds.size()-1];
		for(int i=0; i<temp.length; i++)
			temp[i]=worlds.get(i).name;
		
		spinnerWorld=new JComboBox<String>(temp);
		spinnerWorld.addActionListener(this);
		spinnerWorld.setMaximumRowCount(20);
		spinnerWorld.setFont(new Font("Monospaced",Font.BOLD,17));
		
		spinnerLabels=new ArrayList<>();
		
		seedButton=new JButton("Make Seed");
		seedButton.setFocusPainted(false);
		seedButton.addActionListener(this);
		trackerButton=new JButton("Coming Soon...");
		trackerButton.addActionListener(this);
		
		//Remove when complete:
		trackerButton.setEnabled(false);
		trackerButton.setToolTipText("<html>Coming soon...</html>");
		
		
		spinnerPoints=new JLabel();
		try {
			//spinnerPoints.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			spinnerPoints.setFont(new Font("Monospaced",Font.BOLD,35));
			seedButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			trackerButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		spinnerPoints.addMouseListener(this);
		
		
		ToolTipManager.sharedInstance().setInitialDelay(100);
		hoverPoolText=new ArrayList<>();
		
		
		String line="";
		hoverPoolText.add("<html>");
		
		try {
			BufferedReader file=new BufferedReader(new FileReader("resources/Pool Meaning.txt"));
			
			while((line=file.readLine())!=null) {
				
				if(!line.equals(""))
				hoverPoolText.set(hoverPoolText.size()-1, hoverPoolText.get(hoverPoolText.size()-1)+line+"<br>");
				
				else {
					hoverPoolText.set(hoverPoolText.size()-1, hoverPoolText.get(hoverPoolText.size()-1)+"<html>");
					hoverPoolText.add("<html>");
				}
				
			}
			file.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		
		frame=new JFrame(title);
		frame.add(this);
		frame.setSize(935,900);
		frame.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
		
		
		pnachButton=new ColorToggleButton();
		pnachButton.setUI(new BasicButtonUI());
		pnachButton.addActionListener(this);
		pnachButton.setPreferredSize(new Dimension(10,46));
		pnachButton.setBackground(Color.decode("#2C2F33"));
		pnachButton.setForeground(Color.WHITE);
		pnachButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		pnachButton.setText("Select Seed");
		pnachButton.setFocusPainted(false);
		pnachButton.setToolTipText("<html>Read your pnach file to get started.</html>");
		
		
		
		popoutButton=new ColorToggleButton();
		popoutButton.setUI(new BasicButtonUI());
		popoutButton.addActionListener(this);
		popoutButton.setPreferredSize(new Dimension(10,46));
		popoutButton.setBackground(Color.decode("#2C2F33"));
		popoutButton.setForeground(Color.WHITE);
		popoutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		popoutButton.setText("Popout");
		popoutButton.setEnabled(true); //REMOVE THIS WHEN IMPLEMENTING
		popoutButton.setFocusPainted(false);
		popoutButton.setToolTipText("<html>Popout a window so your stream knows what's new.</html>");
		
		popoutFrame=new JFrame("Popout");
		popoutFrame.add(this);
		popoutFrame.setSize(935,100);
		popoutFrame.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
		
		lastUpdatedButton=-1;
		

		aboutButton=new ColorToggleButton();
		aboutButton.setUI(new BasicButtonUI());
		aboutButton.addActionListener(this);
		aboutButton.setPreferredSize(new Dimension(10,46));
		aboutButton.setBackground(Color.decode("#2C2F33"));
		aboutButton.setForeground(Color.WHITE);
		aboutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		aboutButton.setText("About");
		aboutButton.setFocusPainted(false);
		aboutButton.setToolTipText("<html>Read Me</html>");
		
		markButton=new ColorToggleButton();
		markButton.setUI(new BasicButtonUI());
		markButton.addActionListener(this);
		markButton.setPreferredSize(new Dimension(10,46));
		markButton.setBackground(Color.decode("#2C2F33"));
		markButton.setForeground(Color.WHITE);
		markButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		markButton.setText("Mark Off");
		markButton.setFocusPainted(false);
		markButton.setEnabled(false);
		markButton.setToolTipText("<html>Check off items you find.</html>");
		

		pnachPanel=new JPanel();
		pnachPanel.setLayout(new GridLayout(2,2,3,3));
		pnachPanel.add(pnachButton);
		pnachPanel.add(popoutButton);
		pnachPanel.add(aboutButton);
		pnachPanel.add(markButton);
		/*pnachPanel.add(pnachButton,BorderLayout.SOUTH);
		pnachPanel.add(aboutButton,BorderLayout.CENTER);*/
		//pnachPanel.add(topPanel,BorderLayout.NORTH);
		//pnachPanel.add(botPanel,BorderLayout.CENTER);
		
		frame.add(pnachPanel,BorderLayout.NORTH);
		
		hintPanel=new JPanel();
		hintPanel.setLayout(new GridLayout(14,1));
		
		hintButtons=new ColorToggleButton[14];
		copyButtons=new ColorToggleButton[14];
		copyButtonExtra=new ColorToggleButton();
		copyButtonExtra.setUI(new BasicButtonUI());
		copyButtonExtra.addMouseListener(this);
		copyButtonExtra.setPreferredSize(new Dimension(1,1));
		copyButtonExtra.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		copyButtonExtra.setFocusPainted(false);
		copyButtonExtra.setText(frame.getTitle());
		
		try {
			//copyButtonExtra.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,15));
			copyButtonExtra.setFont(new Font("Monospaced",Font.BOLD,20));
		for(int i=0; i<14; i++) {
			hintButtons[i]=new ColorToggleButton();
			hintButtons[i].setUI(new BasicButtonUI());
			hintButtons[i].addMouseListener(this);
			hintButtons[i].setPreferredSize(new Dimension(1,1));
			//hintButtons[i].setBackground(Color.WHITE);
			hintButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			hintButtons[i].setFocusPainted(false);
			
			copyButtons[i]=new ColorToggleButton();
			copyButtons[i].setUI(new BasicButtonUI());
			copyButtons[i].addMouseListener(this);
			copyButtons[i].setPreferredSize(new Dimension(935,100));
			//copyButtons[i].setBackground(Color.WHITE);
			copyButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			copyButtons[i].setFocusPainted(false);
			
			if(i==13) {
				hintButtons[i].setText("Garden of Assemblage Map");
				copyButtons[i].setText("Garden of Assemblage Map");
				hintButtons[i].setToolTipText("<html>Gives location of best unfound world with a proof's report.<br>If none, gives location of the best unfound world's report.</html>");
			}
			else {
				hintButtons[i].setText("Secret Ansem's Report #"+(i+1));
				copyButtons[i].setText("Secret Ansem's Report #"+(i+1));
			}
			//hintButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			//copyButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			hintButtons[i].setFont(new Font("Monospaced",Font.BOLD,19));
			copyButtons[i].setFont(new Font("Monospaced",Font.BOLD,18));
			hintButtons[i].setEnabled(false);
			hintPanel.add(hintButtons[i]);
			
			copyButtons[i].setEnabled(false);
		}
		
		
			pnachButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			popoutButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			aboutButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			markButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		
		frame.add(hintPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				popoutFrame.setLocationRelativeTo(frame);
			}
		});
		frame.setVisible(true);
		
		JOptionPane.showMessageDialog(frame, "<html>Welcome to "+title+"!<br>Hover over the buttons or labels to get more information and controls.</html>");
		
	}//end GoARacle
	
	public void reset() throws FileNotFoundException {
		locationCodes=new ArrayList<String>();
		poolCodes=new ArrayList<String>();
		reports=new Report[14];
		for(int i=0; i<14; i++) {
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
		
		String[] temp=new String[worlds.size()-1];
		for(int i=0; i<temp.length; i++)
			temp[i]=worlds.get(i).name;
		
		spinnerWorld=new JComboBox<String>(temp);
		spinnerWorld.addActionListener(this);
		spinnerWorld.setMaximumRowCount(20);
		spinnerWorld.setFont(new Font("Monospaced",Font.BOLD,17));
		
		spinnerLabels=new ArrayList<>();
		
		seedButton=new JButton("Make Seed");
		seedButton.setFocusPainted(false);
		seedButton.addActionListener(this);
		trackerButton=new JButton("Coming soon...");
		trackerButton.addActionListener(this);
		
		//Remove this when finished:
		trackerButton.setEnabled(false);
		trackerButton.setToolTipText("<html>Coming soon...</html>");
		
		spinnerPoints=new JLabel();
		try {
			//spinnerPoints.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			spinnerPoints.setFont(new Font("Monospaced",Font.BOLD,35));
			seedButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			trackerButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		spinnerPoints.addMouseListener(this);
		
		
		
		ToolTipManager.sharedInstance().setInitialDelay(100);
		hoverPoolText=new ArrayList<>();
		
		
		String line="";
		hoverPoolText.add("<html>");
		
		try {
			BufferedReader file=new BufferedReader(new FileReader("resources/Pool Meaning.txt"));
			
			while((line=file.readLine())!=null) {
				
				if(!line.equals(""))
				hoverPoolText.set(hoverPoolText.size()-1, hoverPoolText.get(hoverPoolText.size()-1)+line+"<br>");
				
				else {
					hoverPoolText.set(hoverPoolText.size()-1, hoverPoolText.get(hoverPoolText.size()-1)+"<html>");
					hoverPoolText.add("<html>");
				}
				
			}
			file.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.add(this);
		frame.setSize(935,900);
		frame.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
		
		
		pnachButton=new ColorToggleButton();
		pnachButton.setUI(new BasicButtonUI());
		pnachButton.addActionListener(this);
		pnachButton.setPreferredSize(new Dimension(10,46));
		pnachButton.setBackground(Color.decode("#2C2F33"));
		pnachButton.setForeground(Color.WHITE);
		pnachButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		pnachButton.setText("Select Seed");
		pnachButton.setFocusPainted(false);
		pnachButton.setToolTipText("<html>Read your pnach file to get started.</html>");
		
		
		
		popoutButton=new ColorToggleButton();
		popoutButton.setUI(new BasicButtonUI());
		popoutButton.addActionListener(this);
		popoutButton.setPreferredSize(new Dimension(10,46));
		popoutButton.setBackground(Color.decode("#2C2F33"));
		popoutButton.setForeground(Color.WHITE);
		popoutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		popoutButton.setText("Popout");
		popoutButton.setEnabled(true); //REMOVE THIS WHEN IMPLEMENTING
		popoutButton.setFocusPainted(false);
		popoutButton.setToolTipText("<html>Popout a window so your stream knows what's new.</html>");
		
		popoutFrame.getContentPane().removeAll();
		popoutFrame.add(this);
		popoutFrame.setSize(935,100);
		popoutFrame.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
		popoutFrame.add(copyButtonExtra);
		popoutFrame.repaint();
		
		
		
		lastUpdatedButton=-1;
		

		aboutButton=new ColorToggleButton();
		aboutButton.setUI(new BasicButtonUI());
		aboutButton.addActionListener(this);
		aboutButton.setPreferredSize(new Dimension(10,46));
		aboutButton.setBackground(Color.decode("#2C2F33"));
		aboutButton.setForeground(Color.WHITE);
		aboutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		aboutButton.setText("About");
		aboutButton.setFocusPainted(false);
		aboutButton.setToolTipText("<html>Read Me</html>");
		
		markButton=new ColorToggleButton();
		markButton.setUI(new BasicButtonUI());
		markButton.addActionListener(this);
		markButton.setPreferredSize(new Dimension(10,46));
		markButton.setBackground(Color.decode("#2C2F33"));
		markButton.setForeground(Color.WHITE);
		markButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		markButton.setText("Mark Off");
		markButton.setFocusPainted(false);
		markButton.setEnabled(false);
		markButton.setToolTipText("<html>Check off items you find.</html>");
		
		if(spinnerBox!=null) {
			if(spinnerBox.isVisible()) {
				spinnerBox.dispose();
				markButton.doClick();
			}
		}
		
		pnachPanel=new JPanel();
		pnachPanel.setLayout(new GridLayout(2,2,3,3));
		pnachPanel.add(pnachButton);
		pnachPanel.add(popoutButton);
		pnachPanel.add(aboutButton);
		pnachPanel.add(markButton);
		/*pnachPanel.add(pnachButton,BorderLayout.SOUTH);
		pnachPanel.add(aboutButton,BorderLayout.CENTER);*/
		//pnachPanel.add(topPanel,BorderLayout.NORTH);
		//pnachPanel.add(botPanel,BorderLayout.CENTER);
		
		frame.add(pnachPanel,BorderLayout.NORTH);
		
		hintPanel=new JPanel();
		hintPanel.setLayout(new GridLayout(14,1));
		
		hintButtons=new ColorToggleButton[14];
		copyButtons=new ColorToggleButton[14];
		copyButtonExtra=new ColorToggleButton();
		copyButtonExtra.setUI(new BasicButtonUI());
		copyButtonExtra.addMouseListener(this);
		copyButtonExtra.setPreferredSize(new Dimension(1,1));
		copyButtonExtra.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		copyButtonExtra.setFocusPainted(false);
		copyButtonExtra.setText(frame.getTitle().replace('.', '-'));
		
		try {
			//copyButtonExtra.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,15));
			copyButtonExtra.setFont(new Font("Monospaced",Font.BOLD,20));
		for(int i=0; i<14; i++) {
			hintButtons[i]=new ColorToggleButton();
			hintButtons[i].setUI(new BasicButtonUI());
			hintButtons[i].addMouseListener(this);
			hintButtons[i].setPreferredSize(new Dimension(1,1));
			//hintButtons[i].setBackground(Color.WHITE);
			hintButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			hintButtons[i].setFocusPainted(false);
			
			copyButtons[i]=new ColorToggleButton();
			copyButtons[i].setUI(new BasicButtonUI());
			copyButtons[i].addMouseListener(this);
			copyButtons[i].setPreferredSize(new Dimension(935,100));
			//copyButtons[i].setBackground(Color.WHITE);
			copyButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			copyButtons[i].setFocusPainted(false);
			
			if(i==13) {
				hintButtons[i].setText("Garden of Assemblage Map");
				copyButtons[i].setText("Garden of Assemblage Map");
			}
			else {
				hintButtons[i].setText("Secret Ansem's Report #"+(i+1));
				copyButtons[i].setText("Secret Ansem's Report #"+(i+1));
			}
			//hintButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			//copyButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,20));
			hintButtons[i].setFont(new Font("Monospaced",Font.BOLD,20));
			copyButtons[i].setFont(new Font("Monospaced",Font.BOLD,20));
			
			hintButtons[i].setEnabled(false);
			hintPanel.add(hintButtons[i]);
			
			copyButtons[i].setEnabled(false);
		}
		
		
			pnachButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			popoutButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			aboutButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
			markButton.setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,25));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		frame.add(hintPanel,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				popoutFrame.setLocationRelativeTo(frame);
			}
		});
		frame.setVisible(true);
		
		
	}//end reset
	
	@SuppressWarnings("unused")
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
			
			input.close();
			
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
				
				/*for(World world:worlds) {
					world.pools=output;
					world.poolItems=new int[output.size()];
				}*/
				
			}//end while
			input.close();
		}catch(IOException fnf) {
			throw new FileNotFoundException("File not found");
		}
		
		return output;
		
	}//end generatePools
	
	@SuppressWarnings("serial")
	public void readPnach() throws IOException{
		
		if(markButton.isEnabled()) {
		reset();
		}
		
		boolean removedPoL=false;
		boolean removedReports=false;
		
		try {
		
	
		JFileChooser jfc=new JFileChooser();
		jfc.setDialogTitle("Select Seed File");
		jfc.setCurrentDirectory(new File(System.getProperty("user.home")+"/Desktop"));
		
		
		//jfc.addChoosableFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		jfc.setFileFilter(new FileNameExtensionFilter("PNACH files", "pnach"));
		
		if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			
			
			
			if(!jfc.getSelectedFile().getName().toLowerCase().contains("pnach")) {
				readPnach();
				return;
			}
			

			
			
			for(int i=0; i<hintButtons.length; i++) {
				hintButtons[i].setEnabled(true);
				copyButtons[i].setEnabled(true);
			}
			
			ArrayList<JCheckBox> checkBoxes=new ArrayList<>();
			ArrayList<JLabel> labels=new ArrayList<>();
			GridLayout grid=new GridLayout(pools.size(),2);
			JPanel tempPanel=new JPanel();
			tempPanel.setLayout(grid);
			
			for(int i=0; i<pools.size(); i++) {
				labels.add(new JLabel(pools.get(i).name));
				labels.get(i).setToolTipText(hoverPoolText.get(i));
				checkBoxes.add(new JCheckBox());
				if(!labels.get(i).getText().equals("Yen Sid's Choice")&&!labels.get(i).getText().equals("Mickey's Choice"))
				checkBoxes.get(i).setSelected(true);
				tempPanel.add(labels.get(i));
				tempPanel.add(checkBoxes.get(i));
			}
			
			JOptionPane.showMessageDialog(null,tempPanel,"What pools would you like to be counted?",JOptionPane.PLAIN_MESSAGE,new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png"));
			
			String[] instArr= new String[1];
			Pool tempReports=new Pool("",instArr,0) ;
			
			for(int p=0; p<pools.size(); p++)
				if(pools.get(p).name.equals("Reports"))
					tempReports=pools.get(p);
			
			for(int i=0; i<labels.size(); i++) {
				for(int p=0; p<pools.size(); p++) {
						
					if(pools.get(p).name.equals(labels.get(i).getText())) {
						if(!checkBoxes.get(i).isSelected()) {
							if(pools.get(p).name.equals("Path of Light"))
								removedPoL=true;
							if(pools.get(p).name.equals("Reports"))
								removedReports=true;
							pools.remove(p);
							hoverPoolText.remove(p);
							p--;
						}//checkbox
						break;
					}//text equal
				}//for each
			}//for
			
			for(World world:worlds) {
				world.pools=pools;
				world.poolItems=new int[pools.size()];
			}
			
			for(int i=0; i<worlds.size()-1; i++) {
				checklists.add(new ArrayList<Integer>());
				spinners.add(new ArrayList<JSpinner>());
				for(int p=0; p<pools.size(); p++) {
					checklists.get(i).add(0);
					
					SpinnerNumberModel snm=new SpinnerNumberModel();
					snm.setValue(checklists.get(i).get(p));
					snm.setMinimum(0);
					snm.setMaximum(30);
					snm.setStepSize(1);
					spinners.get(i).add(new JSpinner(snm) {
						public void setLayout(LayoutManager mgr) {
							super.setLayout(new SpinnerLayout());
						}
					});
					
					spinners.get(i).set(p,addLabeledSpinner(pools.get(p).name,snm));
					spinners.get(i).get(p).addChangeListener(this);
					
					/*spinners.get(i).get(p).getComponent(0).setSize(new Dimension(200,200));
					spinners.get(i).get(p).getComponent(1).setSize(new Dimension(200,200));
					spinners.get(i).get(p).getComponent(2).setSize(new Dimension(200,200));*/
				}
			}
			if(spinnerBox==null)
			spinnerBox=new JFrame("");
			spinnerBox.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
			spinnerBox.setSize(500, 50*(pools.size()+1));
			spinnerBox.setLayout(new GridLayout(checklists.get(0).size()+1,2,3,3));
			
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
											System.out.println("Report "+(pool.items.indexOf(split[4])+1)+" is in "+world.name);
										}//end Reports check
									}
								}
								if(tempReports.items.contains(split[4])&&removedReports) {
									reports[tempReports.items.indexOf(split[4])].loc=world.name;
									System.out.println("Report "+(tempReports.items.indexOf(split[4])+1)+" is in "+world.name);
								}
							}//end world location check
						}//end world world world
					}//end locationCodes and poolCodes check
				}//end else
			}//end while
			pnachButton.setEnabled(true);
			markButton.setEnabled(true);
		}//end of OpenDialog if
		
		if(markButton.isEnabled()) {
		hidePoL=false;
		//Hide Path of Light hints
		if(!removedPoL)
		hidePoL=(JOptionPane.showConfirmDialog(null, "Would you like to hide Path of Light hints? (Points will still be counted.)")==JOptionPane.YES_OPTION);
		
		
		for(World world:worlds) {
			//world.fixLevel();
			world.updatePriority(hidePoL);
		}
		}//end read check

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
			
			
			for(int i=12; i>=0; i--) {
				if(reports[i].loc.equals("Critical Rewards and Garden")) {
					attempts[i]=13;
					hintButtons[i].setText(worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i));
					hintButtons[i].setToolTipText("<html>"+(worldOrder.get(i).priorityScore-worldOrder.get(i).foundScore)+" points remaining."
							+ "<br><br>Click me to go to the Mark Off page for "+worldOrder.get(i).name+".<html>");
					copyButtons[i].setText("#"+(i+1)+" - "+worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i));
					popoutFrame.getContentPane().removeAll();
					popoutFrame.add(copyButtons[i]);
					lastUpdatedButton=i;
					popoutFrame.repaint();
					popoutFrame.revalidate();
					
					/*try {
						//hintButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,12));
						//copyButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,11));
					} catch (FontFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
					hintButtons[i].setFont(new Font("Monospaced",Font.BOLD,19));
					copyButtons[i].setFont(new Font("Monospaced",Font.BOLD,18));
					
				}
			}
			if(reports[13].loc.equals("Critical Rewards and Garden")) {
				JOptionPane.showMessageDialog(frame, "The Garden of Assemblage map was given to you at the start.  Use it whenever you like!");
			}
			
			
			pnachButton.setSelected(false);
		}//end pnachButton
		
		else if(e.getSource()==aboutButton) {
			JDialog aboutBox=new JDialog(frame, "About");
			aboutBox.setSize(1250,1000);
			
			String text="";
			String line="";
			try {
				BufferedReader file=new BufferedReader(new FileReader("README.txt"));
				
				while((line=file.readLine())!=null) {
					text+=line+"\n";
				}
				file.close();
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
			
			aboutButton.setSelected(false);
		}//end aboutButton
		
		else if(e.getSource()==popoutButton&&!popoutFrame.isVisible()) {
			
			popoutFrame=new JFrame("Popout");
			popoutFrame.setSize(935,100);
			if(lastUpdatedButton==-1)
			popoutFrame.add(copyButtonExtra);
			else popoutFrame.add(copyButtons[lastUpdatedButton]);
			popoutFrame.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
			popoutFrame.setLocationRelativeTo(frame);
			popoutFrame.setVisible(true);
			
			popoutButton.setSelected(false);
		}//end popoutButton
		
		else if(e.getSource()==markButton||e.getSource()==copyButtonExtra) {
			
			String[] allWorlds=new String[worlds.size()-1];
			
			for(int i=0; i<allWorlds.length; i++) {
				allWorlds[i]=worlds.get(i).name;
			}
			
			//String answer=(String) JOptionPane.showInputDialog(null, "What world are you checking?", //Creates pop-up window for location check
			//		"World Check-Off",JOptionPane.QUESTION_MESSAGE,null, allWorlds, allWorlds[0]);
			
			spinnerBox=new JFrame(allWorlds[spinnerWorld.getSelectedIndex()]+" Checklist");
			spinnerBox.setIconImage(new ImageIcon("icon/Struggle_Trophy_Crystal_KHII.png").getImage());
			spinnerBox.setSize(500, 50*(pools.size()+1));
			spinnerBox.setLayout(new GridLayout(checklists.get(spinnerWorld.getSelectedIndex()).size()+1+1,2,3,3));
			
			spinnerBox.add(seedButton);
			spinnerBox.add(trackerButton);
			
			
			spinnerBox.add(spinnerWorld);
			spinnerBox.add(spinnerPoints);
			
			
			
			int totalPoints=0;
			
			
				
					spinnerWorld.removeActionListener(this);
					//spinnerWorld.setSelectedIndex(0);
					spinnerWorld.addActionListener(this);
					
					spinnerLabels=new ArrayList<>();
					
					for(int p=0; p<checklists.get(spinnerWorld.getSelectedIndex()).size(); p++) {
						
						
						spinnerLabels.add(new JLabel(pools.get(p).name));
						spinnerLabels.get(p).setToolTipText(hoverPoolText.get(p));
						/*try {
							//spinnerLabels.get(p).setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,15));
						} catch (FontFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						
						spinnerLabels.get(p).setFont(new Font("Monospaced",Font.BOLD,25));
						
						spinnerLabels.get(p).addMouseListener(this);
						spinnerBox.add(spinnerLabels.get(p));
						spinnerBox.add(spinners.get(spinnerWorld.getSelectedIndex()).get(p));
						totalPoints+=checklists.get(spinnerWorld.getSelectedIndex()).get(p)*pools.get(p).priority;
					}
					spinnerPoints.setText("Points: "+totalPoints);
					
					spinnerPoints.setToolTipText("<html>Keyboard Controls: Click on pool spinner to start.<br>UP/DOWN ARROW: Increment or Decrement the number.<br>TAB: Go to next pool.<br>SHIFT+TAB:Go to previous pool."
							+ "<br><br>Mouse Controls: Click on the pool labels to use the following.<br>LEFT CLICK: Increment the pool.<br>RIGHT CLICK: Decrement the pool."
							+ "<br><br>Mouse Controls: Click on this label to use the following.<br>LEFT CLICK: Go to next location in list.<br>RIGHT CLICK: Go to previous location in list.</html>");
			
			
			
			
			spinnerBox.setVisible(true);
			
			
			markButton.setSelected(false);
		}//end markButton
		else if(e.getSource()==spinnerWorld) {
			
			//System.out.println("HERE");
			
			spinnerBox.getContentPane().removeAll();
			spinnerBox.setTitle(spinnerWorld.getSelectedItem()+" Checklist");
			spinnerBox.setSize(500, 50*(pools.size()+1));
			
			spinnerBox.setLayout(new GridLayout(checklists.get(0).size()+1+1,2,3,3));
			
			spinnerBox.add(seedButton);
			spinnerBox.add(trackerButton);
			
			spinnerBox.add(spinnerWorld);
			spinnerBox.add(spinnerPoints);
			
			
			
			int totalPoints=0;
			
			int w=spinnerWorld.getSelectedIndex();
			
				spinnerLabels=new ArrayList<>();

					for(int p=0; p<checklists.get(w).size(); p++) {
						
						
						spinnerLabels.add(new JLabel(pools.get(p).name));
						spinnerLabels.get(p).setToolTipText(hoverPoolText.get(p));
						/*try {
							//spinnerLabels.get(p).setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,15));
						} catch (FontFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						
						spinnerLabels.get(p).setFont(new Font("Monospaced",Font.BOLD,25));
						
						spinnerBox.add(spinnerLabels.get(p));
						spinnerBox.add(spinners.get(w).get(p));
						totalPoints+=checklists.get(w).get(p)*pools.get(p).priority;
						spinnerLabels.get(p).addMouseListener(this);
					}
					
					spinnerPoints.setText("Points: "+totalPoints);
					
				
				
		spinnerBox.revalidate();
			
		}//end spinnerWorld
		else if(e.getSource()==seedButton) {
			
			File fileName=new File("GoARacle_Seed.goaracle");
			
			
			
			Random rand=new Random();
			
			String outputText="";
			for(int i=0; i<13; i++) {
				outputText+=worldOrder.get(i).locations.get(rand.nextInt(worldOrder.get(i).locations.size()));
				outputText+=",";
				outputText+=worldOrder.get(i).priorityScore*97;
				outputText+=",";
				outputText+=(worldOrder.get(i).priorityScore*53)+worldOrder.get(i).highestPriorityIndex;
				outputText+=".";
			}
			outputText+="\n";
			
			for(int i=0; i<13; i++) {
				for(World world:worlds) {
					if(world.name.equals(reports[i].loc)) {
						outputText+=world.locations.get(rand.nextInt(world.locations.size()));
						outputText+=".";
						break;
					}
						
				}
			}
			
			try {
				BufferedWriter outputStream=new BufferedWriter(new FileWriter(fileName));
				outputStream.write(outputText);
				//System.out.println(fileName.getAbsolutePath());
				outputStream.close();
				
			}catch(IOException io) {
				io.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(frame,"Seed file saved at "+fileName.getPath());
			
		}//end seedButton
		else if(e.getSource()==trackerButton) {
			if(Desktop.isDesktopSupported()&&Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI("https://tracker.zaxu.xyz/next/#/"));
				}catch(IOException | URISyntaxException io) {
					io.printStackTrace();
				}
			}
		}//end trackerButton
		

		frame.revalidate();
		popoutFrame.revalidate();
		
		
	}//end actionPerformed

	@SuppressWarnings("serial")
	private JSpinner addLabeledSpinner(String name, SpinnerNumberModel spinnerNumberModel) {
		// TODO Auto-generated method stub

		JLabel label=new JLabel(name);
		//goARacle.add(label);
		JSpinner spinner=new JSpinner(spinnerNumberModel)
		{
			public void setLayout(LayoutManager mgr) {
				super.setLayout(new SpinnerLayout());
			}
		}
		;
		spinner.addMouseListener(this);
		
		label.setLabelFor(spinner);
		spinner.getComponent(0).setSize(new Dimension(200,200));
		spinner.getComponent(1).setSize(new Dimension(200,200));
		//spinner.getComponent(2).setPreferredSize(new Dimension(10,10));
		spinner.setFont(getFont().deriveFont(32f));
		return spinner;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	//Clicked and pressed not used.
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
			for(int p=0; p<spinnerLabels.size(); p++) {
				if(e.getSource()==spinnerLabels.get(p)) {
					if(e.getButton() == MouseEvent.BUTTON1&&(int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue()!=30) {
						spinners.get(spinnerWorld.getSelectedIndex()).get(p).setValue((int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue()+1);
						checklists.get(spinnerWorld.getSelectedIndex()).set(p,(int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue());
					}
					else if(e.getButton() == MouseEvent.BUTTON3&&(int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue()!=0) {
						spinners.get(spinnerWorld.getSelectedIndex()).get(p).setValue((int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue()-1);
						checklists.get(spinnerWorld.getSelectedIndex()).set(p,(int)spinners.get(spinnerWorld.getSelectedIndex()).get(p).getValue());
					}
				}
			}
		
		
		
		
		if(copyButtonExtra.isSelected())
			copyButtonExtra.doClick();
		
		for(int i=0; i<hintButtons.length; i++) {
			
			if(hintButtons[i].isSelected()) {
				hintButtons[i].doClick();	
			}
			
			if(copyButtons[i].isSelected()) {
				copyButtons[i].doClick();	
			}
			
			if((e.getSource()==hintButtons[i]||e.getSource()==copyButtons[i])&&attempts[i]==13&&i!=13) {
				
				spinnerWorld.setSelectedIndex(worlds.indexOf(worldOrder.get(i)));
				if(!spinnerBox.isVisible())
				markButton.doClick();
				
				
				
			}//end open mark off
			
			if((e.getSource()==hintButtons[i]||e.getSource()==copyButtons[i])&&hintButtons[i].isEnabled()&&attempts[i]<3) {
				
				
				int goaMapUsed=-1;
				
				String[] choices=new String[worlds.size()];
				
				String correctAnswer=reports[i].loc;
				
				for(int p=0; p<choices.length; p++) {
					choices[p]=worlds.get(p).name;
				}
				
				String answer="";
				
				int confirmChoice=-1;
				boolean confirmHappened=false;
				
				do {
				
				if(i==13) {
					answer=(String) JOptionPane.showInputDialog(null, "Where did you find the GoA map?", //Creates pop-up window for location check
							"GoA Map Check: Attempt #"+(attempts[i]+1)+"/3",JOptionPane.QUESTION_MESSAGE,null, choices, choices[0]);
				}
				else answer=(String) JOptionPane.showInputDialog(null, "Where did you find Report #"+(i+1)+"?", //Creates pop-up window for location check
						"Report #"+(i+1)+" Check: Attempt #"+(attempts[i]+1)+"/3",JOptionPane.QUESTION_MESSAGE,null, choices, choices[0]);
				
				if(answer!=null&&attempts[i]==2) {
					confirmHappened=true;
					if(i==13)
						confirmChoice=JOptionPane.showConfirmDialog(null,"Are you sure that GoA Map was in "+answer+"?","Confirmation for GoA Map, attempt #"+(attempts[i]+1)+"/3",JOptionPane.YES_NO_OPTION);
				else confirmChoice=JOptionPane.showConfirmDialog(null,"Are you sure that Report #"+(i+1)+" was in "+answer+"?","Confirmation for Report #"+(i+1)+", attempt #"+(attempts[i]+1)+"/3",JOptionPane.YES_NO_OPTION);
				}
				
				//System.out.println(confirmHappened+" "+confirmChoice);
				}while(confirmChoice==JOptionPane.NO_OPTION);
				
				if(answer.equals(correctAnswer)&&( (confirmHappened&&confirmChoice==JOptionPane.YES_OPTION)||(!confirmHappened&&confirmChoice==-1) )) {
					attempts[i]=13;
					if(i==13) {
							int bestUnfound=-1;
							
							if(pools.get(0).name.equals("Path of Light")&&!hidePoL) {
								for(int r=0; r<13; r++) {
									if(worldOrder.get(r).poolItems[0]>0&&attempts[r]<3) {
										bestUnfound=r;
										break;
									}
								}
							}
							if(bestUnfound==-1)
							for(int r=0; r<13; r++) {
								if(attempts[r]<3) {
									bestUnfound=r;
									break;
								}
							}//end report loop
							
							if(bestUnfound==-1) {
								hintButtons[i].setText("The map leads nowhere, as there is nowhere else to report.");
								copyButtons[i].setText("GoA - The map leads nowhere, as there is nowhere else to report.");
							}
							else {
								hintButtons[i].setText("The map leads you to Report "+(bestUnfound+1)+" in "+reports[bestUnfound].loc+".");
								copyButtons[i].setText("GoA - The map leads you to Report "+(bestUnfound+1)+" in "+reports[bestUnfound].loc+".");
								
								hintButtons[bestUnfound].setText(worldOrder.get(bestUnfound).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(bestUnfound))),bestUnfound));
								copyButtons[bestUnfound].setText(("#"+(bestUnfound+1)+" - "+worldOrder.get(bestUnfound).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(bestUnfound))),bestUnfound)));
								
								/*try {
									//hintButtons[bestUnfound].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,12));
									//copyButtons[bestUnfound].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,11));
								} catch (FontFormatException | IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}*/
								
								hintButtons[bestUnfound].setFont(new Font("Monospaced",Font.BOLD,19));
								copyButtons[bestUnfound].setFont(new Font("Monospaced",Font.BOLD,18));
								
								goaMapUsed=bestUnfound;
								attempts[bestUnfound]=13;
							}
							
							
							
					}//end GoA map get best report
					else {
				hintButtons[i].setText(worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i));
				hintButtons[i].setToolTipText("<html>"+(worldOrder.get(i).priorityScore-worldOrder.get(i).foundScore)+" points remaining."
						+ "<br><br>Click me to go to the Mark Off page for "+worldOrder.get(i).name+".<html>");
				copyButtons[i].setText(("#"+(i+1)+" - "+worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i)));
					}//end normal case
				/*try {
					//hintButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,12));
					//copyButtons[i].setFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("resources/KH2_ALL_MENU_I.TTF"))).deriveFont(Font.PLAIN,11));
				} catch (FontFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
					hintButtons[i].setFont(new Font("Monospaced",Font.BOLD,19));
					copyButtons[i].setFont(new Font("Monospaced",Font.BOLD,18));
				
				popoutFrame.getContentPane().removeAll();
				if(goaMapUsed==-1) {
				popoutFrame.add(copyButtons[i]);
				lastUpdatedButton=i;
				}
				else {
					popoutFrame.add(copyButtons[goaMapUsed]);
					lastUpdatedButton=goaMapUsed;
				}
				popoutFrame.repaint();
				popoutFrame.revalidate();
				
				
				
				
				}//end check for correct answer
				else if(answer!=null &&!(confirmHappened&&confirmChoice==-1) ){
					attempts[i]++;
					
				}
			}//end source if
		}//end outer for loop
		
		for(int i=0; i<hintButtons.length; i++) {
			if(hintButtons[i].isSelected()) {
				hintButtons[i].doClick();	
			}
			if(copyButtons[i].isSelected()) {
				copyButtons[i].doClick();	
			}
		}
		
		if(e.getSource()==spinnerPoints) {
			if(e.getButton()==MouseEvent.BUTTON1) {
				if(spinnerWorld.getSelectedIndex()==16)
					spinnerWorld.setSelectedIndex(0);
				else spinnerWorld.setSelectedIndex(spinnerWorld.getSelectedIndex()+1);
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {
				if(spinnerWorld.getSelectedIndex()==0)
					spinnerWorld.setSelectedIndex(16);
				else spinnerWorld.setSelectedIndex(spinnerWorld.getSelectedIndex()-1);
			}
				
		}
		
		frame.revalidate();
		popoutFrame.revalidate();
	}//end mouseReleased

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}
	//mouseEntered and Exited not used.
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
					
					//System.out.println(worlds.get(w).name+" "+pools.get(p).name+" "+checklists.get(w).get(p));
					
					int totalPoints=0;
					
					for(int i=0; i<checklists.get(w).size(); i++)
						totalPoints+=checklists.get(w).get(i)*pools.get(i).priority;
					spinnerPoints.setText("Points: "+totalPoints);
					
					boolean changed=false;
					
					for(int i=0; i<hintButtons.length-1; i++) {
						if(attempts[i]==13&&!hintButtons[i].getText().equals((worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i)))) {
							hintButtons[i].setText(worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i));
							hintButtons[i].setToolTipText("<html>"+(worldOrder.get(i).priorityScore-worldOrder.get(i).foundScore)+" points remaining."
									+ "<br><br>Click me to go to the Mark Off page for "+worldOrder.get(i).name+".<html>");
							copyButtons[i].setText(("#"+(i+1)+" - "+worldOrder.get(i).getReportInfo(checklists.get(worlds.indexOf(worldOrder.get(i))),i)));
							popoutFrame.getContentPane().removeAll();
							popoutFrame.add(copyButtons[i]);
							lastUpdatedButton=i;
							popoutFrame.repaint();
							popoutFrame.revalidate();
							changed=true;
						}
					}
					if(!changed) {
						popoutFrame.getContentPane().removeAll();
						popoutFrame.add(copyButtonExtra);
						copyButtonExtra.setText("You got "+totalPoints+" points from checks in "+worlds.get(w).name);
						lastUpdatedButton=-1;
					}
					

				}
			}
		}
		

		
		frame.revalidate();
		
		
	}
	
}//end GoARacle class