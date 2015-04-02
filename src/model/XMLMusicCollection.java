package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
