package org.walhalla;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {

    private final String token;

    public MyBot(String botToken) {
        this.token = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
    }

    @Override
    public String getBotUsername() {
        // Возвращает имя вашего бота, которое было указано при регистрации
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}