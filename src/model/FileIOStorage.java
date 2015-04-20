package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * The FileIOStorage class.
 * 
 * This class loads and saves File IO storage files.
 * The user may also add new music items to the music list.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */
public class FileIOStorage extends Storable {

  public FileIOStorage() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Loads the file into the music list
   * 
   * @param filename the storage file
   */
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
        musicList.add(new MusicItem(values[0], values[1], Integer.parseInt(values[2]), values[3]));
      }

      musicCollectionLength = musicList.size();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  /**
   * Adds a music item into the music list
   * 
   */
  public void add(MusicItem item) {
    musicList.add(item);
    
    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the file
   * 
   * @param filename the storage file
   */
  public void save(File filename) {

    String line = "";
    String s = ", ";   // the comma separator
    
    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {

      for (MusicItem item: musicList) {
        line = item.getArtist() + s + item.getAlbum() + s + item.getYear() + s + item.getGenre();
        bw.write(line);
        bw.newLine();
      }
    } catch (IOException ex) {
      System.out.println("File I/O error.");
    }
  }

  @Override
  public void updateCurrentItem() {
    // TODO Auto-generated method stub
    
  }
}
