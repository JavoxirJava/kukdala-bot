package kukdala.uz.kukdala_login.bot;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ButtonSettings {

    final BotSettings botSettings;
    final BotMethods botMethods;

    public ButtonSettings(@Lazy BotSettings botSettings, @Lazy BotMethods botMethods) {
        this.botSettings = botSettings;
        this.botMethods = botMethods;
    }

    //inlineButton
    public List<List<InlineKeyboardButton>> getInlineButtonRows(List<String> data) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int length = data.size() % 2 != 0 ? data.size() - 1 : data.size();
        for (int i = 0; i < length; i += 2) {
            List<InlineKeyboardButton> inlineButton = new ArrayList<>();
            inlineButton.add(getInlineButton(data.get(i), data.get(i)));
            inlineButton.add(getInlineButton(data.get(i + 1), data.get(i + 1)));
            rows.add(inlineButton);
        }
        if (data.size() % 2 != 0) {
            String text = data.get(data.size() - 1);
            rows.add(Collections.singletonList(getInlineButton(text, text)));
        }
        return rows;
    }

    public InlineKeyboardMarkup getInlineMarkup(List<String> list) {
        return new InlineKeyboardMarkup(getInlineButtonRows(list));
    }

    public InlineKeyboardButton getInlineButton(String text, String callback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setCallbackData(callback);
        inlineKeyboardButton.setText(text);
        return inlineKeyboardButton;
    }


    //keyboardButton
    public void keyboardButton(Message message, SendMessage sendMessage, String text, List<String> data) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        int length = data.size() % 2 != 0 ? data.size() - 1 : data.size();
        for (int i = 0; i < length; i += 2) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(getKeyboardButton(data.get(i)));
            keyboardRow.add(getKeyboardButton(data.get(i + 1)));
            keyboardRows.add(keyboardRow);
        }
        KeyboardRow keyboardRow2 = new KeyboardRow();
        if (data.size() % 2 != 0) {
            keyboardRow2.add(getKeyboardButton(data.get(data.size() - 1)));
        }
        keyboardRows.add(keyboardRow2);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(markup);
        botMethods.sendMSG(sendMessage, text);
    }

    public void button() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton("btn 1.1");
        keyboardRow.add(keyboardButton);
        keyboardButton = new KeyboardButton("btn 1.2");
        keyboardRow.add(keyboardButton);
        keyboardRows.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardButton = new KeyboardButton("btn 2.1");
        keyboardRow.add(keyboardButton);
        keyboardButton = new KeyboardButton("btn 2.2");
        keyboardRow.add(keyboardButton);
        keyboardRows.add(keyboardRow);
        markup.setKeyboard(keyboardRows);
//        sendMessage
    }


    public void inlineButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> inlineButton = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("1.1");
        button.setCallbackData("1.1");
        inlineButton.add(button);
        button = new InlineKeyboardButton();
        button.setText("1.2");
        button.setCallbackData("1.2");
        inlineButton.add(button);
        rows.add(inlineButton);
        markup.setKeyboard(rows);
//        sendMessage
    }


    public KeyboardButton getKeyboardButton(String text) {
        return new KeyboardButton(text);
    }
}
