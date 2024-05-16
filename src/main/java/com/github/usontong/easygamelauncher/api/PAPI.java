package com.github.usontong.easygamelauncher.api;

import com.github.usontong.easygamelauncher.entity.Party;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PAPI extends PlaceholderExpansion {

    private final Plugin plugin;

    public PAPI(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "UsonTong";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "EasyGameLauncher";
    }

    @Override
    @NotNull
    public String getVersion() {
        return Bukkit.getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player,
                            @NotNull String params) {
        if (Party.getPartyByPartyName(params) == null) {
            return "null";
        }
        int second = Party.getPartyByPartyName(params).getTimer().getSecond();
        return second + "";
    }
}
