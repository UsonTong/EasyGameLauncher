package com.github.usontong.easygamelauncher.command;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
import com.github.usontong.easygamelauncher.api.PartyAPI;
import com.github.usontong.easygamelauncher.entity.Party;
import com.github.usontong.easygamelauncher.event.JoinPartyEvent;
import com.github.usontong.easygamelauncher.event.LeavePartyEvent;
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
                Party party = PartyAPI.getPartyByPartyName(party_name);
                if (party == null) {
                    MessageSender.sendMessage(commandSender, "派对不存在");
                    return true;
                }

                //添加玩家到party
                if (party.addMember(player)) {
                    //呼叫玩家加入派对事件
                    Bukkit.getPluginManager().callEvent(new JoinPartyEvent(player, party));
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
                Party party = PartyAPI.getPartyByPartyName(party_name);
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
            }
        } else {
            MessageSender.sendMessage(commandSender, "只有玩家能这么做！");
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
                    MessageSender.sendMessage(commandSender, "创建" + party_type + "，派对名为" + party_name);
                } else {
                    MessageSender.sendMessage(commandSender, "派对名" + party_name + "已存在");
                }

            }
        }
        return false;
    }
}
