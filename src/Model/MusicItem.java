package Model;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "MusicItem")
@XmlType(propOrder = { "artist", "album", "year", "genre" })
public class MusicItem {
  
  private String artist;
  private String album;
  private String year;
  private String genre;

  // Default constructor
  public MusicItem() {}
  
  public MusicItem(String artist, String album) {
    this.artist = artist;
    this.album = album;
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
  public String getYear() {
    return year;
  }
  public void setYear(String year) {
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
    return "MusicItem [ArtistName=" + artist + ", AlbumName=" + album
        + "]";
  }
}