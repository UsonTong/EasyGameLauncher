package com.github.usontong.easygamelauncher;

import com.github.usontong.easygamelauncher.command.Command;
import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.listener.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyGameLauncher extends JavaPlugin {
    public static Party PARTY;

    public static int START_AMOUNT;//开始游戏的人数
    public static int START_SECOND;//倒计时的时间
    public static int ACCELERATE_AMOUNT;//加速倒计时的人数
    public static int ACCELERATE_SECOND;//加速到几秒

    public static Plugin instance;

    @Override
    public void onEnable() {
        init();
    }

    private void init() {
        instance = this;

        Bukkit.getPluginCommand("easygamelauncher").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);


        //应当改成配置文件读取
        START_AMOUNT = 2;
        ACCELERATE_AMOUNT = 3;
        START_SECOND = 60;
        ACCELERATE_SECOND = 10;

        PARTY = new Party();
    }
}
