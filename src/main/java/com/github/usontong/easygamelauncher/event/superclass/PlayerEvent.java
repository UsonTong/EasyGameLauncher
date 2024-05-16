package com.github.usontong.easygamelauncher.event.superclass;

import com.github.usontong.easygamelauncher.entity.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Party party;

    public Party getParty() {
        return party;
    }

    public Player getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
