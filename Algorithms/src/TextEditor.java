/* 
Author : Sahil Nyati
This class creates a text editor with option to open,saveas or save an existing file using a file chooser option. 
 */

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TextEditor extends JFrame  {
	public static void main(String[] args) {
		new TextEditor();                                    
	}
	private class MenuAndCountTuple {                   // Class for generating history in menu
		JMenuItem item;
		int count;

		public MenuAndCountTuple(JMenuItem i, int c) {      // Constructor method for Menu and countTuple class
			this.item = i;
			this.count = c;
		}
		JMenuItem getMenuItem() {    //Getter method to get Jmenu Item 
			return this.item;
		}

		int getOccurancesCount() {         
			return this.count;
		}
	}
	private class bookTuple {                   // Class for generating history in menu
		JMenuItem item;
		int pos;

		public bookTuple(JMenuItem i, int c) {      // Constructor method for Menu and countTuple class
			this.item = i;
			this.pos = c;
		}
		JMenuItem getMenuItem() {    //Getter method to get Jmenu Item 
			return this.item;
		}

		int getposition() {         
			return this.pos;
		}
	}	


	// Creating Menus (Source :Stack overflow : How to create menus)
	private JMenu fileMenu;
	private JMenu toolsMenu,opSubm, bookmark, bm1;
	File file12;
	private JMenu HelpMenu;
	private JMenuItem OpenFile, saveFile, saveasFile, closeFile,exit;
	private JMenuItem Helpabout,mi1,mi2,mi3,mi4,bm,bm2;
	private Queue<MenuAndCountTuple> queue;          // Queue initialization 
	private List<bookTuple> allBookmarks;
	// private JFrame frame;
	private JLabel label=new JLabel();  // Creating status label
	private JScrollPane scroll;         // Creating scroll pane to read files 
	private JTextArea textArea;        // Creating text area
	private JFrame window;              // Creating window 
	private boolean open = false;       // Flag variable that change status when file is opened or saved 
	private boolean save=false;
	private String path="";             // Global variable for path

	public TextEditor() {                                   // Constructor for the class

		super("Text Editor");
		Menufile();                // Calling Function to create file menu 
		Menutools();              // Calling Function to create tools menu
		MenuHelp();             // Calling Function to create help menu
		makeTextArea();        // Calling Function to create text area
		createWindow();          // Calling Function to create outside frame

		queue = new LinkedList<MenuAndCountTuple>();   // Creating queue to hold the search history
		allBookmarks = new ArrayList<bookTuple>();
	}

	private JTextArea makeTextArea() {       // Function to create text area

		textArea = new JTextArea(30, 50);
		textArea.setEditable(true);
		Font textFont = new Font("Verdana", 0, 14);
		textArea.setFont(textFont);
		scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return textArea;  
	}
	private JFrame createWindow() {                           // Function to create outside frame
		window = new JFrame("Text Editor");
		window.add(scroll, BorderLayout.CENTER);
		window.setExtendedState(Frame.MAXIMIZED_BOTH);
		window.add(BorderLayout.SOUTH,label); 
		window.setVisible(true);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setJMenuBar(createMenus());
		window.pack();
		return window;
	}

	private JMenuBar createMenus() {                                  // writing Function to create menus 
		JMenuBar menuBar = new JMenuBar();                          // Creating and setting menu bar
		setJMenuBar(menuBar);
		// Adding menus to menu bar
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(HelpMenu);
		return menuBar;
	}

	private void Menufile() {                                   // Function to make file menu with options 

		// Creating menu file with the appropriate size
		fileMenu = new JMenu("File");
		fileMenu.setPreferredSize(new Dimension(40, 20));

		// Creating menu item with appropriate size and adding it to action listener
		OpenFile = new JMenuItem("Open...");
		OpenFile.addActionListener(new OpenButtonListener());
		OpenFile.setPreferredSize(new Dimension(100, 20));
		OpenFile.setEnabled(true);

		// Creating menu item with appropriate size and adding it to action listener
		saveFile = new JMenuItem("Save");
		saveFile.addActionListener(new SaveButtonListener());
		saveFile.setPreferredSize(new Dimension(100, 20));
		saveFile.setEnabled(true);
		// Creating menu item with appropriate size and adding it to action listener
		saveasFile = new JMenuItem("Save As...");
		saveasFile.addActionListener(new SaveasButtonListener());
		saveasFile.setPreferredSize(new Dimension(100, 20));
		saveasFile.setEnabled(true);
		// Creating menu item with appropriate size and adding it to action listener
		closeFile = new JMenuItem("close");
		closeFile.addActionListener(new closeButtonListener());
		closeFile.setPreferredSize(new Dimension(100, 20));
		closeFile.setEnabled(true);
		// Creating menu item with appropriate size and adding it to action listener
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitButtonListner());
		exit.setPreferredSize(new Dimension(100, 20));
		exit.setEnabled(true);
		// Adding item to menu file 
		fileMenu.add(OpenFile);
		fileMenu.add(new JSeparator());          // Adding seperators between the menu items
		fileMenu.add(saveFile);
		fileMenu.add(saveasFile);
		fileMenu.add(new JSeparator());
		fileMenu.add(closeFile);
		fileMenu.add(new JSeparator());
		fileMenu.add(exit);
	}
	private void MenuHelp() {                                                       // Function to create help menu
		HelpMenu = new JMenu("Help");               
		Helpabout = new JMenuItem("About");
		Helpabout.addActionListener(new aboutButtonListener());                   // Adding action listener to about menu option
		Helpabout.setPreferredSize(new Dimension(100, 20));                    // Setting the size of help menu
		Helpabout.setEnabled(true);	
		HelpMenu.add(Helpabout);                                          // Adding item to menu
	}
	private class SaveButtonListener implements ActionListener {                         // Inner class to check if save menu item is pressed
		public void actionPerformed(ActionEvent e) { 
			window.setTitle("Text Editor");
			textArea.setEditable(true);
			if(e.getSource().equals(saveFile))                                                   
			{

				if (!open && !save) {                                                // Opening JFile chooser if the file is not saved or opened

					JFileChooser saveopt = new JFileChooser(); 
					FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");   // Setting default extension for Jfile chooser 
					saveopt.setFileFilter(filter);
					if (saveopt.showSaveDialog(textArea) == JFileChooser.APPROVE_OPTION) {    
						String path2=saveopt.getSelectedFile().getAbsolutePath();                               // Getting the default path for file chooser
						Path p5=Paths.get(path2);
						String res=p5.getFileName().toString();
						System.out.println(res);
						if(res.contains(".txt"))
						{
							path=path2;                                                    // Setting the path variable after file is chosen
							saveFile(path2);                                           // Calling the save file function to save file
							open=true; 
							// Setting the open flag as true
						}
						else 

							JOptionPane.showMessageDialog(textArea, "Invald File. File must have .txt extension", "File Message Error", JOptionPane.ERROR_MESSAGE);		
					}
				}
				else if(open && save)                                                     // Checking if file is open and saved once already not opening file chooser 
				{
					try {

						FileWriter fr=new FileWriter(path);                                 // Passing the path where it was saved to get the file 
						BufferedWriter br=new BufferedWriter(fr);
						br.write(textArea.getText());
						br.close(); 
						Path p=Paths.get(path);                                               // Getting the path from file 
						label.setText(p.getFileName().toString()+" successfully saved ");     // Setting label 
					} catch (IOException err) {
						err.printStackTrace();
					}
				}
				else if(open)                                                                     // Checking if open is true and save is false 
				{
					try {
						FileWriter fr=new FileWriter(file12);                                // Passing the opened file to save
						BufferedWriter br=new BufferedWriter(fr);
						br.write(textArea.getText());
						br.close(); 
						label.setText(file12.getName()+" successfully saved");        // Setting the label for save file 
					} catch (IOException err) {
						err.printStackTrace();
					}
				}
				else {
					try {

						FileWriter fr=new FileWriter(path);                                 // Passing the path where it was saved to get the file 
						BufferedWriter br=new BufferedWriter(fr);
						br.write(textArea.getText());
						br.close(); 
						Path p=Paths.get(path);                                               // Getting the path from file 
						label.setText(p.getFileName().toString()+" successfully saved ");     // Setting label 
					} catch (IOException err) {
						err.printStackTrace();
					}
				}
			}
		}
	} 
	private class SaveasButtonListener implements ActionListener {                        // Inner Class for save as button listener  
		public void actionPerformed(ActionEvent e) {                                        
			if(e.getSource().equals(saveasFile))                                                 // Checking if saveas file menu option is selected  
			{
				textArea.setEditable(true);                                                      // Making text area to be editable
				window.setTitle("Text Editor");                                               
				File file;
				JFileChooser saveopt = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");  // Setting extension as text files
				saveopt.setFileFilter(filter);
				if (saveopt.showSaveDialog(textArea) == JFileChooser.APPROVE_OPTION) { 
					// Opening file chooses for save as option
					String path3=saveopt.getSelectedFile().getAbsolutePath();                               // Getting the default path for file chooser
					Path p1=Paths.get(path3);
					String res=p1.getFileName().toString();
					System.out.println(res);
					if(res.contains(".txt"))
					{
						path=path3;
						file = saveopt.getSelectedFile();
						try
						{

							FileWriter fr=new FileWriter(file);                                       // Writing to the file 
							BufferedWriter br=new BufferedWriter(fr);
							br.write(textArea.getText());
							br.close();
							label.setText(file.getName()+" successfully saved");            // Setting label to give status 
							save=true;	
						}	
						catch(IOException e9)                                                    // Catching file not found exception
						{
							JOptionPane.showMessageDialog(textArea, "Can't save file "
									+ e9.getMessage());
						}
					} 
					else
					{
						JOptionPane.showMessageDialog(textArea, "Invald File. File must have .txt extension", "File Message Error", JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
		}
	}
	private void openingfiles(File filename) throws IOException {          // function to open file 
		try {
			textArea.setEditable(true);
			open=true;
			FileReader fr=new FileReader(filename);                      // Passing file argument to File Reader to open file
			Font textFont = new Font("Verdana", 0, 14);
			textArea.setForeground(Color.BLACK);
			textArea.setFont(textFont);
			textArea.read(fr, null);                                   // Reading the file 
			label.setText("File : "+filename.getName());            // Setting the status label 
		}
		catch (IOException err) {
			// err.printStackTrace();
		}
	}	
	private void saveFile(String filename) {                                              // Save file function to save file 
		try {
			Path p=Paths.get(filename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(textArea.getText());
			writer.close();
			save = true;
			label.setText(p.getFileName().toString()+" successfully saved ");     // Setting the status label
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
	private class OpenButtonListener implements ActionListener {                        // Inner class to open file with Jfile chooser option 
		public void actionPerformed(ActionEvent e) {                                        
			if(e.getSource().equals(OpenFile))                                                   
			{
				textArea.setEditable(true);
				window.setTitle("Text Editor");
				JFileChooser open = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");  // Setting default for Jfile chooser
				open.setFileFilter(filter);
				if(open.showOpenDialog(textArea)==JFileChooser.APPROVE_OPTION)                  
				{
					String path91=open.getSelectedFile().getAbsolutePath();                               // Getting the default path for file chooser
					Path p20=Paths.get(path91);
					String res=p20.getFileName().toString();
					if(res.contains(".txt"))
					{
						file12 = open.getSelectedFile();    // Get the selected file from Jfile chooser to open

						try {
							openingfiles(file12);                                       // Calling the opening file function
						} catch (IOException e1) {
							e1.printStackTrace();
						}                                                                  
					}
					else
						JOptionPane.showMessageDialog(textArea, "Invald File. File must have .txt extension", "File Message Error", JOptionPane.ERROR_MESSAGE);		
				}
			}
		}
	}
	private class aboutButtonListener implements ActionListener {                    // Inner class to check about menu option
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(Helpabout))                                                  
			{
				label.setText(null);
				textArea.setText(null);   
				window.setTitle("About Text Editor");
				textArea.setEditable(false);
				Font textFont = new Font("Comic Sans MS", 0, 20);                      // Changing the font  
				textArea.setFont(textFont);
				textArea.setForeground(Color.BLUE);                                     // Changing the colour
				textArea.append("Text Editor \n Created By \n Sahil Nyati \n Can be contacted via email : snyati@andrew.cmu.edu \n Version 1.0 \n Released on 11/15/2015 \n \n \n \n Carnegie Mellon University \n Pitsburgh \n PA ");                                                     
			}
		}
	}
	private class ExitButtonListner implements ActionListener {                      // Inner class to check exit menu option
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(exit))                                                  
			{
				window.dispose();
				System.exit(0);                                                              // Exiting the program
			}
		}
	}
	private class closeButtonListener implements ActionListener {              // Inner class to check close menu option
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(closeFile))                                                  // Checking for close button
			{
				path="";                                                          // resetting the path
				textArea.setText(null);       
				label.setText(null);
				open=false;
				save=false;
				textArea.setEditable(true);
				Font textFont = new Font("Verdana", 0, 14);
				textArea.setFont(textFont);
				textArea.setForeground(Color.BLACK);
				
				System.out.println("Insideee...");
				allBookmarks.clear();
				bm1.removeAll();
			}
		}
	}
	private void Menutools() {                                                  // Creating tools menu for HW8
		toolsMenu = new JMenu("Tools");
		toolsMenu.setPreferredSize(new Dimension(50, 20));

		opSubm = new JMenu("Words");
		opSubm.add(mi1 = new JMenuItem("Count"));
		mi1.addActionListener(new countButtonListener());
		opSubm.add(mi2 = new JMenuItem("Unique"));
		mi2.addActionListener(new uniqueButtonListener());
		opSubm.add(mi3 = new JMenu("Search"));
		//   JMenu search = new JMenu("Search");
		//  search.add(fi=new JMenuItem("Search New...."));
		mi3.add(mi4 = new JMenuItem("Search New..."));
		mi4.addActionListener(new searchButtonListener());
		mi3.add(new JSeparator()); 
		
		bookmark = new JMenu("Bookmark");
		//   bookmark.addActionListener(new OpenButtonListener());
		bookmark.setPreferredSize(new Dimension(100, 20));
		bookmark.setEnabled(true);
		bookmark.add(bm=new JMenuItem("New"));
		bm.addActionListener(new newButtonListener());
		bookmark.add(bm1=new JMenu("Old"));
		//bm1.add(new JMenuItem("Smaplelmds...."));
		//      bm1.addActionListener(new newButtonListener());
		bookmark.add(bm2=new JMenuItem("Delete"));
		bm2.addActionListener(new deleteButtonListener());

		toolsMenu.add(opSubm);
		toolsMenu.add(bookmark);
		// toolsMenu.add(wordsFile);        
	}
	private class newButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(bm))                                                  // Checking for close button
			{
					int t=textArea.getCaretPosition();			
					String result = JOptionPane.showInputDialog(textArea, "Enter File name:"); 
					System.out.println(result);
					if(result!=null && result.length() >0) {
						bookTuple newItem = new bookTuple(new JMenuItem(result), t);    // updating JMenu Item with searched key
						System.out.println(newItem.getMenuItem());
						//	newItem.getMenuItem().addActionListener(new newButtonListener());

						newItem.getMenuItem().addActionListener(new oldButtonListener());
						allBookmarks.add(newItem);   // Adding the Jmenu item to queue
						List<String> allItems = new ArrayList<String>();
						
						allBookmarks.sort(new Comparator<bookTuple>(){

							@Override
							public int compare(bookTuple o1, bookTuple o2) {
								return o1.getMenuItem().getText().compareTo(
										o2.getMenuItem().getText());
							}
							
						});
											
						bm1.removeAll();
						//List<bookTuple> temp = new ArrayList<bookTuple>();
						for(bookTuple s : allBookmarks) {
							JMenuItem item = s.getMenuItem();
							//item.addActionListener(new oldButtonListener());
							bm1.add(item);
						}
						//bm1.add(newItem.getMenuItem());   // Adding the item to queue

					}
			}
		}
	}
	private class oldButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Iterator<bookTuple> i = allBookmarks.iterator();
			while(i.hasNext()) {
				bookTuple item = i.next();
				if(e.getSource().equals(item.getMenuItem())) {
					textArea.setCaretPosition(item.getposition());
				}
			}	
		}
		
	}
	
	private class deleteButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(bm2))
			{
					// System.out.println("sahil");
					String[] choices = new String[allBookmarks.size()];
					for(int i=0; i<allBookmarks.size();i++) {
						choices[i] = allBookmarks.get(i).getMenuItem().getText();
					}
					Object anchor = null;
					String input = (String) JOptionPane.showInputDialog(null, "Select Bookmark to delete",
							"Delete Bookmarks", JOptionPane.QUESTION_MESSAGE, null,choices, anchor);

					if(input!=null && input.length()>0) {
						bookTuple toBeRem = null;
						for(bookTuple s : allBookmarks) {
							if(s.getMenuItem().getText().equals(input)) {
								toBeRem = s;
							}
						}
						allBookmarks.remove(toBeRem);
						bm1.remove(toBeRem.getMenuItem());
					}
			}
		}
	}

	private class countButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(mi1))                                                  // Checking for close button
			{
				if(open)
				{
					int words=0;
					FileReader fr = null;
					try {
						fr = new FileReader(file12);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Scanner in = new Scanner(fr);
					while(in.hasNext())
					{
						in.next();
						words++;
					}
					//	System.out.println(words);
					label.setText("Word Count: "+words);

				}
				else
					JOptionPane.showMessageDialog(textArea, "Please open file to count words", "File Message Error", JOptionPane.ERROR_MESSAGE);			
			}
		}
	}
	private class uniqueButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(mi2))                                                  // Checking for unique button 
			{
				if(open)
				{
					try {
						ArrayList<String> unique = new ArrayList<String>();             // Creating array list of string 
						FileReader fr=new FileReader(file12);
						BufferedReader br = new BufferedReader (fr);   
						String line = br.readLine();
						int count = 0;
						int uword=0;
						while (line != null) {
							String []split = line.split(" ");                     
							for(int i=0;i<split.length;i++)
							{
								unique.add(split[i]);
							}
							line = br.readLine();
						}
						Set<String> uset=new HashSet<String>(unique);             // Creating hash map for uniqe strings
						for(String words:uset)
						{
							count++;     // Counting unique words
						}
						// 	System.out.println(count);
						label.setText("Unique Word Count: "+count);

					}

					catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}		
				}
				else
					JOptionPane.showMessageDialog(textArea, "Please open file to count unique words", "File Message Error", JOptionPane.ERROR_MESSAGE);				
			}
		}
	}

	private class searchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(mi4))                                                // Checking for close button
			{
				if(open)
				{
					Highlighter h = textArea.getHighlighter();
					String line;
					int count=0;
					int pos=0;
					String searchKey = JOptionPane.showInputDialog(window, "Enter string to search");


					// End Cases
					// If an element is already searched then it should not reappear
					// If count = 0 then dont add.
					try {



					} catch (Exception e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					if(searchKey== null)
					{
						label.setText("Error: Please enter searchkey and press okay");   // Setting the text of the label with no file                                 
					}
					else if(searchKey.length()==0)
					{
						h.removeAllHighlights();
						label.setText("Error: Please enter searchkey and press okay"); 
					}
					else
					{
						h.removeAllHighlights();	
						try{
							FileReader fr=new FileReader(file12);
							BufferedReader br=new BufferedReader(fr);
							pos=textArea.getText().indexOf(searchKey); // Getting the position of search string 
							while ((line=br.readLine())!=null){
								if(line.contains(searchKey))
								{
									count++;
									try {
										h.addHighlight(pos, pos+ searchKey.length(), DefaultHighlighter.DefaultPainter); // Highlight the occurences
										pos=textArea.getText().indexOf(searchKey, pos+1);
									} catch (BadLocationException e2) {  
										e2.printStackTrace();
									}
								}
								else
								{
									label.setText(" Please enter search key and try again" );	
								}
							}
							label.setText(searchKey+" :"+""+count+" occurences" );

							// Check if the searchedItem has already been searched
							boolean alreadySearched = false;    // flag for checking if a string is already searched 
							Iterator<MenuAndCountTuple> i = queue.iterator();   // getting the iterator for queue
							while(i.hasNext()) {
								if(i.next().getMenuItem().getText().equals(searchKey)) {     
									alreadySearched = true;
									break;
								}
							}

							// Now update the JMenuItem
							if(count > 0 && !alreadySearched) {
								if(queue.size() >= 5) {    //checking the size of queue
									mi3.remove((JMenuItem)queue.poll().getMenuItem());    // removing the oldest item if the size is 5
								}

								MenuAndCountTuple newItem = new MenuAndCountTuple(
										new JMenuItem(searchKey), count);    // updating JMenu Item with searched key
								newItem.getMenuItem().addActionListener(new searchButtonListener());

								queue.offer(newItem);   // Adding the Jmenu item to queue
								mi3.add(newItem.getMenuItem());   // Adding the item to queue
							}			       
						}
						catch(IOException e1)
						{
							System.out.println("File ");
						}
					}
				}
				else
					JOptionPane.showMessageDialog(textArea, "Please open file to search words", "File Message Error", JOptionPane.ERROR_MESSAGE);					
			} else {
				// See if any of the previous searches were clicked
				Iterator<MenuAndCountTuple> i = queue.iterator();   // Iterator for queue 
				while(i.hasNext()) {

					MenuAndCountTuple item = i.next();    //Iterating the queue
					if(e.getSource().equals(item.getMenuItem())) {
						label.setText(item.getMenuItem().getText()+ " :" + "" + 
								item.getOccurancesCount() + " occurences" );

						Highlighter h = textArea.getHighlighter();
						h.removeAllHighlights();
						try {
							FileReader fr=new FileReader(file12);
							BufferedReader br=new BufferedReader(fr); 
							int pos1=0;
							pos1=textArea.getText().indexOf(item.getMenuItem().getText());
							String line;

							while ((line=br.readLine())!=null){
								h.addHighlight(pos1, pos1+ item.getMenuItem().getText().length(), DefaultHighlighter.DefaultPainter); // Highlight search position
								pos1=textArea.getText().indexOf(item.getMenuItem().getText(), pos1+1);
							}
						} catch (BadLocationException e1) {
							System.out.println("");
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}
}