package com.github.usontong.easygamelauncher.event.superclass;

import com.github.usontong.easygamelauncher.entity.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class PartyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Party party;

    public Party getParty() {
        return party;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
