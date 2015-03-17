package Model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="jaxb-xml-model")
public class XMLMusicCollection {

  @XmlElementWrapper(name = "musicList")
  @XmlElement(name = "musicItem")
  private ArrayList<MusicItem> musicList;

  public void setMusicList(ArrayList<MusicItem> musicList) {
    this.musicList = musicList;
  }

  public ArrayList<MusicItem> getList() {
    return musicList;
  }
}
