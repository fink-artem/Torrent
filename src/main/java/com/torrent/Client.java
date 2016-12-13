package com.torrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;
/***
 * 
 *Торрент клиент
 */
public class Client {

    private static final String CLIENT = "./client/";
/**
 * Клиент запускает загрузку файла, передаваемого его в параметры
 * @param argsParser объект класса ArgsParser, содержащий в себе ip адреса серверов
 * @param torrentParser объект класса TorrentParser, содержащий в себе распарсенный torrent файл.
 */
    
    static void run(ArgsParser argsParser, TorrentParser torrentParser) {

        QueueDownloader queueDownloader  = new QueueDownloader(argsParser.getListIp(), torrentParser.getNumberPieces(), argsParser.getMyHost());
        String path = CLIENT + "client" + argsParser.getMyHost().getHostName() + "/";
        Downloader downloader = new Downloader(queueDownloader, torrentParser.getPieces(), torrentParser.getPiecesLength(), torrentParser.getAllLength(), path);
        downloader.run();
        List<String> name = torrentParser.getName();
        List<Integer> lenght = torrentParser.getLength();
        int offset = 0;
        try (RandomAccessFile in = new RandomAccessFile(path + "text.txt", "rw")) {
            int number = torrentParser.getNumberFiles();
            for (int i = 0; i < number; i++) {
                try (OutputStream out = new FileOutputStream(path + name.get(i))) {
                    int prom = lenght.get(i);
                    byte[] buffer = new byte[prom];
                    in.seek(offset);
                    in.read(buffer, 0, prom);
                    offset += prom;
                    out.write(buffer);
                }
            }
        } catch (IOException e) {
        }
        
    }

}
