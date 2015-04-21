package model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * The XMLStorage class.
 * 
 * This class loads and saves XML storage files. 
 * The user may also add new music items, update existing items as well as
 * delete items in the music list (see Storable.java).
 * 
 * @author Iain Diamond
 * @version 20/04/2015
 * 
 */

public class XMLStorage extends Storable {

  /**
   * Loads the specified file into the music list using JAXB.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  public void loadWithJaxB(File filename) throws PersistenceException {

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

  /**
   * Loads the specified file into the music list using XPath.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  @Override
  public void load(File filename) throws PersistenceException {

    // Clear the list before populating new data
    musicList.clear();

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder;
    XPath xpath = null;
    Document doc = null;

    try {
      builder = factory.newDocumentBuilder();
      doc = builder.parse(filename);

      // Create XPathFactory object
      XPathFactory xpathFactory = XPathFactory.newInstance();

      // Create XPath object
      xpath = xpathFactory.newXPath();

      // create XPathExpression object
      XPathExpression artist = xpath
          .compile("/xmlMusicCollection/musicitem/artist/text()");
      XPathExpression album = xpath
          .compile("/xmlMusicCollection/musicitem/album/text()");
      XPathExpression track = xpath
          .compile("/xmlMusicCollection/musicitem/track/text()");
      XPathExpression duration = xpath
          .compile("/xmlMusicCollection/musicitem/duration/text()");
      XPathExpression year = xpath
          .compile("/xmlMusicCollection/musicitem/year/text()");
      XPathExpression genre = xpath
          .compile("/xmlMusicCollection/musicitem/genre/text()");

      // evaluate expressions result on XML document
      NodeList artistNodes = (NodeList) artist.evaluate(doc, XPathConstants.NODESET);
      NodeList albumNodes = (NodeList) album.evaluate(doc, XPathConstants.NODESET);
      NodeList trackNodes = (NodeList) track.evaluate(doc, XPathConstants.NODESET);
      NodeList durationNodes = (NodeList) duration.evaluate(doc, XPathConstants.NODESET);
      NodeList yearNodes = (NodeList) year.evaluate(doc, XPathConstants.NODESET);
      NodeList genreNodes = (NodeList) genre.evaluate(doc, XPathConstants.NODESET);

      for (int i = 0; i < artistNodes.getLength(); i++) {
        MusicItem item = new MusicItem();
        item.setArtist(artistNodes.item(i).getNodeValue());
        item.setAlbum(albumNodes.item(i).getNodeValue());
        item.setTrack(trackNodes.item(i).getNodeValue());
        item.setDuration(durationNodes.item(i).getNodeValue());
        item.setYear(Integer.parseInt(yearNodes.item(i).getNodeValue()));
        item.setGenre(genreNodes.item(i).getNodeValue());
        musicList.add(item);
      }
      musicCollectionLength = musicList.size();

    } catch (ParserConfigurationException | SAXException | IOException
        | XPathExpressionException e) {
      throw new PersistenceException();
    }
  }

  /**
   * Adds a music item into the music list.
   * 
   */
  @Override
  public void add(MusicItem item) {

    musicList.add(new MusicItem(item.getArtist(), item.getAlbum(), 
        item.getTrack(), item.getDuration(), item.getYear(), item.getGenre()));

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the specified file using JAXB.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  @Override
  public void save(File filename) throws PersistenceException {

    // create musicCollection
    XMLMusicCollection musicCollection = new XMLMusicCollection();
    musicCollection.setMusicList(musicList);

    try {
      JAXBContext context = JAXBContext.newInstance(XMLMusicCollection.class);
      Marshaller mrsh = context.createMarshaller();
      mrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

      mrsh.marshal(musicCollection, filename);
    } catch (JAXBException e) {
      throw new PersistenceException();
    }
  }
}
