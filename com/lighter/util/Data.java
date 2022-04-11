package com.lighter.util;

import java.util.*;
import org.bukkit.entity.*;

public class Data
{
    private static ArrayList<Player> alertE;
    private static ArrayList<Player> banBroadcast;
    private static ArrayList<Player> alertC;
    private static ArrayList<Player> logSMCmd;
    private static ArrayList<Player> logCmd;
    
    public static ArrayList<Player> listLogSMCmd() {
        return Data.logSMCmd;
    }
    
    public static boolean isLogCmd(final Player player) {
        return listLogCmd().contains(player);
    }
    
    public static ArrayList<Player> listLogCmd() {
        return Data.logCmd;
    }
    
    public static boolean isAlertc(final Player player) {
        return listAlertc().contains(player);
    }
    
    public static boolean isAlerte(final Player player) {
        return listAlerte().contains(player);
    }
    
    public static ArrayList<Player> listAlertc() {
        return Data.alertC;
    }
    
    public static ArrayList<Player> listAlerte() {
        return Data.alertE;
    }
    
    public static boolean isLogSMCmd(final Player player) {
        return listLogSMCmd().contains(player);
    }
    
    static {
        Data.logCmd = new ArrayList<Player>();
        Data.logSMCmd = new ArrayList<Player>();
        Data.alertC = new ArrayList<Player>();
        Data.alertE = new ArrayList<Player>();
        Data.banBroadcast = new ArrayList<Player>();
    }
    
    public static boolean isBanBroadcast(final Player player) {
        return listBan().contains(player);
    }
    
    public static ArrayList<Player> listBan() {
        return Data.banBroadcast;
    }
}
