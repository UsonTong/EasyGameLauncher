package com.github.usontong.easygamelauncher.entity;


import com.github.usontong.easygamelauncher.EasyGameLauncher;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Party {
    private int Lifecycle_ = Lifecycle.ERROR;
    private final ArrayList<Player> allMembers = new ArrayList<>();
    private final ArrayList<Player> presentMembers = new ArrayList<>();

    public int getLifecycle_() {
        return Lifecycle_;
    }

    public void setLifecycle_(int lifecycle_) {
        Lifecycle_ = lifecycle_;
    }

    public ArrayList<Player> getPresentMembers() {
        return presentMembers;
    }

    public ArrayList<Player> getAllMembers() {
        return allMembers;
    }

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

    //通过派对名获得派对对象
    public static Party getPartyByPartyName(String party_name) {
        return EasyGameLauncher.partyMap.getOrDefault(party_name, null);
    }


    public int getAmount() {
        return allMembers.size();
    }

    public boolean addMember(Player p) {
        if (!allMembers.contains(p)) {
            allMembers.add(p);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeMember(Player p) {
        if (allMembers.contains(p)) {
            allMembers.remove(p);
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

    //处理派对结束
    public void end() {
        EasyGameLauncher.partyMap.remove(name);
        allMembers.forEach(player -> EasyGameLauncher.playerInParty.remove(player));
    }
}
