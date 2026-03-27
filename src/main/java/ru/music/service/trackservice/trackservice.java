package ru.music.service.trackservice;

import ru.music.domain.track.ITrack;
import ru.music.repository.trackrepo.TrackRepo;

import java.util.*;

public class trackservice
{
    private final TrackRepo trackRepo;

    public trackservice(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public void addTrackToPlaylist(String playlistName, String trackName)
    {
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
}