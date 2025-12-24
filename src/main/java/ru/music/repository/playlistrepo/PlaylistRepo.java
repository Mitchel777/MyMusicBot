package ru.music.repository.playlistrepo;

import ru.music.domain.playlist.IPlaylist;
import ru.music.domain.playlist.Playlist;
import ru.music.domain.track.ITrack;
import ru.music.idgenerator.IDGenerator;

import java.util.*;

public class PlaylistRepo implements IPlaylistRepo
{

    private List<IPlaylist> playlistsDB;
    private Map<IPlaylist, List<ITrack>> playlistToTrackDB;
    private IDGenerator idGenerator;


    public PlaylistRepo()
    {
        playlistsDB = new ArrayList<IPlaylist>();
        playlistToTrackDB = new HashMap<IPlaylist, List<ITrack>>();
        idGenerator = new IDGenerator();
    }

    public Map<IPlaylist, List<ITrack>> getPlaylistToTrackDB()
    {
        return playlistToTrackDB;
    }

    public void addPlaylist(String nameOfPlaylist, String userID)
    {
        if (isPlaylistInPlaylistsDB(nameOfPlaylist) == null)
        {
            Playlist newPlaylist = new Playlist(idGenerator.generateID(), nameOfPlaylist, userID);
            playlistsDB.add(newPlaylist);
            playlistToTrackDB.putIfAbsent(newPlaylist, new ArrayList<ITrack>());
        }

        else
        {
            System.out.println("Плейлист с данным названием уже существует");
        }
    }

    public IPlaylist isPlaylistInPlaylistsDB(String nameOfPlaylist)
    {

        for (IPlaylist playlist : playlistsDB)
        {
            if (playlist.getName().equalsIgnoreCase(nameOfPlaylist))
            {
                return playlist;
            }
        }

        return null;
    }


    public void removePlaylist(String nameOfPlaylist)
    {
        IPlaylist currentPlaylist = isPlaylistInPlaylistsDB(nameOfPlaylist);
        if (currentPlaylist == null)
        {
            System.out.println("Такого плейлиста не существует");
        }

        else
        {
            playlistsDB.remove(currentPlaylist);
        }
    }



    public List<IPlaylist> getAllPlaylistsByUser(String userID)
    {
        List<IPlaylist> playlists = new ArrayList<IPlaylist>();

        for (IPlaylist playlist : playlistsDB)
        {
            if (playlist.getUserId().equals(userID))
            {
                playlists.add(playlist);
            }
        }
        if (playlists.isEmpty())
        {
            System.out.println("У пользователя нет плейлистов с треками");
        }
        return playlists;
    }
}
