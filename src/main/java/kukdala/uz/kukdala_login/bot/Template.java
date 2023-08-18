package kukdala.uz.kukdala_login.bot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Template {
    String ADMIN_ID = "1085241246";
    String BOT_TOKEN = "5169354010:AAFlFQCD4lk29l9FXfKYb7nTzZnbsfOohy0";
    String BOT_USERNAME = "@Idea_dan_bot";
    List<String> CLEAR = Arrays.asList("Ha", "🔙Back");
    List<String> DISTRICT = List.of("Ko'kdala");
    List<String> BACK_AND_MENU = Arrays.asList("🏠Menu", "Tugatish", "🔙Back");
    List<String> IS_SEND = Arrays.asList("🏠Menu", "Tastiqlash", "Tozalash", "🔙Back");
    List<String> USER_BUTTON = Arrays.asList("🏠Menu", "Tugatish");
    List<String> SEND_ADMIN = Collections.singletonList("👍 🎁");
}
