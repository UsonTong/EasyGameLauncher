package com.github.usontong.easygamelauncher.entity;


import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Party {
    private final ArrayList<Player> members = new ArrayList<>();

    private final String name;//派对名字
    private final PartyConfig config;//配置文件
    private final StartTimer startTimer;

    public PartyConfig getConfig() {
        return config;
    }

    public StartTimer getTimer() {
        return startTimer;
    }

    public Party(String name, PartyConfig config) {
        this.name = name;
        this.config = config;
        startTimer = new StartTimer(config, name);
    }

    public int getAmount() {
        return members.size();
    }

        public boolean addMember(Player p) {
        if (!members.contains(p)) {
            members.add(p);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeMember(Player p) {
        if (members.contains(p)) {
            members.remove(p);
            return true;
        } else {
            return false;
        }
    }

    public void start() {
        startTimer.start();
    }

    public void stop() {
        startTimer.stop();
    }

    public void accelerate() {
        startTimer.accelerate();
    }


}
