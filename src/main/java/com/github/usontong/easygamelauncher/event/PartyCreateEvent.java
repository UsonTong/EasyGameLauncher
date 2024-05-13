package com.github.usontong.easygamelauncher.event;

import com.github.usontong.easygamelauncher.entity.Party;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PartyCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Party party;

    public Party getParty() {
        return party;
    }

    public PartyCreateEvent(Party party) {
        this.party = party;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
