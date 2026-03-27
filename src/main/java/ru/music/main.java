package ru.music;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.music.app.App;

public class main {

    private static final String BOT_TOKEN = "8782263789:AAGenZMy6GbEIx_pljvhiSOkXo7U8cM4ew8";
    private static final String BOT_USERNAME = "tdjgf_bot";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            App bot = new App(BOT_TOKEN, BOT_USERNAME);
            botsApi.registerBot(bot);

            System.out.println("✅ Бот запущен");
            Thread.currentThread().join();

        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("⏹️ Бот остановлен");
        }
    }
}