package ru.music.domain.PlaylistToTrack;

public class PlaylistToTrack implements IPlaylistToTrack
{
    private String id;
    private String trackId;
    private String playlistId;

    public PlaylistToTrack() {}

    public PlaylistToTrack(String id, String trackId, String playlistId)
    {
        this.id = id;
        this.trackId = trackId;
        this.playlistId = playlistId;
    }

    public String getId()
    {
        return id;
    }

    public String getTrackId()
    {
        return trackId;
    }

    public String getPlaylistId()
    {
        return playlistId;
    }
}
