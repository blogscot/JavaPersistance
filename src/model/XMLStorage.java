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
 * The user may also add new music items to the music list.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

public class XMLStorage extends Storable {

  /**
   * Loads the file into the music list using JAXB
   * 
   * @param filename the storage file
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
   * Loads the file into the music list using XPath
   * 
   * @param filename the storage file
   */
  @Override
  public void load(File filename) throws PersistenceException {
    
    // Clear out any old stuff first
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
      XPathExpression expr = xpath.compile("/xmlMusicCollection/musicitem/artist/text()");
      XPathExpression expr2 = xpath.compile("/xmlMusicCollection/musicitem/album/text()");
      XPathExpression expr3 = xpath.compile("/xmlMusicCollection/musicitem/year/text()");
      XPathExpression expr4 = xpath.compile("/xmlMusicCollection/musicitem/genre/text()");

      // evaluate expressions result on XML document
      NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
      NodeList nodes2 = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
      NodeList nodes3 = (NodeList) expr3.evaluate(doc, XPathConstants.NODESET);
      NodeList nodes4 = (NodeList) expr4.evaluate(doc, XPathConstants.NODESET);

      for (int i = 0; i < nodes.getLength(); i++) {
        MusicItem item = new MusicItem();
        item.setArtist(nodes.item(i).getNodeValue());
        item.setAlbum(nodes2.item(i).getNodeValue());
        item.setYear(Integer.parseInt(nodes3.item(i).getNodeValue()));
        item.setGenre(nodes4.item(i).getNodeValue());
        musicList.add(item);
      }
      musicCollectionLength = musicList.size();
      
    } catch (ParserConfigurationException | SAXException | 
        IOException |XPathExpressionException e) {
      throw new PersistenceException();
    }
  }

  /**
   * Adds a music item into the music list
   * 
   */
  @Override
  public void add(MusicItem item) {
    
    musicList.add(new MusicItem(item.getAlbum(), item.getAlbum(), item.getYear(), item.getGenre()));
    
    // Recalculate new List size    
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the file
   * 
   * @param filename the storage file
   */
  @Override
  public void save(File filename) {

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
  public void updateCurrentItem() {
    // TODO Auto-generated method stub
    
  }
}
