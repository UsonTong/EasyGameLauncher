package com.github.usontong.easygamelauncher.command;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.entity.Lifecycle;
import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.event.JoinPartyEvent;
import com.github.usontong.easygamelauncher.event.LeavePartyEvent;
import com.github.usontong.easygamelauncher.event.PartyCreateEvent;
import com.github.usontong.easygamelauncher.event.PlayerOutEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    /**
     *
     * join
     * create party_type party_name
     */
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {//权限验证
            return true;
        }

        String parameter1 = strings[0];

        //玩家加入游戏
        //join party_name
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (parameter1.equalsIgnoreCase("join")) {
                if (strings.length != 2) {
                    MessageSender.sendMessage(commandSender, "/easygamelauncher join <party_name>");
                    return true;
                }

                String party_name = strings[1];


                //验证玩家是否能正确进入派对
                //派对名不存在时
                Party party = Party.getPartyByPartyName(party_name);
                if (party == null) {
                    MessageSender.sendMessage(commandSender, "派对不存在");
                    return true;
                }
                //玩家已经在派对时
                if (EasyGameLauncher.playerInParty.containsKey(player)) {
                    MessageSender.sendMessage(commandSender, "已加入一个派对，无法加入更多派对");
                    return true;
                }

                //将玩家添加到派对
                if (party.addMember(player)) {
                    //呼叫玩家加入派对事件
                    Bukkit.getPluginManager().callEvent(new JoinPartyEvent(player, party));
                    EasyGameLauncher.playerInParty.put(player, party);
                    return true;
                } else  {
                    MessageSender.sendMessage(player, "已加入游戏！");
                }
            } else if (parameter1.equalsIgnoreCase("leave")) {
                if (strings.length != 2) {
                    MessageSender.sendMessage(commandSender, "/easygamelauncher leave <party_name>");
                    return true;
                }

                String party_name = strings[1];
                Party party = Party.getPartyByPartyName(party_name);
                if (party == null) {
                    MessageSender.sendMessage(commandSender, "派对不存在");
                    return true;
                }

                //添加玩家到party
                if (party.removeMember(player)) {
                    //呼叫玩家加入派对事件
                    Bukkit.getPluginManager().callEvent(new LeavePartyEvent(player, party));
                    return true;
                } else {
                    MessageSender.sendMessage(player, "未加入游戏！");
                }
            } else if (parameter1.equalsIgnoreCase("out")) {
                //玩家不在游戏内
                if (!EasyGameLauncher.playerInParty.containsKey(player)) {
                    MessageSender.sendMessage(player, "未加入任何派对");
                    return true;
                }
                Party party = EasyGameLauncher.playerInParty.get(player);

                //生命周期还没有进入能够淘汰的时候
                if (party.getLifecycle_() < (Lifecycle.OUT)) {
                    MessageSender.sendMessage(commandSender, "游戏还没开始");
                    return true;
                }
                //玩家不在场上
                //!party.getPresentMembers().contains(player) 玩家就被淘汰了
                if (!party.getPresentMembers().contains(player)) {
                    MessageSender.sendMessage(commandSender, "玩家已被淘汰");
                    return true;
                }


                party.getPresentMembers().remove(player);
                Bukkit.getPluginManager().callEvent(new PlayerOutEvent(party, player));
                return true;
            }
        }

        //create party_type party_name
        if (parameter1.equalsIgnoreCase("create")) {
            if (strings.length != 3) {
                MessageSender.sendMessage(commandSender, "/easygamelauncher create <party_type> <party_name>");
                return true;
            }

            String party_type = strings[1];
            String party_name = strings[2];

            if (EasyGameLauncher.partyConfigs.containsKey(party_type)) {
                if (!EasyGameLauncher.partyMap.containsKey(party_name)) {
                    Party party = new Party(party_name, EasyGameLauncher.partyConfigs.get(party_type));

                    //创建成功
                    EasyGameLauncher.partyMap.put(party_name, party);
                    Bukkit.getPluginManager().callEvent(new PartyCreateEvent(party));
                    MessageSender.sendMessage(commandSender, "创建" + party_type + "，派对名为" + party_name);
                } else {
                    MessageSender.sendMessage(commandSender, "派对名" + party_name + "已存在");
                }

            }
        }
        return false;
    }
}
