package builder;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FileIOStorage;
import model.MusicItem;
import model.PersistenceException;
import model.SQLStorage;
import model.SerialStorage;
import model.Storable;
import model.XMLStorage;

/**
 * 
 * The Controller class.
 * 
 * This class constructs the Java Persistence GUI and adds action
 * listeners for the menu items and buttons.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */
public class Controller implements Initializable {
  
  // The Storage types
  private Storable musicStorage;
  private Storable fileIOStorage = new FileIOStorage();
  private Storable XMLStorage = new XMLStorage();
  private Storable SerialStorage = new SerialStorage();
  private Storable SQLStorage = new SQLStorage();  
  
  private Stage stage;
  private FileChooser fileChooser = new FileChooser();

  @FXML
  private TextField artistText;
  
  @FXML
  private TextField albumText;
  
  @FXML
  private TextField yearText;
  
  @FXML 
  private TextField genreText;
  

/**
   * The user selected load.
   * 
   *  Get a filename from the user and load the file contents 
   *  using the current storage type.
   * 
   */
  public void load() {

    fileChooser.setTitle("Open File");
    fileChooser.setInitialDirectory(new File("./"));
    File filename = fileChooser.showOpenDialog(stage);
    
    if (filename != null) {
      
      try {
        musicStorage.load(filename);
        
        MusicItem item = musicStorage.getFirstItem();
        displayItem(item);
      } catch (PersistenceException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Load File Error");
        alert.setContentText("There was an error loading: " + filename);
        alert.showAndWait();
      }
    }
  }
  
  /**
   * The user selected save.
   * 
   *  Get a filename from the user and save the file using the 
   *  current storage type.
   * 
   */
  public void save() {
    fileChooser.setTitle("Save File");
    fileChooser.setInitialDirectory(new File("./"));
    File filename = fileChooser.showSaveDialog(stage);
    
    if (filename != null) {
      musicStorage.save(filename);
      
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("File Save Successful");
      alert.showAndWait();
    }
  }
  
  /**
   * The user has quit the application.
   * 
   */
  public void exit() {
    System.exit(0);
  }
  
  
  /**
   * The user has selected File IO storage type.
   * 
   */
  public void fileIO() {
    setStorageType(fileIOStorage);
  }
  
  /**
   * The user has selected XML storage type.
   * 
   */
  public void xml() {
    setStorageType(XMLStorage);
  }
 
  /**
   * The user has selected serialisation storage type.
   * 
   */
  public void serial() {
    setStorageType(SerialStorage);
  }
  
  /**
   * The user has selected SQLite storage type.
   */
  public void sqlite() {
    setStorageType(SQLStorage);
  }
  
/**
 * Builds a MusicItem using the JFX text fields
 *   
 * @return a new music item object
 */
  private MusicItem getCurrentItem() {
    
    String artist = artistText.getText();
    String album = albumText.getText();
    int year = Integer.parseInt(yearText.getText());
    String genre = genreText.getText();
    
    return new MusicItem(artist, album, year, genre);
  }
  /**
   * Adds a new music item to the list.
   * 
   */
  public void add() {

    if (isUserInputValid()) {
      musicStorage.add(getCurrentItem());
      clearUserInputs();
    }
  }
  
  /**
   * Updates the current music item
   * 
   */
  public void update() {
    // Prevent the user from trying to edit a empty item
    if (isUserInputValid()) {
      musicStorage.updateCurrentItem(getCurrentItem());
    }
  }
  
  /**
   * Deletes a music item from the list
   * 
   */
  public void delete() {
    
    // Prevent the user from trying to delete a empty item
    if (isUserInputValid()) {
      musicStorage.removeCurrentItem();
      displayItem(musicStorage.getPrevious());
    }
  }

  /**
   * Clear the GUI TextFields
   * 
   */
  public void clear() {
    clearUserInputs();
  }
  
  /**
   * Displays the next music item in the list.
   * 
   */
  public void next() {
    displayItem(musicStorage.getNext());
  }

  public void previous() {
    displayItem(musicStorage.getPrevious());
  }

  /**
   * Sets the default storage type
   * 
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    setStorageType(fileIOStorage);
  }

  /**
   * This method is called by the main class to set the application
   * stage instance, used by the fileChooser
   * 
   * @param stage the main application stage
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * Updates the GUI TextFields with properties of 
   * the music item parameter.
   * 
   * @param item a music item
   */
  private void displayItem(MusicItem item) {
    artistText.setText(item.getArtist());
    albumText.setText(item.getAlbum());
    yearText.setText(Integer.toString(item.getYear()));
    genreText.setText(item.getGenre());
  }
  
  /**
   * Validates the GUI TextFields
   * 
   * @return true when all fields are set
   */
  private boolean isUserInputValid() {
      String artist = artistText.getText();
      String album = albumText.getText();
      String year = yearText.getText();
      String genre = genreText.getText();
      
      if (artist.length() != 0 && album.length() != 0
          && year.length() != 0 && genre.length() != 0) { 
        return true; 
      }
      return false;
    }

  /**
   * Clears the GUI Textfields
   * 
   */
  private void clearUserInputs() {
    artistText.setText("");
    albumText.setText("");
    yearText.setText("");
    genreText.setText("");
  }

  /**
   * Sets the class field with the storage type
   * 
   * @param store the storage type
   */
    private void setStorageType(Storable store) {
      this.musicStorage = store;
    }
}
