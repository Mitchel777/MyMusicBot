package ru.music.domain.Track;

import java.util.UUID;

public class Track implements ITrack
{
    private final String title;
    private final String TrackId;
    private final String artist;

    public  Track(String title, String artist, String TrackId)
    {
        this.title = title;
        this.TrackId = TrackId;
        this.artist = artist;
    }

    public  String getTitle()
    {
        return title;
    }

    public String getTrackId()
    {
        return TrackId;
    }

    public String getArtist()
    {
        return artist;
    }
}

