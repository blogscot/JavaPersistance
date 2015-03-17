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
  public void load(File filename) {

    JAXBContext context;
    try {
      context = JAXBContext.newInstance(XMLMusicCollection.class);

      Unmarshaller unmrsh = context.createUnmarshaller();
      XMLMusicCollection musicCollection = (XMLMusicCollection) unmrsh
          .unmarshal(new FileReader(filename));
      musicList = musicCollection.getList();
      
      musicCollectionLength = musicList.size();
      
    } catch (JAXBException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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

  @Override
  public MusicItem getFirstItem() {
    return musicList.get(0);
  }

  @Override
  public MusicItem getNext() {
    return getFirstItem();
  }

  @Override
  public MusicItem getPrevious() {
    return getFirstItem();
  }

}
