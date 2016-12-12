package com.torrent;

public class Main {

    public static void main(String[] args) throws BadArgumentsException {
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        if (argsParser.getMode() == ArgsParser.CLIENT_MODE) {
            Client.run(argsParser.getFilePath());
        } else {
            Server.run(argsParser.getMyHost());
        }
    }
}
