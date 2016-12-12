package com.torrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QueueDownloader {

    private final List<InetSocketAddress> ipList;
    private List<Integer> pieceList = new ArrayList<>();
    private int length;
    private final int end = -1;
    private final Object monitor = new Object();
    private FileWriter out = null;
    private final String client = "./client/";

    public QueueDownloader(List<InetSocketAddress> ipList, int numberPieces, InetSocketAddress myHost) {
        for (int i = 0; i < numberPieces; i++) {
            pieceList.add(i);
        }
        this.ipList = ipList;
        length = pieceList.size();
        
    }

    int getPiece() {
        int work;
        synchronized (monitor) {
            if (0 == length) {
                return end;
            } else {
                work = pieceList.get(0);
                pieceList.remove(0);
                length--;
            }
        }
        return work;
    }

    InetSocketAddress getIP() {
        Random r = new Random();
        return ipList.get(r.nextInt(ipList.size()));
    }

    void setPiece(int piece) {
        synchronized (monitor) {
            try {
                out.write(piece + "\n");
                out.flush();
            } catch (IOException ex) {
            }
        }
    }

}
