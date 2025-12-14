package ru.music.domain.Playlist;

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

    @Override
    public String name() {
        return "";
    }

    public String getName()
    {
        return name();
    }
    public String getUserId()
    {
        return userID;
    }
}