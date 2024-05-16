package com.github.usontong.easygamelauncher.entity;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.event.party.PartyStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class StartTimer {
    private final PartyConfig partyConfig;

    private int second;
    private boolean start = false;//表示时间是否流动 true start  false stop
    private boolean stop = false;

    public int getSecond() {
        return second;
    }

    public void reduce() {
        this.second--;
    }

    public void start() {
        if (!start) {
            start = true;
        }
    }

    public void stop() {
        if (start) {
            start = false;
        }
    }

    public void accelerate() {
        if (second > partyConfig.getAccelerateSecond()) {
            second = partyConfig.getAccelerateSecond();
        }
    }


    public StartTimer(PartyConfig partyConfig, String party_name) {
        //init
        this.partyConfig = partyConfig;
        this.second = partyConfig.getStartSecond();


        new BukkitRunnable() {
            @Override
            public void run() {
                if (stop) {
                    return;
                }

                if (start) {
                    reduce();
                } else {
                    second = partyConfig.getStartSecond();
                }

                if (second <= 0) {
                    start = false;
                    Bukkit.getPluginManager().callEvent(new PartyStartEvent(Party.getPartyByPartyName(party_name)));

                    //倒计时结束
                    stop = true;
                }
            }
        }.runTaskTimer(EasyGameLauncher.instance, 20, 20);
    }
}
