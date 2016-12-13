package com.torrent;

public class Main {
    
    /**
     * 
     * @param args параметр должен содержать следующие данные
     * первый аргумент: тип (для сервера = 1, для клиента = 0)
     * второй аргумент: адрес формата xxx.xxx.xxx.xxxx:xxxx
     * третий аргумент: файл расширения .torrent
     * Если выбран тип 0, то следующими параметрами передаются адреса для скачивания
     * @throws Exception  возникает, если неправильные входные данные
     */

    public static void main(String[] args) throws Exception {
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        TorrentParser torrentParser = new TorrentParser(argsParser.getFilePath());
        if (argsParser.getMode() == ArgsParser.CLIENT_MODE) {
             Client.run(argsParser, torrentParser);
        } else {
            Server.run(argsParser.getMyHost(),torrentParser);
        }
    }
}
