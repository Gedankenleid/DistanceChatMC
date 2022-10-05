package de.lamue.distancechat.listeners;

import de.lamue.distancechat.utils.ChatMode;
import de.lamue.distancechat.utils.ConfigurationManager;
import de.lamue.distancechat.utils.StringContent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class AsyncPlayerChat implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent asyncPlayerChatEvent){
        Player player = asyncPlayerChatEvent.getPlayer();
        String world = player.getWorld().toString();
        if(ConfigurationManager.WORLDS.contains(world)){
            String message = asyncPlayerChatEvent.getMessage();
            ChatMode messageType = ConfigurationManager.STANDARDCHATMODE;
            if(message.startsWith(ConfigurationManager.KEYWORDGLOBAL)){
                message.substring(1);
                if(messageType.equals(ChatMode.DISTANCE)){
                    messageType = ChatMode.GLOBAL;
                }else{
                    messageType = ChatMode.DISTANCE;
                }
            }else if(message.startsWith(ConfigurationManager.KEYWORDSHOUT)){
                message.substring(1);
                messageType = ChatMode.SHOUT;
            }
            if(messageType.equals(ChatMode.GLOBAL)){
                if(player.hasPermission("DistanceChat.global")){
                    String newMessage = ConfigurationManager.SYNTAXGLOBAL.replaceAll("%name%", player.getCustomName()).replaceAll("%message%", message);
                    Bukkit.getConsoleSender().sendMessage(newMessage);
                    for(Player allPlayers : Bukkit.getOnlinePlayers()) {
                        allPlayers.sendMessage(newMessage);
                    }
                }else{
                    player.sendMessage(StringContent.NOPERM);
                }
            }else if(messageType.equals(ChatMode.SHOUT)){
                if(player.hasPermission("DistanceChat.shout")){
                    String newMessage = ConfigurationManager.SYNTAXSHOUT.replaceAll("%name%", player.getCustomName()).replaceAll("%message%", message);
                    Bukkit.getConsoleSender().sendMessage(newMessage);
                    for(Player allPlayers : GETPLAYERSAROUND(player.getLocation(), ConfigurationManager.SHOUTDISTANCE)) {
                        allPlayers.sendMessage(newMessage);
                    }
                }else{
                    player.sendMessage(StringContent.NOPERM);
                }
            }else if(messageType.equals(ChatMode.DISTANCE)){
                String newMessage = ConfigurationManager.SYNTAXDISTANCE.replaceAll("%name%", player.getCustomName()).replaceAll("%message%", message);
                Bukkit.getConsoleSender().sendMessage(newMessage);
                for(Player allPlayers : GETPLAYERSAROUND(player.getLocation(), ConfigurationManager.DISTANCE)) {
                    allPlayers.sendMessage(newMessage);
                }
            }
        }
    }

    public static List<Player> GETPLAYERSAROUND(Location location, Integer distance){
        List check = new ArrayList();
        for (Player around : Bukkit.getOnlinePlayers()) {
            if (around.getWorld().equals(location.getWorld()) && location.distance(location) <= distance) {
                check.add(around);
            }
        }
        return check;
    }
}
