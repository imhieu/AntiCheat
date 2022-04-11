package de.xbrowniecodez.pluginprotect;

import java.io.*;
import java.util.zip.*;
import java.lang.invoke.*;

public class Bootstrap
{
    public static boolean done;
    
    static {
        Bootstrap.done = false;
    }
    
    public static Object bootstrapMethod(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        if (o == null && o2 == null && o3 == null && o4 == null && o5 == null && o6 == null && o7 == null && o8 == null && o9 == null) {
            try {
                final char[] charArray = ((String)o10).toCharArray();
                final char[] array = new char[charArray.length];
                final char[] array2 = { '\u4832', '\u2385', '\u2386', '\u9813', '\u9125', '\u4582', '\u0913', '\u3422', '\u0853', '\u0724' };
                final char[] array3 = { '\u4820', '\u8403', '\u8753', '\u3802', '\u3840', '\u3894', '\u8739', '\u1038', '\u8304', '\u3333' };
                for (int i = 0; i < charArray.length; ++i) {
                    array[i] = (char)(charArray[i] ^ array2[i % array2.length]);
                }
                final char[] array4 = new char[array.length];
                for (int j = 0; j < charArray.length; ++j) {
                    array4[j] = (char)(array[j] ^ array3[j % array3.length]);
                }
                return new String(array4);
            }
            catch (Exception ex2) {
                return o10;
            }
        }
        if (!Bootstrap.done) {
            try {
                Class.forName(xor("}\ua7f4\ua4b2\ua03f\ua907\u7d63\u8e41\u2471\u8b3e\u3463<\ua7e5\ua4ba\ua07c\ua908\u7d77\u8e44\u247e\u8b79\u3454}\ua7e8\ua4a6\ua07e\ua909\u7d73\u8e69\u2475\u8b3a\u347as\ua7e8\ua4b1\ua042\ua900\u7d78\u8e4e\u247f\u8b25")).getMethod(xor("a\ua7e3\ua4bb\ua075\ua928\u7d73\u8e59\u2469\u8b36\u3470w"), String.class).invoke(Class.forName(xor("}\ua7f4\ua4b2\ua03f\ua907\u7d63\u8e41\u2471\u8b3e\u3463<\ua7c4\ua4a0\ua07a\ua90e\u7d7f\u8e5e")).getMethod("getConsoleSender", (Class<?>[])new Class[0]).invoke(null, new Object[0]), xor("µ\ua7e5\ua48e\ua05d\ua90c\u7d71\u8e42\u246e\u8b32\u3465O\ua7a6\ua496\ua063\ua904\u7d75\u8e41\u247f\u8b33\u3437p\ua7ff\ua4f5\ua05b\ua90a\u7d73"));
            }
            catch (Throwable t) {}
            Bootstrap.done = true;
        }
        try {
            new ZipFile(new File(Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())).entries();
            final MethodHandles.Lookup lookup = (MethodHandles.Lookup)o;
            MethodHandle type;
            try {
                final Class<?> forName = Class.forName(o7.toString());
                final MethodType fromMethodDescriptorString = MethodType.fromMethodDescriptorString(o9.toString(), Bootstrap.class.getClassLoader());
                MethodHandle methodHandle = null;
                switch (Integer.valueOf(String.valueOf(String.valueOf(o4.toString())) + o5.toString() + o6.toString())) {
                    case 184: {
                        methodHandle = lookup.findStatic(forName, o8.toString(), fromMethodDescriptorString);
                        break;
                    }
                    case 182:
                    case 185: {
                        methodHandle = lookup.findVirtual(forName, o8.toString(), fromMethodDescriptorString);
                        break;
                    }
                    default: {
                        throw new BootstrapMethodError();
                    }
                }
                type = methodHandle.asType((MethodType)o3);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new BootstrapMethodError();
            }
            return new ConstantCallSite(type);
        }
        catch (Throwable t2) {
            return null;
        }
    }
    
    private static String xor(final String s) {
        try {
            final char[] charArray = s.toCharArray();
            final char[] array = new char[charArray.length];
            final char[] array2 = { '\u4832', '\u2385', '\u2386', '\u9813', '\u9125', '\u4582', '\u0913', '\u3422', '\u0853', '\u0724' };
            final char[] array3 = { '\u4820', '\u8403', '\u8753', '\u3802', '\u3840', '\u3894', '\u8739', '\u1038', '\u8304', '\u3333' };
            for (int i = 0; i < charArray.length; ++i) {
                array[i] = (char)(charArray[i] ^ array3[i % array3.length]);
            }
            final char[] array4 = new char[array.length];
            for (int j = 0; j < charArray.length; ++j) {
                array4[j] = (char)(array[j] ^ array2[j % array2.length]);
            }
            return new String(array4);
        }
        catch (Exception ex) {
            return s;
        }
    }
}
