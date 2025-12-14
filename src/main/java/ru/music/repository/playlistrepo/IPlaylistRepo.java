package ru.music.repository.playlistrepo;

import ru.music.domain.Playlist.IPlaylist;

import java.util.List;

public interface IPlaylistRepo<IPlaylist> {
    //public boolean hasNameOfPlaylistInRepo();
    public List<IPlaylist> getAllPlaylistsByUser(String UserID);

}
