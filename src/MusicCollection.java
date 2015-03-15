import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class MusicCollection {

  private ArrayList<MusicItem> musicItems = new ArrayList<>();
  
  public MusicCollection() {
    // TODO Auto-generated constructor stub
  }
  
  public void setStorageType() {}
  
  public void load(File filename) {
    
    // Using try with resources, Java 7 feature
    try (BufferedReader br = new BufferedReader(new FileReader(filename))){
      String artistLine;
      
      // Read in all music items into memory
      while((artistLine = br.readLine()) != null) {
        String albumLine = br.readLine();
        musicItems.add(new MusicItem(artistLine, albumLine));
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  public void save(File filename, MusicItem item) {

    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
      
      bw.write(item.getArtistName());
      bw.newLine();
      bw.write(item.getAlbumName());
      bw.newLine();
      
    } catch (IOException ex) {
      System.out.println("File I/O error.");
    }
  }
  
  public MusicItem getItem(int index) {
    
    // Check the user isn't doing something stupid (i.e. me)
    if (index < musicItems.size()) {
      return musicItems.get(index);
    }
    return new MusicItem("Unknown Artist","Unknown Album");
  }
}
