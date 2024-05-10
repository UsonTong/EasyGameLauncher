package com.github.usontong.easygamelauncher.api;

import com.github.usontong.easygamelauncher.EasyGameLauncher;
import com.github.usontong.easygamelauncher.entity.Party;

public class PartyAPI {

    //通过派对名获得派对对象
    public static Party getPartyByPartyName(String party_name) {
        return EasyGameLauncher.partyMap.getOrDefault(party_name, null);
    }
}
