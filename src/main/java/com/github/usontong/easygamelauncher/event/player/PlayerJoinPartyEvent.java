package com.github.usontong.easygamelauncher.event.player;

import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.event.superclass.PlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerJoinPartyEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Party party;

    public Party getParty() {
        return party;
    }

    public PlayerJoinPartyEvent(Player player, Party party) {
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
