package btw.community.arminias.tweaks;

import btw.AddonHandler;
import btw.BTWAddon;

import java.util.Map;

public class TweaksAddon extends BTWAddon {
    private static TweaksAddon instance;
    private Map<String, String> config = null;
    public static boolean JOIN_INVULNERABILITY_OFF = false;
    public static boolean WITHER_SPAWNABLE_IN_END = false;
    public static boolean TWO_DRAGONS_IN_END = false;
    public static boolean DO_LOGGING_OF_ACTIONS = false;
    public static String LOGGING_PATH_PREFIX = ".";
    public static final String ADDON_CHECK_MESSAGE = "Install the Transient modpack to join!";

    public TweaksAddon() {
        super();
    }

    @Override
    public void preInitialize() {
        this.registerProperty("JOIN_INVULNERABILITY_OFF", "false", "Transient Server Settings");
        this.registerProperty("WITHER_SPAWNABLE_IN_END", "false" );
        this.registerProperty("TWO_DRAGONS_IN_END", "false");
        this.registerProperty("DO_LOGGING_OF_ACTIONS", "false");
        this.registerProperty("LOGGING_PATH_PREFIX", ".");
    }

    @Override
    public void handleConfigProperties(Map<String, String> propertyValues) {
        this.config = propertyValues;
        if (this.config != null) {
            String tmp;
            if ((tmp = this.config.get("JOIN_INVULNERABILITY_OFF")) != null) JOIN_INVULNERABILITY_OFF = Boolean.parseBoolean(tmp);
            if ((tmp = this.config.get("WITHER_SPAWNABLE_IN_END")) != null) WITHER_SPAWNABLE_IN_END = Boolean.parseBoolean(tmp);
            if ((tmp = this.config.get("TWO_DRAGONS_IN_END")) != null) TWO_DRAGONS_IN_END = Boolean.parseBoolean(tmp);
            if ((tmp = this.config.get("DO_LOGGING_OF_ACTIONS")) != null) DO_LOGGING_OF_ACTIONS = Boolean.parseBoolean(tmp);
            if ((tmp = this.config.get("LOGGING_PATH_PREFIX")) != null) LOGGING_PATH_PREFIX = tmp;
        }
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}