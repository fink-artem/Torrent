package com.torrent;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ArgsParser {

    private static final int MIN_ARGS_LENGTH = 3;
    private static final int MIN_CLIENT_ARGS_LENGTH = 4;
    public static final int CLIENT_MODE = 0;
    public static final int SERVER_MODE = 1;
    private int mode;
    private InetSocketAddress myHost;
    private String filePath;
    private List<InetSocketAddress> listIp;

    public void parse(String[] args) throws BadArgumentsException {
        if (args.length < MIN_ARGS_LENGTH) {
            throw new BadArgumentsException();
        }
        try {
            mode = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsException();
        }
        switch (mode) {
            case CLIENT_MODE:
                parseClientArgs(args);
                break;
            case SERVER_MODE:
                parseServerArgs(args);
                break;
            default:
                throw new BadArgumentsException();
        }
    }

    private void parseClientArgs(String[] args) throws BadArgumentsException {
        myHost = parseAddress(args[1]);
        filePath = args[2];
        if (!filePath.endsWith(".torrent")) {
            throw new BadArgumentsException();
        }
        if (args.length < MIN_CLIENT_ARGS_LENGTH) {
            throw new BadArgumentsException();
        }
        listIp = new ArrayList<>();
        for (int i = 3; i < args.length; i++) {
            listIp.add(parseAddress(args[i]));
        }
    }

    private void parseServerArgs(String[] args) throws BadArgumentsException {
        myHost = parseAddress(args[1]);
        filePath = args[2];
        if (!filePath.endsWith(".torrent")) {
            throw new BadArgumentsException();
        }
    }

    private InetSocketAddress parseAddress(String address) throws BadArgumentsException {
        int search = address.indexOf(":");
        InetSocketAddress inetSocketAddress = null;
        try {
            String url = address.substring(0, search);
            int port = Integer.parseInt(address.substring(search + 1));
            inetSocketAddress = new InetSocketAddress(url, port);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentsException();
        }
        return inetSocketAddress;
    }

    public int getMode() {
        return mode;
    }

    public InetSocketAddress getMyHost() {
        return myHost;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<InetSocketAddress> getListIp() {
        return listIp;
    }

}
