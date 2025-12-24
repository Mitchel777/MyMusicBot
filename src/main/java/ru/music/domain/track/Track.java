package ru.music.domain.Track;

public class Track implements ITrack
{
    private final String title;
    private final String artist;
    private String TrackId;

    public Track(String title, String artist, String trackID)
    {
        this.title = title;
        this.artist = artist;
        this.TrackId = trackID;
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

