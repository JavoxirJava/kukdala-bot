package kukdala.uz.kukdala_login.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotMethods {

    final BotSettings botSettings;
    final ButtonSettings buttonSettings;

    public BotMethods(@Lazy BotSettings botSettings, @Lazy ButtonSettings buttonSettings) {
        this.botSettings = botSettings;
        this.buttonSettings = buttonSettings;
    }

    //  +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=  Messages  +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=
    public void sendMSG(SendMessage sendMessage, String text) {
        try {
            sendMessage.setText(text);
            botSettings.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("not execute");
        }
    }

    public void sendLOC(SendLocation sendLocation, SendMessage sendMessage) {
        try {
            botSettings.execute(sendLocation);
        } catch (TelegramApiException e) {
            sendMSG(sendMessage, "location junatishda muammo yuzaga keldi!");
        }
    }

    //  +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=  Buttons  +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
    public ReplyKeyboardMarkup phoneNumberOrLocation(boolean isPhone) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        if (isPhone) {
            keyboardButton.setText("Telefon raqamni yuborish");
            keyboardButton.setRequestContact(true);
        } else {
            keyboardButton.setText("hozirgi joylashuvni yuborish");
            keyboardButton.setRequestLocation(true);
        }
        keyboardFirstRow.add(keyboardButton);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
