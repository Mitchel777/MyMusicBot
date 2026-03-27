package ru.music.domain.track;

public class Track implements ITrack
{
    private final String trackId;
    private final String title;
    private final String artist;

    public Track(String trackId, String title, String artist)
    {
        this.trackId = trackId;
        this.title = title;
        this.artist = artist;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArtist()
    {
        return artist;
    }

    public String getTrackId()
    {
        return trackId;
    }
}