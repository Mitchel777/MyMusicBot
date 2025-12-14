package ru.music.domain.track;

public class Track implements ITrack
{
    private final String title;
    private final String artist;
    private String trackId;

    public Track(String title, String artist, String trackID)
    {
        this.title = title;
        this.artist = artist;
        this.trackId = trackID;
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

