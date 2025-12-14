package ru.music.repository.playlistrepo;

import ru.music.domain.Playlist.IPlaylist;

import java.util.*;

public class PlaylistRepo implements IPlaylistRepo
{

    private HashMap<String, List<IPlaylist>> playlistsDB;


    public PlaylistRepo()
    {
        playlistsDB = new HashMap<>();
    }

    public List<IPlaylist> getAllPlaylistsByUser(String UserID)
    {
        List<IPlaylist> playlists = new ArrayList<IPlaylist>();

        for (IPlaylist playlist : playlistsDB.get(UserID))
        {
            playlists.add(playlist);
        }
        if (playlists.isEmpty())
        {
            System.out.println("У пользователя нет плейлистов с треками");
        }
        return playlists;
    }
}
