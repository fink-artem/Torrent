package com.torrent;

import java.io.IOException;

public class Client {
   

    static void run(String path) throws BadArgumentsException{
        try {
            TorrentParser torrentParser = new TorrentParser(path);
        } catch (IOException ex) {
            throw new BadArgumentsException();
        }
     
    }
    
}
