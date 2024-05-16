package com.github.usontong.easygamelauncher;

import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.api.PAPI;
import com.github.usontong.easygamelauncher.command.Command;
import com.github.usontong.easygamelauncher.entity.*;
import com.github.usontong.easygamelauncher.listener.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

public class EasyGameLauncher extends JavaPlugin {
    public static Plugin instance;
    public static Configuration config;


    public static HashMap<String, PartyConfig> partyConfigs = new HashMap<>();//派对类型 派对对象party配置
    public static HashMap<String, Party> partyMap = new HashMap<>();//派对名字 派对对象 表示创建的派对名和 派对之间的关系
    public static HashMap<Player, Party> playerInParty = new HashMap<>();//玩家名字 派对对象 表示玩家正在派对内

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
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI(this).register();
        } else {
            MessageSender.sendMessage(Bukkit.getConsoleSender(), "请安装 PlaceholderAPI!");
        }
    }

    //读取所有派对配置
    public static void readPartyConfig() {
        ConfigurationSection party = config.getConfigurationSection("Party");
        Set<String> keys = party.getKeys(false);

        keys.forEach(party_type -> {
            //设置party配置
            int startAmount = party.getInt(party_type + ".StartAmount");
            int startSecond = party.getInt(party_type + ".StartSecond");
            int accelerateAmount = party.getInt(party_type + ".AccelerateAmount");
            int accelerateSecond = party.getInt(party_type + ".AccelerateSecond");
            int leaveAmount = party.getInt(party_type + ".LeaveAmount");
            PartyConfig partyConfig = new PartyConfig(party_type,
                    startAmount,
                    startSecond,
                    accelerateAmount,
                    accelerateSecond,
                    leaveAmount);

            //添加到列表
            partyConfigs.put(party_type, partyConfig);
            try {
                readTrigger(partyConfig, party, party_type);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EasyGameLauncher.partyConfigs.forEach((party_type1, party1)
                    -> MessageSender.sendAll(party_type1 + "," + party1));
        });
    }

    public static void readTrigger(PartyConfig partyConfig,
                                   ConfigurationSection configuration,
                                   String party_type) throws IOException {
        ConfigurationSection configurationSection = configuration.getConfigurationSection(party_type + ".Trigger");
        configurationSection.getKeys(false).forEach(trigger_type -> {
            String self = configurationSection.getString(trigger_type + ".SELF");
            String nonself = configurationSection.getString(trigger_type + ".NONSELF");
            String all = configurationSection.getString(trigger_type + ".ALL");


            String playerPath = "com.github.usontong.easygamelauncher.event.player.";
            String partyPath = "com.github.usontong.easygamelauncher.event.party.";
            try {
                Class<?> playerClazz = Class.forName(playerPath + trigger_type);
                PlayerTrigger playerTrigger = new PlayerTrigger(playerClazz);
                playerTrigger.getTargetSkill().put(PlayerTarget.ALL, all);
                playerTrigger.getTargetSkill().put(PlayerTarget.SELF, self);
                playerTrigger.getTargetSkill().put(PlayerTarget.NONSELF, nonself);

                partyConfig.getPlayerTriggerHashMap().put(trigger_type, playerTrigger);
            } catch (ClassNotFoundException ignored) {}
            try {
                Class<?> partyClazz = Class.forName(partyPath + trigger_type);
                PartyTrigger partyTrigger = new PartyTrigger(partyClazz);
                partyTrigger.getTargetSkill().put(PlayerTarget.ALL, all);
                partyTrigger.getTargetSkill().put(PlayerTarget.SELF, self);
                partyTrigger.getTargetSkill().put(PlayerTarget.NONSELF, nonself);

                partyConfig.getPartyTriggerHashMap().put(trigger_type, partyTrigger);
            } catch (ClassNotFoundException ignored) {}
        });
    }
}
