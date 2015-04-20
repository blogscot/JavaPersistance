package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 * The SerialStorage class.
 * 
 * This class loads and saves serialisation storage files. The user may also add
 * new music items to the music list.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

public class SerialStorage extends Storable {

  /**
   * Loads the file into the music list
   * 
   * @param filename
   *          the storage file
   */
  @SuppressWarnings("unchecked")
  @Override
  public void load(File filename) throws PersistenceException {

    musicList.clear();

    try (FileInputStream fis = new FileInputStream(filename)) {

      ObjectInputStream ois = new ObjectInputStream(fis);
      musicList = (ArrayList<MusicItem>) ois.readObject();
      ois.close();

      musicCollectionLength = musicList.size();

    } catch (Exception e) {
      throw new PersistenceException();
    }
  }

  /**
   * Adds a music item into the music list
   * 
   */
  @Override
  public void add(MusicItem item) {

    musicList.add(item);

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the file
   * 
   * @param filename
   *          the storage file
   */
  @Override
  public void save(File filename) {

    try (FileOutputStream fos = new FileOutputStream(filename)) {

      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(musicList);
      oos.close();

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
