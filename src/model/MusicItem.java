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
 * and within an SQLite database.
 * 
 * @author Iain Diamond
 * @version 20/04/2015
 * 
 */

@XmlRootElement(name = "MusicItem")
@XmlType(propOrder = { "artist", "album", "track", "duration", "year", "genre" })
public class MusicItem implements Serializable {
  
  private static final long serialVersionUID = 1L;
  private String artist;
  private String album;
  private String track;
  private String duration;
  private int year;
  private String genre;

  // Default constructor
  public MusicItem() {}
  
  public MusicItem(String artist, String album, String track, String duration, int year, String genre) {
    this.artist = artist;
    this.album = album;
    this.track = track;
    this.duration = duration;
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
  public String getTrack() {
    return track;
  }
  public void setTrack(String track) {
    this.track = track;
  }
  public String getDuration() {
    return duration;
  }
  public void setDuration(String duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return "MusicItem [artist=" + artist + ", album=" + album + ", track="
        + track + ", duration=" + duration + ", year=" + year + ", genre="
        + genre + "]";
  }
}