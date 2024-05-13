package com.github.usontong.easygamelauncher.entity;

public interface Lifecycle {
    /**
     * ERROR 不可能出现的周期
     * CREATE 此时派对创建完毕
     * JOIN 开始加入玩家
     * START 开启游戏
     * OUT 玩家淘汰
     * END 游戏结束
     */
    int ERROR = -1;
    int CREATE = 0;
    int JOIN = 1;
    int START = 2;
    int OUT = 3;
    int END = 4;
}
