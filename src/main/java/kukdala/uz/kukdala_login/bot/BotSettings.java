package kukdala.uz.kukdala_login.bot;

import kukdala.uz.kukdala_login.entity.User;
import kukdala.uz.kukdala_login.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.ApproveChatJoinRequest;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatJoinRequest;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BotSettings extends TelegramLongPollingBot {

    private final Map<Long, String> choose = new HashMap<>();
    private final Map<Long, String> F_I_SH = new HashMap<>();
    private final Map<Long, String> birthDate = new HashMap<>();

    final BotMethods botMethods;
    final ButtonSettings buttonSettings;
    private final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sm = new SendMessage();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long userId = message.getChatId();
            sm.setChatId(userId);
            if (message.hasText()) {
                String text = message.getText();
                if (!userService.isUserRegister(userId)) {
                    if (text.equals("/start")) {
                        botMethods.sendMSG(sm, "Assalomu alaykum hurmatli foydalanivchi!");
                        sm.setReplyMarkup(buttonSettings.getInlineMarkup(Template.DISTRICT));
                        botMethods.sendMSG(sm, "Tumanni tanlang");
                        choose.put(userId, "district");
                    } else {
                        if (choose.get(userId).equals("F.I.Sh")) {
                            botMethods.sendMSG(sm, "Toʻgʻilgan sanangizni kiriting\n msol (2000-01-30)\n yane (yil, oy, kun)");
                            choose.put(userId, "birthDate");
                            F_I_SH.put(userId, text);
                        } else if (choose.get(userId).equals("birthDate")) {
                            sm.setReplyMarkup(botMethods.phoneNumberOrLocation(true));
                            botMethods.sendMSG(sm, "telefon raqamingizni kiriting msol (+998912345678)");
                            choose.put(userId, "number");
                            birthDate.put(userId, text);
                        } else if (choose.get(userId).equals("number")) {
                            userService.addUser(new User(userId, Template.DISTRICT.get(0), F_I_SH.get(userId), birthDate.get(userId), text));
                            botMethods.sendMSG(sm, "siz Muaffaqiyatle ruyxatdan utdingiz");
                            botMethods.sendMSG(sm, "https://t.me/+3oUsewqaSI4xNzhi");
                            botMethods.sendMSG(sm, "ushbu guruxga qushilib oling");
                            choose.put(userId, "full");
                        }
                    }
                } else botMethods.sendMSG(sm, "Kechirasiz siz allaqachon ruyxatdan utgansiz!");
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long userId = update.getCallbackQuery().getFrom().getId();
            sm.setChatId(userId);
            if (data.equals(Template.DISTRICT.get(0)) && choose.get(userId).equals("district")) {
                botMethods.sendMSG(sm, "F.I.Sh");
                choose.put(userId, "F.I.Sh");
            }
        } else if (update.hasChatJoinRequest()) {
            ChatJoinRequest chatJoinRequest = update.getChatJoinRequest();
            ApproveChatJoinRequest joinRequest = new ApproveChatJoinRequest(
                    chatJoinRequest.getChat().getId().toString(),
                    chatJoinRequest.getUserChatId()
            );
            try {
                execute(joinRequest);
            } catch (TelegramApiException e) {
                System.out.println("qushilmadi!");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Template.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Template.BOT_TOKEN;
    }
}
