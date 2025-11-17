package ru.music.repository.trackrepo;

import ru.music.domain.playlist.IPlaylist;
import ru.music.domain.track.ITrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackRepo implements ITrackRepo
{
    private HashMap<IPlaylist, List<ITrack>> playlistToTrackDB;

    public TrackRepo()
    {
        playlistToTrackDB = new HashMap<>();
    }

    public List<ITrack> getTracksByPlaylistId(String playlistId)
    {
        List<ITrack> tracks = new ArrayList<>();

        if (playlistToTrackDB.containsKey(playlistId))
        {
            for (ITrack track : playlistToTrackDB.get(playlistId))
            {
                tracks.add(track);
            }

            if (tracks.isEmpty())
            {
                System.out.println("В данном плейлисте нет треков");
            }
        }
        else
        {
            System.out.println("Данного плейлиста не существует");
        }

        return tracks;
    }
}
