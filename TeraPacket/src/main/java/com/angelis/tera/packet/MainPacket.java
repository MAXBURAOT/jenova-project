/**
 * 
 */
package com.angelis.tera.packet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.UIManager;

import javolution.text.TextBuilder;
import javolution.util.FastMap;

import com.angelis.tera.packet.captor.Captor;
import com.angelis.tera.packet.captor.ConsoleUserInterface;
import com.angelis.tera.packet.captor.IUserInterface;
import com.angelis.tera.packet.gui.Main;
import com.angelis.tera.packet.parser.PartTypeManager;
import com.angelis.tera.packet.protocol.Protocol;
import com.angelis.tera.packet.protocol.ProtocolManager;

/**
 * @author Ulysses R. Ribeiro
 * 
 */
public class MainPacket {
    public enum VerboseLevel {
        QUIET, NORMAL, VERBOSE, VERY_VERBOSE
    };

    public static Properties _loggerProperties = new Properties();

    public static final String CONFIG_FILE = "config" + File.separator + "ps.properties";

    public static IUserInterface USER_INTERFACE;

    public static boolean PARSER_ACTIVE;

    public static boolean DECRYPT_ACTIVE;

    public static boolean LIVE_CAPTOR_ACTIVE;

    public static VerboseLevel VERBOSITY;

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.loadLibrary("jpcap");
        
        MainPacket.loadConfig();

        PartTypeManager.initBaseTypes();

        if (MainPacket.getConfigProperty("LookAndFeel", "System").equals("System")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        PARSER_ACTIVE = true;
        DECRYPT_ACTIVE = true;
        LIVE_CAPTOR_ACTIVE = true;
        VERBOSITY = VerboseLevel.NORMAL;

        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equalsIgnoreCase("-nogui")) {
                    if (USER_INTERFACE != null) {
                        System.out.println("You can not use both -nogui and -gui");
                        continue;
                    }
                    USER_INTERFACE = new ConsoleUserInterface();
                    PARSER_ACTIVE = false;
                }
                else if (arg.equalsIgnoreCase("-gui")) {
                    if (USER_INTERFACE != null) {
                        System.out.println("You can not use both -nogui and -gui");
                        continue;
                    }
                    USER_INTERFACE = new Main();
                }
                else if (arg.equalsIgnoreCase("-noparser")) {
                    PARSER_ACTIVE = false;
                }
                else if (arg.equalsIgnoreCase("-parser")) {
                    if (!DECRYPT_ACTIVE) {
                        System.out.println("You can not activate parsing when decrypt is disabled");
                        continue;
                    }
                    PARSER_ACTIVE = true;
                }
                else if (arg.equalsIgnoreCase("-nodecrypt")) {
                    if (PARSER_ACTIVE) {
                        System.out.println("You can not desactivate decrypting when parsing is enabled");
                        continue;
                    }
                    DECRYPT_ACTIVE = false;
                }
                else if (arg.equalsIgnoreCase("-decrypt")) {
                    DECRYPT_ACTIVE = true;
                }
                else if (arg.equalsIgnoreCase("-nocaptor")) {
                    LIVE_CAPTOR_ACTIVE = false;
                }
                else if (arg.equalsIgnoreCase("-captor")) {
                    LIVE_CAPTOR_ACTIVE = true;
                }
                else if (arg.equalsIgnoreCase("-q")) {
                    VERBOSITY = VerboseLevel.QUIET;
                }
                else if (arg.equalsIgnoreCase("-v")) {
                    VERBOSITY = VerboseLevel.VERBOSE;
                }
                else if (arg.equalsIgnoreCase("-vv")) {
                    VERBOSITY = VerboseLevel.VERY_VERBOSE;
                }
            }
        }
        
        if (USER_INTERFACE == null) {
            Main ui = new Main();
            USER_INTERFACE = ui;
            ui.init();
        }
        
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        if (LIVE_CAPTOR_ACTIVE) {
            Captor.getInstance();
        }
    }

    public static IUserInterface getUserInterface() {
        return USER_INTERFACE;
    }

    public static void saveConfig() {
        File config = new File(CONFIG_FILE);

        try {
            boolean configExists = config.exists();
            if (!configExists) {
                new File(config.getParent()).mkdirs();
                configExists = config.createNewFile();
            }

            if (configExists) {
                try {
                    OutputStream outputStream = new FileOutputStream(config);
                    _loggerProperties.store(outputStream, "Packet Samurai Config");
                }
                catch (IOException ioe) {
                    MainPacket.getUserInterface().log("ERROR: Failed to save config: " + ioe.toString());
                }
            }
        }
        catch (IOException ioe) {
            MainPacket.getUserInterface().log("ERROR: Failed to create the config: " + ioe.toString());
        }
    }

    public static void loadConfig() {
        File config = new File(CONFIG_FILE);

        if (config.exists()) {
            try {
                InputStream inputStream = new FileInputStream(config);
                _loggerProperties.load(inputStream);
                inputStream.close();
            }
            catch (Exception e) {
                MainPacket.getUserInterface().log("ERROR: Failed to laod config: " + e.toString());
            }
        }
    }

    public static void setConfigProperty(String property, Object value) {
        _loggerProperties.setProperty(property, value.toString());
    }

    public static String getConfigProperty(String property) {
        return MainPacket.getConfigProperty(property, "");
    }

    public static boolean configExists(String property) {
        return _loggerProperties.containsKey(property);
    }

    public static String getConfigProperty(String property, String defaultValue) {
        // if the value is not in the config file yet add it with default value
        if (!_loggerProperties.containsKey(property))
            MainPacket.setConfigProperty(property, defaultValue);
        return _loggerProperties.getProperty(property, defaultValue);
    }

    public static Map<Integer, Protocol> loadSnifferActiveProtocols() {
        if (_loggerProperties.containsKey("SnifferProtocols")) {
            Map<Integer, Protocol> activeProtocols = new FastMap<Integer, Protocol>();
            String[] protocols = MainPacket.getConfigProperty("SnifferProtocols").split(";");
            for (String activeProtocol : protocols) {
                String[] parts = activeProtocol.split(":");
                try {
                    int port = Integer.parseInt(parts[0]);
                    Protocol p = ProtocolManager.getInstance().getProtocolByName(parts[1]);

                    if (p != null) {
                        // ignore this entry
                        if (port != p.getPort()) {
                            MainPacket.getUserInterface().log("Port does not match for protocol " + p.getName());
                            continue;
                        }
                        activeProtocols.put(port, p);
                    }
                    else {
                        MainPacket.getUserInterface().log("ERROR: While retrieveing active sniffing protocol from config. Protocol [" + parts[1] + "] was not found.");
                    }
                }
                catch (NumberFormatException e) {
                    // msg should be: stop playing with the config mannualy noob
                    MainPacket.getUserInterface().log("ERROR: While retrieveing active sniffing protocol from config, invalid port.");
                }
            }
            return activeProtocols;
        }
        return null;
    }

    public static void saveSnifferActiveProtocols() {
        TextBuilder tb = new TextBuilder();
        int i = 0;
        for (Entry<Integer, Protocol> entry : Captor.getActiveProtocols().entrySet()) {
            if (i++ > 0) {
                tb.append(";");
            }
            tb.append(entry.getKey() + ":" + entry.getValue().getName());
        }
        MainPacket.setConfigProperty("SnifferProtocols", tb.toString());
    }

    public static String hexDump(byte[] data) {
        int size = data.length;
        String out = "";
        // char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            Byte b = data[i];
            out = out + " " + (b & 0xFF);
        }
        return out;
    }

    static class ShutdownHook extends Thread {
        @Override
        public void run() {
            USER_INTERFACE.close();
        }
    }
}
