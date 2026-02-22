package ru.music.app;

import ru.music.domain.track.ITrack;
import ru.music.repository.trackrepo.TrackRepo;
import ru.music.service.trackservice.trackservice;

import java.util.Scanner;

public class App {

    private final trackservice TrackService;
    private final Scanner scanner;
    private boolean isRunning;

    public App() {
        TrackRepo trackRepo = new TrackRepo();
        this.TrackService = new trackservice(trackRepo);
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        start();
    }

    public void start() {
        printWelcomeMessage();

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

            case "/add":
                addTrack(args);
                break;

            case "/remove":
                removeTrack(args);
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
        System.out.println(" Команды (как в Telegram боте):");
        System.out.println("   /tracks - показать все треки");
        System.out.println("   /add [плейлист] [трек] - добавить трек");
        System.out.println("   /remove [плейлист] [трек] - удалить трек");
        System.out.println("   /help - помощь");
        System.out.println("   /exit - выход");
    }

    private void showAllTracks() {
        System.out.println(" Загружаю треки из файла...");
        TrackService.showAllTracksFromFile();
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

        TrackService.addTrackToPlaylist(playlistName, trackName);
    }

    private void removeTrack(String args) {
        String[] params = args.split(" ", 2);

        if (params.length < 2) {
            System.out.println(" Используйте: /remove [плейлист] [название трека]");
            System.out.println("   Пример: /remove Рок-хиты Hotel California");
            return;
        }

        String playlistName = params[0];
        String trackName = params[1];

        TrackService.removeTrackFromPlaylist(playlistName, trackName);
    }

    private void showHelp() {
        System.out.println(" Помощь по командам:");
        System.out.println("/start - начать работу");
        System.out.println("/tracks - показать все треки из файла");
        System.out.println("/add [плейлист] [трек] - добавить трек в плейлист");
        System.out.println("   Пример: /add 'Рок-хиты' 'Bohemian Rhapsody'");
        System.out.println("/remove [плейлист] [трек] - удалить трек из плейлиста");
        System.out.println("/help - эта справка");
        System.out.println("/exit - выход");
    }

    private void printWelcomeMessage() {
        System.out.println("Начните с команды /start");
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}