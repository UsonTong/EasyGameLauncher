package com.github.usontong.easygamelauncher.entity;

import java.util.HashMap;

public class PartyTrigger {
    private final Class<?> clazz;
    private final HashMap<String, String> targetSkill = new HashMap<>();

    @Override
    public String toString() {
        return "PartyTrigger{" +
                "clazz=" + clazz +
                ", targetSkill=" + targetSkill +
                '}';
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public HashMap<String, String> getTargetSkill() {
        return targetSkill;
    }

    public PartyTrigger(Class<?> clazz) {
        this.clazz = clazz;
    }
}
