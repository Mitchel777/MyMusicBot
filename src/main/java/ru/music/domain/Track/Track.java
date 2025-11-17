package ru.music.domain.Track;

public class Track implements ITrack
{
    private final String title;
    private final String artist;
    private String TrackId;

    public  Track(String title, String artist)
    {
        this.title = title;
        this.artist = artist;
    }

    public  String getTitle()
    {
        return title;
    }

    public  String getArtist()
    {
        return artist;
    }

    public String getTrackId()
    {
        return TrackId;
    }
}

