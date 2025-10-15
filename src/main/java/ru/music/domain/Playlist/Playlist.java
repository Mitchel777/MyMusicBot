package ru.music.domain.Playlist;


public class Playlist implements IPlaylist
{
    private String userId;
    private String name;  // Название
    private String id;  // Кто создал плейлист
    private String user; // У каждого плейлиста свои пользователь

    public Playlist() {}

    public  Playlist(String userId, String name, String id)
    {
        this.userId = userId;
        this.name = name;
        this.id = id;
    }

    public String getUserId()
        {
        return userId;
        }

    public String  getName()
        {

            return name;
        }

    public String  getId()
        {
            return id;
        }

    public  String getUser()
        {
            return user;
        }
}
