package model;

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

    String line = "";
    String values[];

    // Using try with resources, Java 7 feature
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      
      // Clear the list before populating new data
      musicList.clear();

      // Read in all music items into memory
      while ((line = br.readLine()) != null) {
        values = line.split(", ");
        musicList.add(new MusicItem(values[0], values[1]));
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

    String line = "";
    String s = ", ";   // the comma separator
    
    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {

      for (MusicItem item: musicList) {
        // TODO Replace fields with real data
        line = item.getArtist() + s + item.getAlbum() + s + "1976" + s + "Rock";
        bw.write(line);
        bw.newLine();
      }
    } catch (IOException ex) {
      System.out.println("File I/O error.");
    }
  }
}
