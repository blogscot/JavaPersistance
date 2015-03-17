
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
import javax.swing.JTextField;

import Model.FileIOStorage;
import Model.MusicItem;
import Model.Storable;
import Model.XMLStorage;


public class MusicStore extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel artistLabel;
	private JTextField artistText;
	private JLabel albumLabel;
	private JTextField albumText;

	private JButton nextButton;
	private JButton prevButton;

	private JMenuBar menuBar;
	private JMenu fileMenu, storageMenu;
	private JMenuItem fileLoad, fileSave, programExit, selectfileIO, selectXML;

	private JFileChooser fileChooser;
	private File file;

	private Storable musicStorage;
	private Storable fileIOStorage = new FileIOStorage();
	private Storable XMLStorage = new XMLStorage();

	public static void main(String[] args) {

		MusicStore frame = new MusicStore();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(280, 160);
		frame.setTitle("My Music Collection");
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public MusicStore() {

	  fileChooser = new JFileChooser();
		prevButton = new JButton("Prev");
		nextButton = new JButton("Next");

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
		add(nextButton);

		// Let's start with File IO storage
		setStorageType(fileIOStorage);

		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// For now just show the first item
				MusicItem item = musicStorage.getNext();
				artistText.setText(item.getArtist());
				albumText.setText(item.getAlbum());
			}
		});

		prevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// For now just show the first item
				MusicItem item = musicStorage.getPrevious();
				artistText.setText(item.getArtist());
				albumText.setText(item.getAlbum());
			}
		});
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

		// We only want to load, save or exit
		fileLoad = new JMenuItem("Load");
		fileMenu.add(fileLoad);

		fileSave = new JMenuItem("Save");
		fileMenu.add(fileSave);

		programExit = new JMenuItem("Exit");
    fileMenu.add(programExit);
    
		selectfileIO = new JMenuItem("FileIO");
		selectXML = new JMenuItem("XML");
		storageMenu.add(selectfileIO);
		storageMenu.add(selectXML);

		// Join the menu to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(storageMenu);

		fileLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser.setCurrentDirectory(new File("./"));

				// Check if a file is selected
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					file = fileChooser.getSelectedFile();
					musicStorage.load(file);

					// For now just show the first item
					MusicItem item = musicStorage.getFirstItem();
					artistText.setText(item.getArtist());
					albumText.setText(item.getAlbum());
				}
			}
		});

		fileSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fileChooser.setCurrentDirectory(new File("./"));
				String artist = artistText.getText();
				String album = albumText.getText();

				// Validate user input then check if a file is selected
				if (artist.length() != 0
						&& album.length() != 0
						&& fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

					file = fileChooser.getSelectedFile();
					musicStorage.save(file, new MusicItem(artist, album));

					// Clear user input
					artistText.setText("");
					albumText.setText("");
				}
			}
		});
		
		programExit.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        
        // Thanks for all the fish!
        System.exit(0);
      }
    });
		
		selectfileIO.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        setStorageType(fileIOStorage);
        // Clear user input
        artistText.setText("");
        albumText.setText("");
      }
    });
		
		selectXML.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        setStorageType(XMLStorage);
        // Clear user input
        artistText.setText("");
        albumText.setText("");
        
      }
    });

		// Join the MenuBar to the frame
		setJMenuBar(menuBar);
	}
}