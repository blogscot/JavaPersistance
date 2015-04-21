package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 * The SerialStorage class.
 * 
 * This class loads and saves serialisation storage files. 
 * The user may also add new music items, update existing items as well as
 * delete items in the music list (see Storable.java).
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

public class SerialStorage extends Storable {

  /**
   * Loads the specified file into the music list.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
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

    } catch (ClassNotFoundException | IOException e) {
      throw new PersistenceException();
    }
  }

  /**
   * Adds a music item into the music list.
   * 
   */
  @Override
  public void add(MusicItem item) {

    musicList.add(item);

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the specified file.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  @Override
  public void save(File filename) throws PersistenceException {

    try (FileOutputStream fos = new FileOutputStream(filename)) {

      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(musicList);
      oos.close();

    } catch ( IOException e) {
      throw new PersistenceException();
    }
  }
}
