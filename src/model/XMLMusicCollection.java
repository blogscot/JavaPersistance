package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * The XMLMusicCollection class.
 * 
 * This class contains the XML music list definition.
 * Getter and Setter methods are provided. 
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

@XmlRootElement
public class XMLMusicCollection {

  @XmlElement(name = "musicitem")
  private ArrayList<MusicItem> musicList;

  public void setMusicList(ArrayList<MusicItem> musicList) {
    this.musicList = musicList;
  }

  public ArrayList<MusicItem> getList() {
    return musicList;
  }
}
