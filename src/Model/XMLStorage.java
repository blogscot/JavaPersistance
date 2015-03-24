package Model;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLStorage implements Storable {

  ArrayList<MusicItem> musicList = new ArrayList<>();
  private int currentItemIndex = 0;
  private int musicCollectionLength = 0;

  @Override
  public void load(File filename) throws PersistenceException {

    JAXBContext context;
    try {
      context = JAXBContext.newInstance(XMLMusicCollection.class);

      Unmarshaller unmrsh = context.createUnmarshaller();
      XMLMusicCollection musicCollection = (XMLMusicCollection) unmrsh
          .unmarshal(new FileReader(filename));
      
      musicList = musicCollection.getList();
      musicCollectionLength = musicList.size();
      
    } catch (JAXBException | IOException e) {
      throw new PersistenceException();
    }
  }

  @Override
  public void save(File filename, MusicItem item) {
    
    MusicItem item1 = new MusicItem();
    item1.setArtist(item.getArtist());
    item1.setAlbum(item.getAlbum());
    item1.setYear("2015");
    item1.setGenre("progressive-rock");
    musicList.add(item1);

    // create musicCollection
    XMLMusicCollection musicCollection = new XMLMusicCollection();
    musicCollection.setMusicList(musicList);

    try {
      JAXBContext context = JAXBContext.newInstance(XMLMusicCollection.class);
      Marshaller mrsh = context.createMarshaller();
      mrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

      mrsh.marshal(musicCollection, filename);
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Returns the first MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getFirstItem() {
    return getItem(0);
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
}
