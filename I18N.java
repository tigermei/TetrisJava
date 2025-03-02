import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18N {
    private static final ResourceBundle messages = ResourceBundle.getBundle("resources.messages");
    
    static {
        // 根据系统默认语言加载资源包
        // bundle = ResourceBundle.getBundle("messages", Locale.getDefault());
    }
    
    public static String getString(String key) {
        return messages.getString(key);
    }
    
    public static String getString(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }
    
    public static void setLocale(Locale locale) {
        // bundle = ResourceBundle.getBundle("messages", locale);
    }
} 