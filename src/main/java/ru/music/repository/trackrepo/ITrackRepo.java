package ru.music.repository.trackrepo;

import ru.music.domain.track.ITrack;

import java.util.List;

public interface ITrackRepo {
    public List<ITrack> getTracksByPlaylistId(String playlistId);
}
