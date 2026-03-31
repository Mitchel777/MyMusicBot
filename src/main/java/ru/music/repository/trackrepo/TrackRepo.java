package ru.music.repository.trackrepo;

import ru.music.domain.track.ITrack;
import ru.music.domain.track.Track;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class TrackRepo implements ITrackRepo
{
    private static final String TrackFile = "src/main/java/ru/music/repository/music.txt";

    private final HashMap<String, List<ITrack>> playlistToTrackDB; // ключ - НАЗВАНИЕ плейлиста

    public TrackRepo() {
        playlistToTrackDB = new HashMap<>();
    }

    public List<ITrack> getTracksByPlaylistId(String playlistId) {
        return new ArrayList<>();
    }

    public List<ITrack> getTracksByPlaylistName(String playlistName) {
        List<ITrack> tracks = playlistToTrackDB.get(playlistName);

        if (tracks == null) {
            System.out.println("Данного плейлиста не существует");
            return new ArrayList<>();
        }

        if (tracks.isEmpty()) {
            System.out.println("В данном плейлисте нет треков");
        }

        return tracks;
    }

    public void addTrack(String playlistName, String trackName) {
        System.out.println("🔍 addTrack вызван: playlist=" + playlistName + ", track=" + trackName);

        List<ITrack> allTracks = loadAllTracks();
        ITrack foundTrack = null;

        for (ITrack track : allTracks) {
            if (track.getTitle().equalsIgnoreCase(trackName)) {
                foundTrack = track;
                System.out.println("✅ Найден в библиотеке: " + track.getTitle());
                break;
            }
        }

        if (foundTrack == null) {
            String trackId = "custom_" + System.currentTimeMillis();
            foundTrack = new Track(trackId, trackName, "Пользовательский трек");
            System.out.println("📝 Создан пользовательский трек: " + trackName);
        }

        List<ITrack> playlist = playlistToTrackDB.computeIfAbsent(
                playlistName,
                k -> new ArrayList<>()
        );

        // Проверяем, нет ли уже такого трека в плейлисте
        for (ITrack existingTrack : playlist) {
            if (existingTrack.getTitle().equalsIgnoreCase(trackName)) {
                System.out.println("⚠️ Трек уже есть в плейлисте");
                return;
            }
        }

        playlist.add(foundTrack);
        System.out.println("✅ Трек добавлен. Теперь в плейлисте " + playlist.size() + " треков");
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
            if (track.getTitle().equalsIgnoreCase(trackName)) {
                iterator.remove();
                System.out.println("Трек '" + trackName + "' удален из плейлиста '" + playlistName + "'");
                return;
            }
        }

        System.out.println("Трек не найден: " + trackName);
    }

    public List<ITrack> loadAllTracks() {
        List<ITrack> tracks = new ArrayList<>();

        Path path = Paths.get(TrackFile);
        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + TrackFile);
            return tracks;
        }

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" - ", 3);
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String title = parts[1].trim();
                    String artist = parts[2].trim();

                    ITrack track = new Track(id, title, artist);
                    tracks.add(track);
                } else {
                    System.out.println("Некорректный формат строки: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return tracks;
    }

    public void createPlaylistInTrackRepo(String playlistName) {
        playlistToTrackDB.putIfAbsent(playlistName, new ArrayList<>());
    }

    public void removePlaylistFromTrackRepo(String playlistName) {
        playlistToTrackDB.remove(playlistName);
    }

    public List<ITrack> searchTracksByArtist(String artistName) {
        List<ITrack> allTracks = loadAllTracks();
        List<ITrack> result = new ArrayList<>();

        for (ITrack track : allTracks) {
            if (track.getArtist().toLowerCase().contains(artistName.toLowerCase())) {
                result.add(track);
            }
        }

        return result;
    }

}