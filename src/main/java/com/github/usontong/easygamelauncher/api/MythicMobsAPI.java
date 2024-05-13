package com.github.usontong.easygamelauncher.api;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.util.MythicUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MythicMobsAPI {
    public void test(Player player, String skillName) {
        LivingEntity target = MythicUtil.getTargetedEntity(player);
        List<Entity> targets = new ArrayList<>();
        targets.add(target);
        MythicMobs.inst().getAPIHelper().castSkill((Entity)player, skillName, (Entity)player, player.getLocation(), targets, null, 1.0F);
    }
}
