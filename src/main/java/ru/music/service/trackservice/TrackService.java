package ru.music.service.trackservice;

import ru.music.domain.track.ITrack;
import ru.music.repository.trackrepo.TrackRepo;

import java.util.*;

public class TrackService
{
    private final TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public void addTrackToPlaylist(String playlistName, String trackName) {
        if (trackName == null || trackName.trim().isEmpty()) {
            System.out.println("❌ Название трека не может быть пустым");
            return;
        }
        trackRepo.addTrack(playlistName, trackName);
    }

    public void removeTrackFromPlaylist(String playlistName, String trackName)
    {
        trackRepo.removeTrack(playlistName, trackName);
    }

    public List<ITrack> getPlaylistTracks(String playlistName)
    {
        return trackRepo.getTracksByPlaylistName(playlistName);
    }

    public List<ITrack> showAllTracksFromFile() {
        List<ITrack> tracks = trackRepo.loadAllTracks();

        if (tracks.isEmpty()) {
            System.out.println("Нет треков");
            return tracks;
        }

        for (ITrack track : tracks)
        {
            System.out.println(track.getTrackId() + ". " +
                    track.getTitle() + " - " +
                    track.getArtist());
        }
        return tracks;
    }

    public List<ITrack> searchTracksByArtist(String artistName) {
        return trackRepo.searchTracksByArtist(artistName);
    }
}