class MusicItem {
  private String ArtistName;
  private String AlbumName;

  public MusicItem(String artist, String album) {
    this.ArtistName = artist;
    this.AlbumName = album;
  }

  public String getArtistName() {
    return ArtistName;
  }

  public String getAlbumName() {
    return AlbumName;
  }

  @Override
  public String toString() {
    return "MusicItem [ArtistName=" + ArtistName + ", AlbumName=" + AlbumName
        + "]";
  }
}