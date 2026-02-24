package ru.music.app;

import ru.music.domain.track.ITrack;
import ru.music.repository.playlistrepo.PlaylistRepo;
import ru.music.repository.trackrepo.TrackRepo;
import ru.music.service.playlistservice.PlaylistService;
import ru.music.service.trackservice.TrackService;

import java.util.Scanner;

public class App {

    private final TrackService trackService;
    private final PlaylistService playlistService;
    private final Scanner scanner;
    private boolean isRunning;

    public App() {
        TrackRepo trackRepo = new TrackRepo();
        PlaylistRepo playlistRepo = new PlaylistRepo();

        this.trackService = new TrackService(trackRepo);
        this.playlistService = new PlaylistService(playlistRepo);

        this.scanner = new Scanner(System.in);
        this.isRunning = true;

        start();
    }

    public void start() {
        showStartMenu();

        while (isRunning) {
            System.out.print("\nВведите команду: ");
            String command = scanner.nextLine().trim();

            processCommand(command);
        }

        scanner.close();
        System.out.println("До свидания!");
    }

    private void processCommand(String command) {
        if (command.startsWith("/")) {
            executeBotCommand(command);
        } else {
            System.out.println(" Команда должна начинаться с /");
        }
    }

    private void executeBotCommand(String command) {
        String[] parts = command.split(" ", 2);
        String cmd = parts[0];
        String args = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "/start":
                showStartMenu();
                break;

            case "/tracks":
                showAllTracks();
                break;

            case "/addtrack":
                addTrack(args);
                break;

            case "/removetrack":
                removeTrack(args);
                break;

            case "/createplaylist":
                createPlaylist(args);
                break;

            case "/playlist":
                showAllPlaylists();
                break;

            case "/removeplaylist":
                removePlaylist(args);
                break;

            case "/help":
                showHelp();
                break;

            case "/exit":
                isRunning = false;
                break;

            default:
                System.out.println(" Неизвестная команда. Используйте /help");
                break;
        }
    }

    private void showStartMenu() {
        System.out.println(" Добро пожаловать в Music App!");
        System.out.println("   /tracks - показать все треки");
        System.out.println("   /addtrack [плейлист] [трек] - добавить трек");
        System.out.println("   /removetrack [плейлист] [трек] - удалить трек");
        System.out.println("   /createplaylist [плейлист] - создать новый плейлист");
        System.out.println("   /playlist [плейлист] - показать все плейлисты");
        System.out.println("   /removeplaylist [плейлист] - удалить плейлист");
        System.out.println("   /help - помощь");
        System.out.println("   /exit - выход");
    }

    private void showAllTracks() {
        System.out.println(" Загружаю треки из файла...");
        trackService.showAllTracksFromFile();
    }


    private void addTrack(String args) {
        String[] params = args.split(" ", 2);

        if (params.length < 2) {
            System.out.println(" Используйте: /add [плейлист] [название трека]");
            System.out.println("   Пример: /add Рок-хиты Bohemian Rhapsody");
            return;
        }

        String playlistName = params[0];
        String trackName = params[1];

        trackService.addTrackToPlaylist(playlistName, trackName);
    }

    private void removeTrack(String args) {
        String[] params = args.split(" ", 2);

        if (params.length < 2) {
            System.out.println(" Используйте: /removetrack [плейлист] [название трека]");
            System.out.println("   Пример: /remove Рок-хиты Hotel California");
            return;
        }

        String playlistName = params[0];
        String trackName = params[1];

        trackService.removeTrackFromPlaylist(playlistName, trackName);
    }

    private void createPlaylist(String args)
    {
        String[] params = args.split(" ", 1);

        if (params.length < 1) {
            System.out.println(" Используйте: /createplaylist [плейлист]");
            System.out.println("   Пример: /createplaylist Рок-хиты");
            return;
        }

        String playlistName = params[0];

        playlistService.createPlaylist(playlistName, "111");
    }

    private void showAllPlaylists()
    {
        playlistService.getPlaylistsByUserID("111");
    }

    private void removePlaylist(String args)
    {
        String[] params = args.split(" ", 1);

        if (params.length < 1) {
            System.out.println(" Используйте: /removeplaylist [плейлист]");
            System.out.println("   Пример: /removeplaylist Рок-хиты");
            return;
        }

        String playlistName = params[0];

        playlistService.removePlaylist(playlistName, "111");
    }

    private void showHelp() {
        System.out.println(" Помощь по командам:");
        System.out.println("/start - начать работу");
        System.out.println("/tracks - показать все треки из файла");
        System.out.println("/addtrack [плейлист] [трек] - добавить трек в плейлист");
        System.out.println("   Пример: /addtrack 'Рок-хиты' 'Bohemian Rhapsody'");
        System.out.println("/removetrack [плейлист] [трек] - удалить трек из плейлиста");
        System.out.println("/createplaylist [плейлист] - создать новый плейлист");
        System.out.println("/playlist [плейлист] - показать все плейлисты");
        System.out.println("/removeplaylist [плейлист] - удалить плейлист");
        System.out.println("/search [текст] - поиск треков");
        System.out.println("/help - эта справка");
        System.out.println("/exit - выход");
    }


    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}