package kukdala.uz.kukdala_login;

import kukdala.uz.kukdala_login.bot.BotMethods;
import kukdala.uz.kukdala_login.bot.BotSettings;
import kukdala.uz.kukdala_login.bot.ButtonSettings;
import kukdala.uz.kukdala_login.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class KukdalaLoginApplication {

    public static void main(String[] args) throws TelegramApiException {
        ConfigurableApplicationContext run = SpringApplication.run(KukdalaLoginApplication.class, args);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        BotMethods botMethods = run.getBean(BotMethods.class);
        ButtonSettings buttonSettings = run.getBean(ButtonSettings.class);
        UserService userService = run.getBean(UserService.class);
        botsApi.registerBot(new BotSettings(botMethods, buttonSettings, userService));

    }

}
