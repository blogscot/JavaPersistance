package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerialStorage extends Storable {
  
  @SuppressWarnings("unchecked")
  @Override
  public void load(File filename) throws PersistenceException {
    
    musicList.clear();
    
    try(FileInputStream fis = new FileInputStream(filename)) {
      
      ObjectInputStream ois = new ObjectInputStream(fis);
      musicList = (ArrayList<MusicItem>) ois.readObject();
      ois.close();
      
      musicCollectionLength = musicList.size();
      
    } catch (Exception e) {
      throw new PersistenceException();
    }
  }

  @Override
  public void add(MusicItem item) {
    
    musicList.add(item);
    
    // Recalculate new List size
    musicCollectionLength = musicList.size();    
  }

  @Override
  public void save(File filename) {

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
}
