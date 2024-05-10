package com.github.usontong.easygamelauncher;

import com.github.usontong.easygamelauncher.command.Command;
import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.entity.PartyConfig;
import com.github.usontong.easygamelauncher.listener.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

public class EasyGameLauncher extends JavaPlugin {
    public static Plugin instance;
    public static Configuration config;


    public static HashMap<String, PartyConfig> partyConfigs = new HashMap<>();//存放所有party配置
    public static HashMap<String, Party> partyMap = new HashMap<>();//当前正在运行的所有队列

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        instance = this;
        saveDefaultConfig();
        config = getConfig();
        readPartyConfig();

        Bukkit.getPluginCommand("easygamelauncher").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
    }

    //读取所有派对配置
    public static void readPartyConfig() {
        ConfigurationSection party = config.getConfigurationSection("party");
        Set<String> keys = party.getKeys(false);

        keys.forEach(party_type -> {
            //设置party配置
            int startAmount = party.getInt(party_type + ".StartAmount");
            int startSecond = party.getInt(party_type + ".StartSecond");
            int accelerateAmount = party.getInt(party_type + ".AccelerateAmount");
            int accelerateSecond = party.getInt(party_type + ".AccelerateSecond");
            PartyConfig partyConfig = new PartyConfig(party_type, startAmount, startSecond, accelerateAmount, accelerateSecond);

            //添加到列表
            partyConfigs.put(party_type, partyConfig);
        });
    }
}
