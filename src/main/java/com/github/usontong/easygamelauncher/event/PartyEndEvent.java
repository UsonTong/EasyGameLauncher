package com.github.usontong.easygamelauncher.event;

import com.github.usontong.easygamelauncher.entity.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Party party;

    public Party getParty() {
        return party;
    }

    public PartyEndEvent(Party party) {
        this.party = party;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
