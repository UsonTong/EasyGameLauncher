package com.github.usontong.easygamelauncher.api;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

public class MessageSender {
    public static String pluginName = "[§5" + EasyGameLauncher.class.getSimpleName() + "§r]";
    public static String sendName = pluginName + " ";

    public static void sendAll(Object message) {
        String messageString = message.toString();
        Bukkit.getLogger().info(messageString);
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            player.sendMessage(sendName + message);
        }
    }

    public static void sendMessage(CommandSender commandSender, Object message) {
        commandSender.sendMessage(sendName + message);
    }

    public static void loggerInfo(Objects message) {
        Bukkit.getLogger().info(sendName + message);
    }

}
