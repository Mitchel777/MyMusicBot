package ru.music.domain.Playlist;


public class Playlist implements IPlaylist
{
    private String UserId;
    private String name;  // Название
    private String PlaylistId;  //

    public Playlist() {}

    public  Playlist(String UserId, String name, String PlaylistId)
    {
        this.UserId = UserId;
        this.name = name;
        this.PlaylistId = PlaylistId;
    }

    public String getUserId()
        {
        return UserId;
        }

    public String  getName()
        {

            return name;
        }

    public String getPlaylistId()
        {
            return PlaylistId;
        }
}
