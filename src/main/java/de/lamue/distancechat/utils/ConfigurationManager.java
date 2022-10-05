package de.lamue.distancechat.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager {

    public static File DIR = new File("plugins//DistanceChat");
    public static File FILE = new File("plugins//DistanceChat//config.yml");
    public static FileConfiguration CFG = YamlConfiguration.loadConfiguration(FILE);

    public static ChatMode STANDARDCHATMODE;
    public static String KEYWORDGLOBAL;
    public static Integer DISTANCE;
    public static String KEYWORDSHOUT;
    public static Integer SHOUTDISTANCE;
    public static List<String> WORLDS;
    public static String SYNTAXDISTANCE;
    public static String SYNTAXSHOUT;
    public static String SYNTAXGLOBAL;
    public static void loadConfiguration(){
        if (!DIR.exists()) {
            DIR.mkdirs();
        }
        if (!FILE.exists()) {
            try {
                List<String> worlds = new ArrayList<>();
                worlds.add("world");
                FILE.createNewFile();
                CFG.set("Settings.Mode", "distance");
                CFG.set("Settings.Mode.Explanation", "distance, global");
                CFG.set("Global.Keyword", ">");
                CFG.set("Global.Keyword.Explanation", "Set the keyword for global messages. If Global is standard; this keyword will be used for distance-based chat.");
                CFG.set("Distance.Standard", Integer.valueOf(10));
                CFG.set("Distance.Shout", Integer.valueOf(30));
                CFG.set("Shout.Keyword", "!");
                CFG.set("Syntax.Global", "§8[GLOBAL] %name% > §7%message%");
                CFG.set("Syntax.Distance", "§8[LOCAL] %name% > §7%message%");
                CFG.set("Syntax.Shout", "§8[§cSHOUT!§8] %name% > §7%message%");
                CFG.set("Worlds", worlds);
                CFG.save(FILE);
            } catch (IOException e) {
                loadConfiguration();
            }
        }
        if(CFG.getString("Settings.Mode").equals("distance")){
            STANDARDCHATMODE = ChatMode.DISTANCE;
        }
        if(CFG.getString("Settings.Mode").equals("global")){
            STANDARDCHATMODE = ChatMode.GLOBAL;
        }
        SYNTAXDISTANCE = CFG.getString("Syntax.Distance");
        SYNTAXGLOBAL = CFG.getString("Syntax.Global");
        SYNTAXSHOUT = CFG.getString("Syntax.Shout");
        KEYWORDGLOBAL = CFG.getString("Global.Keyword");
        DISTANCE = CFG.getInt("Distance.Standard");
        KEYWORDSHOUT = CFG.getString("Shout.Keyword");
        SHOUTDISTANCE = CFG.getInt("Distance.Shout");
        WORLDS = (List<String>) CFG.getList("Worlds");
    }
}
