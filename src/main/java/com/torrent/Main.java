package com.torrent;

public class Main {

    public static void main(String[] args) throws Exception {
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        TorrentParser torrentParser = new TorrentParser(argsParser.getFilePath());
        if (argsParser.getMode() == ArgsParser.CLIENT_MODE) {
            Client.run(argsParser.getFilePath());
        } else {
            Server.run(argsParser.getMyHost(),torrentParser);
        }
    }
}
