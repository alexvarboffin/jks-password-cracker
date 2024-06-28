package org.walhalla;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.groupadministration.PromoteChatMember;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Telega {

    public static String stickerslib_bot = "6022505368:AAHM9rl2O503lPD-yaq-LvxKixRxtxucC-A";//@Stickerslib_bot
    //String botToken="220535441:AAGSE2J0uJp0X87cxyup4kL9ytybvb78AGk";

    public static void main(String[] args) {


        // Создание объекта бота
        TelegramBotsApi botsApi = null;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                // Регистрация бота
                MyBot bot = new MyBot(stickerslib_bot);
                botsApi.registerBot(bot);

                // Вызов метода для добавления администратора
                promoteToAdmin(bot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    private static void promoteToAdmin(MyBot bot) {
        // Замените 'CHANNEL_ID' на ID вашего канала
        String chatId = "-1001326923928";

        // Замените 'USER_ID' на ID пользователя, которого хотите сделать администратором
        long userId = 5462837472L;//

        // Создание объекта с разрешениями администратора
        ChatPermissions permissions = new ChatPermissions();
        permissions.setCanChangeInfo(true);
        permissions.setCanInviteUsers(true);
        //permissions.setCanRestrictMembers(true);
        permissions.setCanPinMessages(true);
        //permissions.setCanPromoteMembers(false);

        // Создание объекта запроса на добавление администратора
        PromoteChatMember promoteChatMember = new PromoteChatMember();

        promoteChatMember.setChatId(chatId);
        promoteChatMember.setCanInviteUsers(true);
        promoteChatMember.setUserId(userId);
        promoteChatMember.setCanDeleteMessages(true);
        permissions.setCanInviteUsers(true);
        //promoteChatMember.setPermissions(permissions); нет такого метода


        //USER_PRIVACY_RESTRICTED
        //USER_NOT_MUTUAL_CONTACT

        try {
            // Выполнение запроса на добавление администратора
            bot.execute(promoteChatMember);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
