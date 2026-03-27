package ru.music.repository.playlistrepo;

import ru.music.domain.playlist.IPlaylist;
import ru.music.domain.playlist.Playlist;
import ru.music.idgenerator.IDGenerator;
import ru.music.repository.trackrepo.TrackRepo;

import java.util.*;

public class PlaylistRepo implements IPlaylistRepo
{
    private List<IPlaylist> playlistsDB;
    private IDGenerator idGenerator;
    private TrackRepo trackRepo;

    public PlaylistRepo()
    {
        playlistsDB = new ArrayList<>();
        idGenerator = new IDGenerator();
    }

    public void setTrackRepo(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public void addPlaylist(String nameOfPlaylist, String userID)
    {
        if (isPlaylistInPlaylistsDB(nameOfPlaylist) == null)
        {
            Playlist newPlaylist = new Playlist(idGenerator.generateID(), nameOfPlaylist, userID);
            playlistsDB.add(newPlaylist);

            if (trackRepo != null) {
                trackRepo.createPlaylistInTrackRepo(nameOfPlaylist);
            }

            System.out.println("Плейлист '" + nameOfPlaylist + "' успешно добавлен");
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

            if (trackRepo != null) {
                trackRepo.removePlaylistFromTrackRepo(nameOfPlaylist);
            }

            System.out.println("Плейлист '" + nameOfPlaylist + "' удален");
        }
    }

    public List<IPlaylist> getAllPlaylistsByUser(String userID)
    {
        List<IPlaylist> playlists = new ArrayList<>();
        for (IPlaylist playlist : playlistsDB)
        {
            if (playlist.getUserId().equals(userID))
            {
                playlists.add(playlist);
            }
        }
        return playlists;
    }
}