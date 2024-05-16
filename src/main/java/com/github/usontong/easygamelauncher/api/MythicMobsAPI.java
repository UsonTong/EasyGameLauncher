package com.github.usontong.easygamelauncher.api;

import com.github.usontong.easygamelauncher.entity.PartyTrigger;
import com.github.usontong.easygamelauncher.entity.PlayerTarget;
import com.github.usontong.easygamelauncher.entity.PlayerTrigger;
import com.github.usontong.easygamelauncher.event.superclass.PartyEvent;
import com.github.usontong.easygamelauncher.event.superclass.PlayerEvent;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.util.MythicUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MythicMobsAPI {
    public static void test(Player player, String skillName) {
        LivingEntity target = MythicUtil.getTargetedEntity(player);
        List<Entity> targets = new ArrayList<>();
        targets.add(target);
        MythicMobs.inst().getAPIHelper().castSkill(player, skillName, player, player.getLocation(), targets, null, 1.0F);
    }

    public static void playerSkill(PlayerEvent event) {
        HashMap<String, PlayerTrigger> playerTriggerHashMap = event.getParty().getConfig().getPlayerTriggerHashMap();
        String simpleName = event.getClass().getSimpleName();
        if (playerTriggerHashMap.containsKey(simpleName)) {
            PlayerTrigger playerTrigger = playerTriggerHashMap.get(simpleName);
            HashMap<String, String> targetSkill = playerTrigger.getTargetSkill();
            String self = targetSkill.get(PlayerTarget.SELF);
            String nonself = targetSkill.get(PlayerTarget.NONSELF);
            String all = targetSkill.get(PlayerTarget.ALL);

            if (self != null) {
                Player player = event.getPlayer();
                MythicMobsAPI.test(player, self);
            }
            if (nonself != null) {
                ArrayList<Player> allMembers = event.getParty().getAllMembers();
                allMembers.forEach(player -> {
                    if (event.getPlayer() != player) {
                        MythicMobsAPI.test(player, nonself);
                    }
                });
            }
            if (all != null) {
                ArrayList<Player> allMembers = event.getParty().getAllMembers();
                allMembers.forEach(player -> MythicMobsAPI.test(player, all));
            }
        }
    }

    public static void partySkill(PartyEvent event) {
        HashMap<String, PartyTrigger> partyTriggerHashMap = event.getParty().getConfig().getPartyTriggerHashMap();
        String simpleName = event.getClass().getSimpleName();
        if (partyTriggerHashMap.containsKey(simpleName)) {
            PartyTrigger partyTrigger = partyTriggerHashMap.get(simpleName);
            HashMap<String, String> targetSkill = partyTrigger.getTargetSkill();
            String all = targetSkill.get(PlayerTarget.ALL);
            if (all != null) {
                ArrayList<Player> allMembers = event.getParty().getAllMembers();
                allMembers.forEach(player -> MythicMobsAPI.test(player, all));
            }
        }
    }
}
