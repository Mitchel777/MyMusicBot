package ru.music.app;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.music.domain.playlist.IPlaylist;
import ru.music.domain.track.ITrack;
import ru.music.repository.playlistrepo.PlaylistRepo;
import ru.music.repository.trackrepo.TrackRepo;
import ru.music.service.playlistservice.PlaylistService;
import ru.music.service.trackservice.TrackService;

import java.util.List;

public class App extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;
    private final TrackService trackService;
    private final PlaylistService playlistService;
    private final PlaylistRepo playlistRepo;

    public App(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;

        TrackRepo trackRepo = new TrackRepo();
        this.playlistRepo = new PlaylistRepo();
        this.playlistRepo.setTrackRepo(trackRepo);

        this.trackService = new TrackService(trackRepo);
        this.playlistService = new PlaylistService(playlistRepo, trackRepo);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.startsWith("/")) {
                processCommand(chatId, messageText);
            }
        }
    }

    private void processCommand(long chatId, String command) {
        String[] parts = command.split(" ", 2);
        String cmd = parts[0];
        String args = parts.length > 1 ? parts[1] : "";
        String userId = "user_" + chatId;

        switch (cmd) {
            case "/start":
                sendMessage(chatId, getWelcomeMessage());
                break;

            case "/tracks":
                showAllTracks(chatId);
                break;

            case "/playlists":
                showAllPlaylists(chatId, userId);
                break;

            case "/createplaylist":
                if (args.isEmpty()) {
                    sendMessage(chatId, "📝 Введите название плейлиста:\nПример: /createplaylist Мои любимые");
                } else {
                    createPlaylist(chatId, args, userId);
                }
                break;

            case "/deleteplaylist":
                if (args.isEmpty()) {
                    sendMessage(chatId, "🗑 Введите название плейлиста:\nПример: /deleteplaylist Мои любимые");
                } else {
                    deletePlaylist(chatId, args, userId);
                }
                break;

            case "/playlisttracks":
                if (args.isEmpty()) {
                    sendMessage(chatId, "📋 Введите название плейлиста:\nПример: /playlisttracks Мои любимые");
                } else {
                    showPlaylistTracks(chatId, args, userId);
                }
                break;

            case "/add":
                if (args.isEmpty()) {
                    sendMessage(chatId, "➕ Используйте: /add [плейлист] [трек]\nПример: /add Мои любимые Bohemian Rhapsody");
                } else {
                    addTrack(chatId, args, userId);
                }
                break;

            case "/remove":
                if (args.isEmpty()) {
                    sendMessage(chatId, "➖ Используйте: /remove [плейлист] [трек]\nПример: /remove Мои любимые Bohemian Rhapsody");
                } else {
                    removeTrack(chatId, args, userId);
                }
                break;

            case "/search":
                searchByArtist(chatId, args);
                break;

            case "/help":
                sendMessage(chatId, getHelpMessage());
                break;

            default:
                sendMessage(chatId, "❌ Неизвестная команда. Используйте /help");
                break;
        }
    }

    private void showAllTracks(long chatId) {
        List<ITrack> tracks = trackService.showAllTracksFromFile();

        if (tracks.isEmpty()) {
            sendMessage(chatId, "📀 Нет доступных треков");
            return;
        }

        StringBuilder message = new StringBuilder("🎵 *Доступные треки:*\n\n");
        for (ITrack track : tracks) {
            message.append(String.format("%s. *%s* - %s\n",
                    track.getTrackId(), track.getTitle(), track.getArtist()));
        }

        sendMessage(chatId, message.toString());
    }

    private void showAllPlaylists(long chatId, String userId) {
        List<IPlaylist> playlists = playlistService.getPlaylistByUserID(userId);

        if (playlists.isEmpty()) {
            sendMessage(chatId, "📭 У вас нет плейлистов. Создайте первый с помощью /createplaylist");
            return;
        }

        StringBuilder message = new StringBuilder("📁 *Ваши плейлисты:*\n\n");
        for (int i = 0; i < playlists.size(); i++) {
            message.append(String.format("%d. %s\n", i + 1, playlists.get(i).getName()));
        }

        sendMessage(chatId, message.toString());
    }

    private void createPlaylist(long chatId, String playlistName, String userId) {
        playlistService.createPlaylist(playlistName, userId);
        sendMessage(chatId, "✅ Плейлист *" + playlistName + "* успешно создан!");
    }

    private void deletePlaylist(long chatId, String playlistName, String userId) {
        List<IPlaylist> playlists = playlistService.getPlaylistByUserID(userId);
        boolean exists = playlists.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(playlistName));

        if (!exists) {
            sendMessage(chatId, "❌ Плейлист *" + playlistName + "* не найден");
            return;
        }

        playlistService.removePlaylist(playlistName);
        sendMessage(chatId, "🗑 Плейлист *" + playlistName + "* удален");
    }

    private void showPlaylistTracks(long chatId, String playlistName, String userId) {
        System.out.println("🔍 showPlaylistTracks вызван: " + playlistName);

        List<IPlaylist> playlists = playlistService.getPlaylistByUserID(userId);
        boolean exists = false;
        for (IPlaylist playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(playlistName)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            sendMessage(chatId, "❌ Плейлист *" + playlistName + "* не найден");
            return;
        }

        List<ITrack> tracks = trackService.getPlaylistTracks(playlistName);
        System.out.println("🔍 Получено треков: " + tracks.size());

        if (tracks.isEmpty()) {
            sendMessage(chatId, "📭 В плейлисте *" + playlistName + "* пока нет треков");
            return;
        }

        StringBuilder message = new StringBuilder("🎵 *Треки в плейлисте " + playlistName + ":*\n\n");
        for (int i = 0; i < tracks.size(); i++) {
            ITrack track = tracks.get(i);
            message.append(String.format("%d. *%s* - %s\n", i + 1, track.getTitle(), track.getArtist()));
        }

        sendMessage(chatId, message.toString());
    }

    private void addTrack(long chatId, String args, String userId) {
        String[] params = args.split(" ", 2);

        if (params.length < 2) {
            sendMessage(chatId, "❌ Используйте: /add [плейлист] [название трека]\n" +
                    "Пример: /add Мои любимые Bohemian Rhapsody");
            return;
        }

        String playlistName = params[0];
        String trackName = params[1];

        if (!playlistRepo.playlistExists(playlistName)) {
            sendMessage(chatId, "❌ Плейлист *" + playlistName + "* не существует");
            return;
        }

        trackService.addTrackToPlaylist(playlistName, trackName);
        sendMessage(chatId, "➕ Трек *" + trackName + "* добавлен в плейлист *" + playlistName + "*");
    }

    private void removeTrack(long chatId, String args, String userId) {
        String[] params = args.split(" ", 2);

        if (params.length < 2) {
            sendMessage(chatId, "❌ Используйте: /remove [плейлист] [название трека]\n" +
                    "Пример: /remove Мои любимые Bohemian Rhapsody");
            return;
        }

        String playlistName = params[0];
        String trackName = params[1];

        if (!playlistRepo.playlistExists(playlistName)) {
            sendMessage(chatId, "❌ Плейлист *" + playlistName + "* не существует");
            return;
        }

        List<ITrack> playlistTracks = trackService.getPlaylistTracks(playlistName);
        boolean trackInPlaylist = false;
        for (ITrack track : playlistTracks) {
            if (track.getTitle().equalsIgnoreCase(trackName)) {
                trackInPlaylist = true;
                break;
            }
        }

        if (!trackInPlaylist) {
            sendMessage(chatId, "❌ Трек *" + trackName + "* не найден в плейлисте *" + playlistName + "*\n\n" +
                    "📋 Используйте /playlisttracks " + playlistName + " для просмотра треков");
            return;
        }

        trackService.removeTrackFromPlaylist(playlistName, trackName);
        sendMessage(chatId, "➖ Трек *" + trackName + "* удален из плейлиста *" + playlistName + "*");
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("Markdown");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }

    private void searchByArtist(long chatId, String args) {
        if (args.trim().isEmpty()) {
            sendMessage(chatId, "🔍 Используйте: /search [имя исполнителя]\n" +
                    "Пример: /search Queen\n\n" +
                    "Также можно искать по части имени:\n" +
                    "/search Beat");
            return;
        }

        String artistName = args.trim();
        List<ITrack> tracks = trackService.searchTracksByArtist(artistName);

        if (tracks.isEmpty()) {
            sendMessage(chatId, "❌ Не найдено треков исполнителя *" + artistName + "*");
            return;
        }

        StringBuilder message = new StringBuilder("🔍 *Результаты поиска по исполнителю \"" + artistName + "\":*\n\n");
        for (int i = 0; i < tracks.size(); i++) {
            ITrack track = tracks.get(i);
            message.append(String.format("%d. *%s* - %s\n", i + 1, track.getTitle(), track.getArtist()));
        }

        sendMessage(chatId, message.toString());
    }

    private String getWelcomeMessage() {
        return "*/tracks* - показать все доступные треки\n" +
                "*/search [исполнитель]* - найти треки по исполнителю (только из библиотеки)\n" +
                "   Пример: */search Queen*\n" +
                "*/playlists* - показать все ваши плейлисты\n" +
                "*/createplaylist [название]* - создать новый плейлист\n" +
                "*/deleteplaylist [название]* - удалить плейлист\n" +
                "*/playlisttracks [название]* - показать треки в плейлисте\n" +
                "*/add [плейлист] [трек]* - добавить трек в плейлист\n" +
                "*/remove [плейлист] [трек]* - удалить трек из плейлиста\n" +
                "*/help* - показать эту справку";
    }

    private String getHelpMessage() {
        return "📚 *Помощь по командам:*\n\n" +
                "*/tracks* - показать все доступные треки\n" +
                "*/search [исполнитель]* - найти треки по исполнителю (только из библиотеки)\n" +
                "   Пример: */search Queen*\n" +
                "*/playlists* - показать все ваши плейлисты\n" +
                "*/createplaylist [название]* - создать новый плейлист\n" +
                "*/deleteplaylist [название]* - удалить плейлист\n" +
                "*/playlisttracks [название]* - показать треки в плейлисте\n" +
                "*/add [плейлист] [трек]* - добавить трек в плейлист\n" +
                "*/remove [плейлист] [трек]* - удалить трек из плейлиста\n" +
                "*/help* - показать эту справку";
    }
}
