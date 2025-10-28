package ru.music.domain.Track;

public class Track implements ITrack
{
    private String title;
    private String artist;
    private String LinkSong;
    private String TrackId;

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
    public String getTrackId()
    {
        return TrackId;
    }
}

