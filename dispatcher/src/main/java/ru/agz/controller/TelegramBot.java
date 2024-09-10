package ru.agz.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
     @Value("${bot.name}")
     private String botName;
    @Override
    public String getBotUsername() {
        //передаем имя бота
        return botName;
    }
    public TelegramBot(@Value("${bot.token}") String botToken) {
        //передаем токен бота
        super(botToken);
    }
    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();//получаем сообщение пользователя
        log.debug(message.getText());//выводим сообщение в лог с помощью
        //работа с сообщениями
        SendMessage response = new SendMessage();//создаем объект класса "отправка сообщений"
        response.setChatId(message.getChatId().toString());//передаем обязательный ID чата в который будет отправлено сообщение
        response.setText(message.getText());//передаем содержание сообщения (в данном случае копируем сообщение пользователя)
        sendMessage(response);//запускаем метод отправки сообщения

    }



    public void sendMessage(SendMessage message) {
        if(message != null){//если объект сообщения существует то
            try {
                execute(message);//отправляем сообщение
            }catch (TelegramApiException e){//
                log.error(e);//иначе выводим nullPointException
            }
        }

    }
}

