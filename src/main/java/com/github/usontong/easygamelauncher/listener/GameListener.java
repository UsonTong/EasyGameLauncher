package com.github.usontong.easygamelauncher.listener;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.event.GameStartEvent;
import com.github.usontong.easygamelauncher.event.JoinPartyEvent;
import com.github.usontong.easygamelauncher.event.LeavePartyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListener implements Listener {
    @EventHandler
    public void onGameStart(GameStartEvent event) {
        if (EasyGameLauncher.PARTY.getAmount() < EasyGameLauncher.START_SECOND) {
            EasyGameLauncher.PARTY.stop();
        }

        MessageSender.sendAll("游戏开始！");
    }


    /**
     * 当人数大于等于 START_AMOUNT 时开始 START_SECOND 秒读秒
     * 当人数小于 START_AMOUNT 时关闭读秒
     * 当人数等于 ACCELERATE_AMOUNT 时触发加速
     * 加速到 ACCELERATE_SECOND
     */
    @EventHandler
    public void onJoinParty(JoinPartyEvent event) {
        if (EasyGameLauncher.PARTY.getAmount() >= EasyGameLauncher.START_AMOUNT) {
            EasyGameLauncher.PARTY.start();
        }
        if (EasyGameLauncher.PARTY.getAmount() >= EasyGameLauncher.ACCELERATE_AMOUNT) {
            EasyGameLauncher.PARTY.accelerate();
        }

        MessageSender.sendAll(event.getPlayer().getName() + " 加入游戏！");
    }

    @EventHandler
    public void onLeaveParty(LeavePartyEvent event) {
        if (EasyGameLauncher.PARTY.getAmount() < EasyGameLauncher.START_SECOND) {
            EasyGameLauncher.PARTY.stop();
        }

        MessageSender.sendAll(event.getPlayer().getName() + " 退出游戏！");
    }
}
