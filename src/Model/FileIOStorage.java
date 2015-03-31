package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOStorage extends Storable {

  public FileIOStorage() {
    // TODO Auto-generated constructor stub
  }

  public void load(File filename) {

    // Using try with resources, Java 7 feature
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String artistLine;
      
      // Clear the list before populating new data
      musicList.clear();

      // Read in all music items into memory
      while ((artistLine = br.readLine()) != null) {
        String albumLine = br.readLine();
        musicList.add(new MusicItem(artistLine, albumLine));
      }

      musicCollectionLength = musicList.size();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  public void add(MusicItem item) {
    musicList.add(item);
    
    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  public void save(File filename) {

    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {

      for (MusicItem item: musicList) {
        bw.write(item.getArtist());
        bw.newLine();
        bw.write(item.getAlbum());
        bw.newLine();
      }
    } catch (IOException ex) {
      System.out.println("File I/O error.");
    }
  }
}
