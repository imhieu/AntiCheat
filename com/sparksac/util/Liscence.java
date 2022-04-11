package com.sparksac.util;

import org.bukkit.plugin.*;
import org.bukkit.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class Liscence
{
    private Plugin plugin;
    private Liscence.LogType logType;
    private String securityKey;
    private String validationServer;
    private boolean debug;
    private String licenseKey;
    
    public boolean register() {
        this.log(0, "----------[SpookyAC 2.0]----------");
        this.log(0, "Checking your liscence...");
        if (this.isValid() == Liscence.ValidationType.VALID) {
            this.log(1, "License is valid!");
            this.log(0, "----------[SpookyAC 2.0]----------");
            return true;
        }
        this.log(1, "License is not valid!");
        this.log(1, "Disabling plugin!");
        this.log(0, "----------[SpookyAC 2.0]----------");
        Bukkit.getScheduler();
        return false;
    }
    
    private void log(final int n, final String s) {
        if (this.logType == Liscence.LogType.NONE || (this.logType == Liscence.LogType.LOW && n == 0)) {
            return;
        }
        System.out.println(s);
    }
    
    public Liscence debug() {
        this.debug = true;
        return this;
    }
    
    private String toBinary(final String s) {
        final byte[] bytes = s.getBytes();
        final StringBuilder sb = new StringBuilder();
        byte[] array;
        for (int length = (array = bytes).length, i = 0; i < length; ++i) {
            int n = array[i];
            for (int j = 0; j < 8; ++j) {
                sb.append(((n & 0x80) != 0x0) ? 1 : 0);
                n <<= 1;
            }
        }
        return String.valueOf(sb);
    }
    
    private static String xor(final String s, final String s2) {
        String value = "";
        for (int i = 0; i < ((s.length() < s2.length()) ? s.length() : s2.length()); ++i) {
            value = String.valueOf(new StringBuilder(String.valueOf(value)).append(Byte.valueOf(String.valueOf(new StringBuilder().append(s.charAt(i)))) ^ Byte.valueOf(String.valueOf(new StringBuilder().append(s2.charAt(i))))));
        }
        return value;
    }
    
    public Liscence setSecurityKey(final String securityKey) {
        this.securityKey = securityKey;
        return this;
    }
    
    public boolean isValidSimple() {
        return this.isValid() == Liscence.ValidationType.VALID;
    }
    
    public Liscence.ValidationType isValid() {
        final String binary = this.toBinary(UUID.randomUUID().toString());
        final String binary2 = this.toBinary(this.securityKey);
        final String binary3 = this.toBinary(this.licenseKey);
        try {
            final URL url = new URL(String.valueOf(new StringBuilder(String.valueOf(this.validationServer)).append("?v1=").append(xor(binary, binary2)).append("&v2=").append(xor(binary, binary3)).append("&pl=").append(this.plugin.getName())));
            if (this.debug) {
                System.out.println(String.valueOf(new StringBuilder("RequestURL -> ").append(url.toString())));
            }
            final Scanner scanner = new Scanner(url.openStream());
            if (scanner.hasNext()) {
                final String next = scanner.next();
                scanner.close();
                try {
                    return Liscence.ValidationType.valueOf(next);
                }
                catch (IllegalArgumentException ex2) {
                    final String xor = xor(xor(next, binary3), binary2);
                    if (binary.substring(0, xor.length()).equals(xor)) {
                        return Liscence.ValidationType.VALID;
                    }
                    return Liscence.ValidationType.WRONG_RESPONSE;
                }
            }
            scanner.close();
            return Liscence.ValidationType.PAGE_ERROR;
        }
        catch (IOException ex) {
            if (this.debug) {
                ex.printStackTrace();
            }
            return Liscence.ValidationType.URL_ERROR;
        }
    }
    
    public Liscence(final String licenseKey, final String validationServer, final Plugin plugin) {
        this.logType = Liscence.LogType.NORMAL;
        this.securityKey = "YecoF0I6M05thxLeokoHuW8iUhTdIUInjkfF";
        this.debug = false;
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }
    
    public Liscence setConsoleLog(final Liscence.LogType logType) {
        this.logType = logType;
        return this;
    }
}
