package ru.music.repository.trackrepo;

import ru.music.domain.Track.ITrack;
import ru.music.domain.Track.Track;

import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class TrackRepo implements ITrackRepo
{
    private static final String TrackFile = "repository/music.txt";

    private final HashMap<String, List<ITrack>> playlistToTrackDB;

    public TrackRepo() {
        playlistToTrackDB = new HashMap<String, List<ITrack>>();
    }

    public List<ITrack> getTracksByPlaylistId(String playlistId) {
        List<ITrack> tracks = new ArrayList<>();

        if (playlistToTrackDB.containsKey(playlistId)) {
            for (ITrack track : playlistToTrackDB.get(playlistId)) {
                tracks.add(track);
            }

            if (tracks.isEmpty()) {
                System.out.println("В данном плейлисте нет треков");
            }
        } else {
            System.out.println("Данного плейлиста не существует");
        }

        return tracks;
    }

    public void addTrack(String playlistName, String trackName)
    {
        List<ITrack> allTracks = loadAllTracks();
        ITrack foundTrack = null;

        for (ITrack track : allTracks)
        {
            if (track.getTitle().equals(trackName))
            {
                track = foundTrack;
            }
        }
        if (allTracks == null)
        {
            System.out.println("Трек не найден");
        }
        List<ITrack> tracks = playlistToTrackDB.get(playlistName);
        if (tracks == null) {
            tracks = new ArrayList<>();
            playlistToTrackDB.put(playlistName, tracks);
        }
        for (ITrack existingTrack : tracks) {
            if (existingTrack.getTitle().equals(trackName)) {
                System.out.println("Трек уже есть в плейлисте");
                return;
            }
        }

        tracks.add(foundTrack);
        System.out.println("Трек " + trackName + " добавлен в плейлист " + playlistName);
    }

    public void removeTrack(String playlistName, String trackName) {
        List<ITrack> tracks = playlistToTrackDB.get(playlistName);
        if (tracks == null) {
            System.out.println("Плейлист не найден: " + playlistName);
            return;
        }

        Iterator<ITrack> iterator = tracks.iterator();
        while (iterator.hasNext()) {
            ITrack track = iterator.next();
            if (track.getTitle().equals(trackName)) {
                iterator.remove();
                System.out.println("Трек " + trackName + " удален из плейлиста " + playlistName);
                return;
            }
        }

        System.out.println("Трек не найден: " + trackName);
    }

    public List<ITrack> loadAllTracks() {
        List<ITrack> tracks = new ArrayList<>();

        Path path = Paths.get(TrackFile);
        if (!Files.exists(path)) {
            System.out.println("Файл не найден");
            return tracks;
        }

        List<String> lines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(" - ");
            if (parts.length >= 3) {
                String id = parts[0].trim();
                String title = parts[1].trim();
                String artist = parts[2].trim();

                ITrack track = new Track(id, title, artist);
                tracks.add(track);
            }
        }
        return tracks;
    }

}
