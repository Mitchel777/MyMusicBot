package ru.music.service.playlistservice;

import ru.music.domain.playlist.IPlaylist;
import ru.music.repository.playlistrepo.PlaylistRepo;

import java.util.List;


public class PlaylistService {
    private final PlaylistRepo playlistRepo;

    public PlaylistService(PlaylistRepo playlistRepo)
    {
        this.playlistRepo = playlistRepo;
    }

    public List<IPlaylist> getPlaylistsByUserID(String userID)
    {
        return playlistRepo.getAllPlaylistsByUser(userID);
    }

    public void createPlaylist(String nameOfPlaylist, String userID)
    {
        playlistRepo.addPlaylist(nameOfPlaylist, userID);
    }

    public void removePlaylist(String nameOfPlaylist, String userID)
    {
        playlistRepo.removePlaylist(nameOfPlaylist);
    }

}
