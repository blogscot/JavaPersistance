package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerialStorage implements Storable {
  
  private ArrayList<MusicItem> musicList = new ArrayList<>();
  private int currentItemIndex = 0;
  private int musicCollectionLength = 0;

  @SuppressWarnings("unchecked")
  @Override
  public void load(File filename) {
    
    musicList.clear();
    
    try(FileInputStream fis = new FileInputStream(filename)) {
      
      ObjectInputStream ois = new ObjectInputStream(fis);
      musicList = (ArrayList<MusicItem>) ois.readObject();
      ois.close();
      
      musicCollectionLength = musicList.size();
      
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void save(File filename, MusicItem item) {
    
    musicList.add(item);
    
    try(FileOutputStream fos = new FileOutputStream(filename)) {
      
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
    return new MusicItem("", "");
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
    return new MusicItem("", "");
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
    return new MusicItem("", "");
  }

}
