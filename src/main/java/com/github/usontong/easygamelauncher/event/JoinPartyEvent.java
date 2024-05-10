package com.github.usontong.easygamelauncher.event;

import com.github.usontong.easygamelauncher.entity.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JoinPartyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Party party;

    public Party getParty() {
        return party;
    }

    public JoinPartyEvent(Player player, Party party) {
        this.player = player;
        this.party = party;
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
