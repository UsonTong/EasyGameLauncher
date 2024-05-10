package com.github.usontong.easygamelauncher.command;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.api.MessageSender;
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
     * create party_name
     */
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {//权限验证
            return true;
        }

        String parameter1 = strings[0];

        //玩家加入游戏
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (parameter1.equalsIgnoreCase("join")) {
                //加入的代码

                //添加玩家到party
                if (EasyGameLauncher.PARTY.addMember(player)) {
                    //呼叫玩家加入派对事件
                    Bukkit.getPluginManager().callEvent(new JoinPartyEvent(player));
                    return true;
                } else  {
                    MessageSender.sendMessage(player, "已加入游戏！");
                }
            } else if (parameter1.equalsIgnoreCase("leave")) {
                //加入退出的代码

                //添加玩家到party
                if (EasyGameLauncher.PARTY.removeMember(player)) {
                    //呼叫玩家加入派对事件
                    Bukkit.getPluginManager().callEvent(new LeavePartyEvent(player));
                    return true;
                } else {
                    MessageSender.sendMessage(player, "未加入游戏！");
                }
            }
        } else {
            MessageSender.sendMessage(commandSender, "只有玩家能这么做！");
        }
        return false;
    }
}
