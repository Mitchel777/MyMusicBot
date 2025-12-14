package ru.music.domain.playlist;

public class Playlist implements IPlaylist
{
    private final String playlistID;
    private final String name;
    private final String userID;

    public Playlist(String playlistID, String name, String userID)
    {
        this.playlistID = playlistID;
        this.name = name;
        this.userID = userID;
    }
    public String getPlaylistId()
    {
        return playlistID;
    }

    public String getName()
    {
        return getName();
    }
    public String getUserId()
    {
        return userID;
    }
}
