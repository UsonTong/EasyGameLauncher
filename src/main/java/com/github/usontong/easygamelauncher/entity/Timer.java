package com.github.usontong.easygamelauncher.entity;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.event.GameStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private int second = EasyGameLauncher.START_SECOND;
    private boolean status = false;//true start  false stop

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void reduce() {
        this.second--;
    }

    public void start() {
        status = true;
    }

    public void stop() {
        status = false;
    }

    public void accelerate() {
        second = EasyGameLauncher.ACCELERATE_SECOND;
    }


    public Timer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (status) {
                    reduce();
                } else {
                    second = EasyGameLauncher.START_SECOND;
                }

                if (second <= 0) {
                    status = false;
                    Bukkit.getPluginManager().callEvent(new GameStartEvent());
                    EasyGameLauncher.PARTY = null;//游戏开始后 派对解散
                }

                MessageSender.sendAll(second + "");
            }
        }.runTaskTimer(EasyGameLauncher.instance, 20, 20);
    }
}
