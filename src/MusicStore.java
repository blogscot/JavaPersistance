

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;


public class MusicStore extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel artistLabel;
	private JTextField artistText;
	private JLabel albumLabel;
	private JTextField albumText;

	private JButton nextButton;
	private JButton prevButton;
	
  private JMenuBar menuBar;
  private JMenu fileMenu; 
  private JMenuItem fileLoad, fileSave;
	
	private JFileChooser fileChooser;
	private File file;
	
	private ArrayList<MusicItem> musicItems = new ArrayList<>();
	
	public static void main(String[] args) {
  	
  	MusicStore frame = new MusicStore();
  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	frame.setSize(250,160);
  	frame.setTitle("File I/O Example");
  	frame.setVisible(true);
  }

  public MusicStore() {
		
		fileChooser = new JFileChooser();
		prevButton = new JButton("Prev");
		nextButton = new JButton("Next");
		
		artistLabel = new JLabel("Artist Name:");
		artistText = new JTextField(10);
		albumLabel = new JLabel("Album Title:");
		albumText = new JTextField(10);
		
		setLayout(new FlowLayout());

		addMenuBar();
		
		add(artistLabel);
		add(artistText);
		add(albumLabel);
		add(albumText);
		add(prevButton);
		add(nextButton);

	}

  private void addMenuBar() {
    // Menu Bar strip
    menuBar = new JMenuBar();

    // Only one menu (i.e. contains sub-items)
    fileMenu = new JMenu("File");
    
    // We only want to load or save
    fileLoad = new JMenuItem("Load");
    fileMenu.add(fileLoad);

    fileSave = new JMenuItem("Save");
    fileMenu.add(fileSave);
    
    // Join the Menu to the Menu Bar
    menuBar.add(fileMenu);
    
    fileLoad.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        
        fileChooser.setCurrentDirectory(new File("./"));
        
        // Check if a file is selected
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          file = fileChooser.getSelectedFile();
          
          // Using try with resources, Java 7 feature
          try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String artistLine;
            
            // Read in all music items into memory
            while((artistLine = br.readLine()) != null) {
              String albumLine = br.readLine();
              musicItems.add(new MusicItem(artistLine, albumLine));
            }

            
            // For now just show the first item
            MusicItem item = musicItems.get(0);
            artistText.setText(item.getArtistName());
            albumText.setText(item.getAlbumName());
          
          } catch (IOException ex) {
            ex.printStackTrace();
          }
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
        if (artist.length() != 0 && album.length() != 0 && 
            fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
          file = fileChooser.getSelectedFile();
          
          // Using try with resources, Java 7 feature
          try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            
            bw.write(artist);
            bw.newLine();
            bw.write(album);
            bw.newLine();
            
            // Clear user input
            artistText.setText("");
            albumText.setText("");
            
          } catch (IOException ex) {
            System.out.println("File I/O error.");
          }
        }
      }
    });
    
    // Join the MenuBar to the frame
    setJMenuBar(menuBar);
    
  }
}