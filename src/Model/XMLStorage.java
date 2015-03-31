package Model;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLStorage extends Storable {

  @Override
  public void load(File filename) throws PersistenceException {

    JAXBContext context;
    try {
      context = JAXBContext.newInstance(XMLMusicCollection.class);

      Unmarshaller unmarshaller = context.createUnmarshaller();
      XMLMusicCollection musicCollection = (XMLMusicCollection) unmarshaller
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
    item1.setYear(2015);
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
  public void add(MusicItem item) {
    // TODO Auto-generated method stub
    
  }
}
