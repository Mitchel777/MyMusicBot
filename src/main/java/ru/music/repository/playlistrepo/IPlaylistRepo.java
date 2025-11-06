package ru.music.repository.playlistrepo;

import ru.music.domain.playlist.IPlaylist;

import java.util.List;

public interface IPlaylistRepo {
    //public boolean hasNameOfPlaylistInRepo();
    public List<IPlaylist> getAllPlaylistsByUser(String UserID);

}
