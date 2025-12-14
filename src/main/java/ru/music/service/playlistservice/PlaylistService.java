package ru.music.service.playlistservice;

import ru.music.domain.playlist.IPlaylist;
import ru.music.repository.playlistrepo.PlaylistRepo;
import ru.music.repository.trackrepo.TrackRepo;

import java.util.List;


public class PlaylistService {
    private final PlaylistRepo playlistRepo;
    private final TrackRepo trackRepo;

    PlaylistService(PlaylistRepo playlistRepo, TrackRepo trackRepo)
    {
        this.playlistRepo = playlistRepo;
        this.trackRepo = trackRepo;
    }

    public List<IPlaylist> getPlaylistByUserID(String userID)
    {
        return playlistRepo.getAllPlaylistsByUser(userID);
    }

    public void createPlaylist(String nameOfPlaylist, String userID)
    {
        playlistRepo.addPlaylist(nameOfPlaylist, userID);
    }

    public void removePlaylist(String nameOfPlaylist)
    {
        playlistRepo.removePlaylist(nameOfPlaylist);
    }

}
