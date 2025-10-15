package ru.music.domain.Track;

public class Track implements ITrack
{
    private String title;  // Название
    private String artist;
    private String LinkSong;// Исполнитель

    public Track() {}

    public  Track(String title, String artist, String LinkSong)
    {
        this.title = title;
        this.artist = artist;
        this.LinkSong = LinkSong;
    }

    public  String getTitle()
    {
        return title;
    }

    public  String getArtist()
    {
        return artist;
    }

    public  String getLinkSong()
    {
        return LinkSong;
    }
}

