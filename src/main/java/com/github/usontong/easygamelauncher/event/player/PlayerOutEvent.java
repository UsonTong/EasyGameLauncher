package com.github.usontong.easygamelauncher.event.player;

import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.event.superclass.PlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerOutEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Party party;
    private final Player player;

    public PlayerOutEvent(Party party, Player player) {
        this.party = party;
        this.player = player;
    }

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
