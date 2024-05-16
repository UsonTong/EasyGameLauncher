package com.github.usontong.easygamelauncher.listener;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.api.MythicMobsAPI;
import com.github.usontong.easygamelauncher.entity.*;
import com.github.usontong.easygamelauncher.event.party.PartyCreateEvent;
import com.github.usontong.easygamelauncher.event.party.PartyEndEvent;
import com.github.usontong.easygamelauncher.event.party.PartyStartEvent;
import com.github.usontong.easygamelauncher.event.player.PlayerJoinPartyEvent;
import com.github.usontong.easygamelauncher.event.player.PlayerLeavePartyEvent;
import com.github.usontong.easygamelauncher.event.player.PlayerOutEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListener implements Listener {

    @EventHandler
    public void onPartyCreate(PartyCreateEvent event) {
        event.getParty().setLifecycle_(Lifecycle.CREATE);//进入到下一个生命周期

        MythicMobsAPI.partySkill(event);
    }

    @EventHandler
    public void onPartyEnd(PartyEndEvent event) {
        event.getParty().setLifecycle_(Lifecycle.END);

        event.getParty().end();

        MythicMobsAPI.partySkill(event);
    }

    @EventHandler
    public void onPlayerOut(PlayerOutEvent event) {
        Party party = event.getParty();
        if (party.getPresentMembers().size() <= party.getConfig().getLeaveAmount()) {
            Bukkit.getPluginManager().callEvent(new PartyEndEvent(party));
            return;
        }

        MythicMobsAPI.playerSkill(event);
    }

    @EventHandler
    public void onPartyStart(PartyStartEvent event) {
        event.getParty().setLifecycle_(Lifecycle.START);//进入到下一个生命周期

        Party party = event.getParty();
        //做一次验证
        if (party.getAmount() < party.getConfig().getStartAmount()) {
            party.stop();
            return;
        }

        //初始化场上所有人
        party.getPresentMembers().addAll(party.getAllMembers());
        event.getParty().setLifecycle_(Lifecycle.OUT);//进入到下一个生命周期

        MythicMobsAPI.partySkill(event);
    }


    /**
     * 当人数大于等于 START_AMOUNT 时开始 START_SECOND 秒读秒
     * 当人数小于 START_AMOUNT 时关闭读秒
     * 当人数等于 ACCELERATE_AMOUNT 时触发加速
     * 加速到 ACCELERATE_SECOND
     */
    @EventHandler
    public void onPlayerJoinParty(PlayerJoinPartyEvent event) {
        event.getParty().setLifecycle_(Lifecycle.JOIN);//进入到下一个生命周期

        Party party = event.getParty();
        if (party.getAmount() >= party.getConfig().getStartAmount()) {
            party.start();
        }
        if (party.getAmount() >= party.getConfig().getAccelerateAmount()) {
            party.accelerate();
        }

        MythicMobsAPI.playerSkill(event);
    }

    @EventHandler
    public void onPlayerLeaveParty(PlayerLeavePartyEvent event) {
        Party party = event.getParty();
        EasyGameLauncher.playerInParty.remove(event.getPlayer());
        StartTimer startTimer = party.getTimer();
        MessageSender.sendAll(party.getConfig().getStartAmount() +"<" + startTimer.getSecond());
        if (party.getAmount() < party.getConfig().getStartAmount()) {
            party.stop();
        }

        MythicMobsAPI.playerSkill(event);
    }
}
