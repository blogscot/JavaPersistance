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

public class Controller implements Initializable {
  
  private Storable musicStorage;
  private Storable fileIOStorage = new FileIOStorage();
  private Storable XMLStorage = new XMLStorage();
  private Storable SerialStorage = new SerialStorage();
  private Storable SQLStorage = new SQLStorage();  
  
  Stage stage;
  FileChooser fileChooser = new FileChooser();

  @FXML
  private TextField artistText;
  
  @FXML
  private TextField albumText;
  
  @FXML
  private TextField yearText;
  
  @FXML 
  private TextField genreText;
  
  
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

private void clearUserInputs() {
  artistText.setText("");
  albumText.setText("");
  yearText.setText("");
  genreText.setText("");
}

  private void setStorageType(Storable store) {
    this.musicStorage = store;
  }
  
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
  
  public void exit() {
    System.exit(0);
  }
  
  public void fileIO() {
    setStorageType(fileIOStorage);
  }
  
  public void xml() {
    setStorageType(XMLStorage);
  }
  
  public void serial() {
    setStorageType(SerialStorage);
  }
  
  public void sqlite() {
    setStorageType(SQLStorage);
  }
  
  public void add() {

    if (isUserInputValid()) {
      String artist = artistText.getText();
      String album = albumText.getText();
      int year = Integer.parseInt(yearText.getText());
      String genre = genreText.getText();

      musicStorage.add(new MusicItem(artist, album, year, genre));
      clearUserInputs();
    }
  }
  
  public void delete() {}

  public void clear() {
    clearUserInputs();
  }
  
  public void next() {
    displayItem(musicStorage.getNext());
  }

  private void displayItem(MusicItem item) {
    artistText.setText(item.getArtist());
    albumText.setText(item.getAlbum());
    yearText.setText(Integer.toString(item.getYear()));
    genreText.setText(item.getGenre());
  }
  
  public void previous() {
    displayItem(musicStorage.getPrevious());
  }
  
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    setStorageType(fileIOStorage);
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
