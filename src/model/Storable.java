package model;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * The Storable abstract class.
 * 
 * This class provides the abstract files for a Storable type class, i.e.
 * load and save storage files, plus add music items to a music list.
 * 
 * Methods for indexing through the music list are provided. 
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

public abstract class Storable {
  
  protected ArrayList<MusicItem> musicList = new ArrayList<>();
  protected int currentItemIndex = 0;
  protected int musicCollectionLength = 0;

  public abstract void load(File filename) throws PersistenceException;
  public abstract void add(MusicItem item);
  public abstract void save(File filename);

  /**
   * Returns the first MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getFirstItem() {
    return getItem(0);
  }
  
  /**
   * Returns the MusicItem at the index position
   * 
   * @param index
   *          the MusicCollection index
   * @return a MusicItem instance
   */
  private MusicItem getItem(int index) {

    // Check a collection exists and the index is valid
    if (musicCollectionLength > 0 && index < musicCollectionLength) {
      return musicList.get(index);
    }
    System.err.println("MusicCollection: Invalid index value: " + index);
    return new MusicItem("", "", 0, "");
  }

  /**
   * Returns the next MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getNext() {

    if (musicCollectionLength > 0) {

      // increment and wrap the index
      // Note: modulus by 0 is not pretty
      currentItemIndex = ++currentItemIndex % musicCollectionLength;
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "", 0, "");
  }

  /**
   * Returns the previous MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getPrevious() {

    if (musicCollectionLength > 0) {
      if (--currentItemIndex < 0) {

        // wrap the index
        currentItemIndex = musicCollectionLength - 1;
      }
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "", 0, "");
  }
}
