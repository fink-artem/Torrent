package com.torrent;


public class Client {
   

    static void run(ArgsParser argsParser, TorrentParser torrentParser){
       
        QueueDownloader queueDownloader;
        queueDownloader = new QueueDownloader(argsParser.getListIp(), torrentParser.getNumberPieces(),argsParser.getMyHost());
       
     
    }
    
}
