package ru.music.repository.trackrepo;

import ru.music.domain.Track.ITrack;

import java.util.List;

public interface ITrackRepo {
    public List<ITrack> getTracksByPlaylistId(String playlistId);
}
