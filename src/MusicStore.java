
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Model.FileIOStorage;
import Model.MusicItem;
import Model.PersistenceException;
import Model.SQLStorage;
import Model.SerialStorage;
import Model.Storable;
import Model.XMLStorage;


public class MusicStore extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel artistLabel;
	private JTextField artistText;
	private JLabel albumLabel;
	private JTextField albumText;

	private JButton nextButton, prevButton, addButton;

	private JMenuBar menuBar;
	private JMenu fileMenu, storageMenu;
	private JMenuItem fileLoad, fileSave, programExit, selectfileIO, selectXML, selectSerial, selectSQL;

	private JFileChooser fileChooser;
	private File file;

	private Storable musicStorage;
	private Storable fileIOStorage = new FileIOStorage();
	private Storable XMLStorage = new XMLStorage();
	private Storable SerialStorage = new SerialStorage();
	private Storable SQLStorage = new SQLStorage();
  private Color initialMenuColor;
  private Color selectedMenuColor = new Color(0xa0a0a0);
	
	private static MusicStore frame;

	public static void main(String[] args) {

		frame = new MusicStore();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(280, 160);
		frame.setTitle("Iain's Music Collection");
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public MusicStore() {

	  fileChooser = new JFileChooser();
		prevButton = new JButton("<<");
		addButton = new JButton("Add");
		nextButton = new JButton(">>");

		artistLabel = new JLabel("Artist Name:");
		artistText = new JTextField(14);
		albumLabel = new JLabel("Album Title:");
		albumText = new JTextField(14);

		setLayout(new FlowLayout());

		addMenuBar();

		add(artistLabel);
		add(artistText);
		add(albumLabel);
		add(albumText);
		add(prevButton);
		add(addButton);
		add(nextButton);

		// Let's start with File IO storage
		setStorageType(fileIOStorage);

		nextButton.addActionListener(this);
		addButton.addActionListener(this);
		prevButton.addActionListener(this);
	}
	
	public void setStorageType(Storable store) {
	  this.musicStorage = store;
	}

	private void addMenuBar() {

		// Menu Bar strip
		menuBar = new JMenuBar();

		// Add a few menus (i.e. contains sub-items)
		fileMenu = new JMenu("File");
		storageMenu = new JMenu("Storage Type");
	  initialMenuColor = storageMenu.getBackground();

		// We only want to load, save or exit
		fileLoad = new JMenuItem("Load");
		fileMenu.add(fileLoad);

		fileSave = new JMenuItem("Save");
		fileMenu.add(fileSave);

		programExit = new JMenuItem("Exit");
    fileMenu.add(programExit);
    
		selectfileIO = new JMenuItem("File IO");
		selectXML = new JMenuItem("XML");
    selectSerial = new JMenuItem("Serialization");
    selectSQL = new JMenuItem("SQLite");

    storageMenu.add(selectfileIO);
		storageMenu.add(selectXML);
		storageMenu.add(selectSerial);
		storageMenu.add(selectSQL);

		// Join the menu to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(storageMenu);
		
    selectfileIO.setBackground(selectedMenuColor);

		fileLoad.addActionListener(this);
		fileSave.addActionListener(this);
		programExit.addActionListener(this);
		selectfileIO.addActionListener(this);
		selectXML.addActionListener(this);
		selectSerial.addActionListener(this);
		selectSQL.addActionListener(this);

		// Join the MenuBar to the frame
		setJMenuBar(menuBar);
	}

	public void clearSelections() {
    selectfileIO.setBackground(initialMenuColor);
    selectXML.setBackground(initialMenuColor);
    selectSerial.setBackground(initialMenuColor);
    selectSQL.setBackground(initialMenuColor); 
	}
	
	public void clearUserInputs() {
    artistText.setText("");
    albumText.setText("");
	}
	
  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == nextButton) {
      
      // For now just show the first item
      MusicItem item = musicStorage.getNext();
      artistText.setText(item.getArtist());
      albumText.setText(item.getAlbum());
    } else if (e.getSource() == addButton) {

      String artist = artistText.getText();
      String album = albumText.getText();
      musicStorage.add(new MusicItem(artist, album));
      
      clearUserInputs();

    } 
    else if (e.getSource() == prevButton) {
      
      MusicItem item = musicStorage.getPrevious();
      artistText.setText(item.getArtist());
      albumText.setText(item.getAlbum());
    } else if (e.getSource() == fileLoad) {
    
      fileChooser.setCurrentDirectory(new File("./"));
  
      // Check if a file is selected
      if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
  
        file = fileChooser.getSelectedFile();
        try {
          musicStorage.load(file);
          
          MusicItem item = musicStorage.getFirstItem();
          artistText.setText(item.getArtist());
          albumText.setText(item.getAlbum());
        } catch (PersistenceException ex) {
          JOptionPane.showMessageDialog(frame, "Error Reading File.","Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    } else if (e.getSource() == fileSave) {

      fileChooser.setCurrentDirectory(new File("./"));
      String artist = artistText.getText();
      String album = albumText.getText();
  
      // Validate user input then check if a file is selected
      if (artist.length() != 0
          && album.length() != 0
          && fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
  
        file = fileChooser.getSelectedFile();
        musicStorage.save(file, new MusicItem(artist, album));
  
        JOptionPane.showMessageDialog(frame, "File Saved Successfully","File Saved", JOptionPane.PLAIN_MESSAGE);
      }
    } else if (e.getSource() == programExit) {
      // Thanks for all the fish!
      System.exit(0);
    } else if (e.getSource() == selectfileIO) {
      
      setStorageType(fileIOStorage);
      clearSelections();
      selectfileIO.setBackground(selectedMenuColor);
      
      clearUserInputs();
      
    } else if (e.getSource() == selectXML) {

      setStorageType(XMLStorage);
      clearSelections();
      selectXML.setBackground(selectedMenuColor);
      
      clearUserInputs();
      
    } else if (e.getSource() == selectSerial) {

      setStorageType(SerialStorage);
      clearSelections();
      selectSerial.setBackground(selectedMenuColor);
      
      clearUserInputs();
      
    } else if (e.getSource() == selectSQL) {

      setStorageType(SQLStorage);
      clearSelections();
      selectSQL.setBackground(selectedMenuColor);
      
      clearUserInputs();
    }
  }
}