package com.github.usontong.easygamelauncher.entity;


import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Party {
    private final ArrayList<Player> members = new ArrayList<>();
    private final Timer timer = new Timer();

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
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void accelerate() {
        timer.accelerate();
    }


}
