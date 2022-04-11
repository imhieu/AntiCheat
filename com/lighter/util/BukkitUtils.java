package com.lighter.util;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.*;
import java.io.*;

public class BukkitUtils
{
    public static int getPotionLevel(final Player player, final PotionEffectType potionEffectType) {
        final Iterator<PotionEffect> iterator = new ArrayList<PotionEffect>(player.getActivePotionEffects()).iterator();
        while (iterator.hasNext()) {
            final PotionEffect potionEffect;
            if ((potionEffect = iterator.next()).getType().getId() == potionEffectType.getId()) {
                return potionEffect.getAmplifier() + 1;
            }
        }
        return 0;
    }
    
    public static String getFromFile(final String s) {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(s));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            return String.valueOf(sb);
        }
        catch (Exception ex) {
            return "-1";
        }
    }
    
    public static void log(final Player player, final String s) {
        final String value = String.valueOf(new StringBuilder().append("plugins/Lighter/data/").append(player.getName().toLowerCase()).append(".LighterLog"));
        final File file = new File(value.replace(value.split("/")[value.split("/").length - 1], ""));
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            try (final PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(new StringBuilder().append("plugins/Lighter/data/").append(player.getName().toLowerCase()).append(".LighterLog")), true)))) {
                printWriter.println(s);
                if (printWriter != null) {
                    final Throwable t;
                    if (t != null) {
                        try {
                            printWriter.close();
                        }
                        catch (Throwable t2) {
                            t.addSuppressed(t2);
                        }
                    }
                    else {
                        printWriter.close();
                    }
                }
            }
            new PrintWriter(new BufferedWriter(new FileWriter(String.valueOf(new StringBuilder().append("plugins/Lighter/data/").append(player.getName().toLowerCase()).append(".LighterLog")), true))).close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(String.valueOf(new StringBuilder().append("[Lighter] Warning: Unable to write log for ").append(player.getName()).append("!")));
        }
    }
}
