package ru.music.domain.playlist;

public class playlist implements IPlaylist
{
    private final String playlistID;
    private final String name;
    private final String userID;

    public playlist(String playlistID, String name, String userID)
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
    public String getName()
    {
        return getName();
    }
    public String getUserId()
    {
        return userID;
    }
}