package com.github.usontong.easygamelauncher.event.party;

import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.event.superclass.PartyEvent;
import org.bukkit.event.HandlerList;

public class PartyEndEvent extends PartyEvent {
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
