package model;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * The MusicItem class.
 * 
 * This class contains the data definition for a music item.
 * Each item can be stored using File IO, XML, Serialisation,
 * and within an SQL database.
 * 
 * @author Iain Diamond
 * @version 10/04/2015
 * 
 */

@XmlRootElement(name = "MusicItem")
@XmlType(propOrder = { "artist", "album", "year", "genre" })
public class MusicItem implements Serializable {
  
  private static final long serialVersionUID = 1L;
  private String artist;
  private String album;
  private int year;
  private String genre;

  // Default constructor
  public MusicItem() {}
  
  public MusicItem(String artist, String album, int year, String genre) {
    this.artist = artist;
    this.album = album;
    this.year = year;
    this.genre = genre;
  }

  public String getArtist() {
    return artist;
  }
  public void setArtist(String artist) {
    this.artist = artist;
  }
  public String getAlbum() {
    return album;
  }
  public void setAlbum(String album) {
    this.album = album;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public String getGenre() {
    return genre;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }

  @Override
  public String toString() {
    return "MusicItem [artist=" + artist + ", album=" + album + ", year="
        + year + ", genre=" + genre + "]";
  }
}