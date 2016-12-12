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

    private final List<InetSocketAddress> ip_list;
    private List<Integer> piece_list = new ArrayList<>();
    private int length;
    private final int end = -1;
    private final Object monitor = new Object();
    private FileWriter out = null;
    private String client = "./client/";

    public QueueDownloader(List<InetSocketAddress> ip_list, int number_pieces, InetSocketAddress my_host) {
        String path = client + "client" + my_host.getHostName() + "/";
        File myPath = new File(path);
        myPath.mkdirs();
        this.ip_list = ip_list;
        for (int i = 0; i < number_pieces; i++) {
            piece_list.add(i);
        }
        try (Scanner reader = new Scanner(new FileInputStream(path + "data.txt"))) {
            while (reader.hasNext()) {
                piece_list.remove((Integer) reader.nextInt());
            }
        } catch (FileNotFoundException ex) {
        }
        length = piece_list.size();
        try {
            out = new FileWriter(path + "data.txt", true);
        } catch (IOException ex) {
        }
    }

    int getPiece() {
        int work;
        synchronized (monitor) {
            if (0 == length) {
                return end;
            } else {
                work = piece_list.get(0);
                piece_list.remove(0);
                length--;
            }
        }
        return work;
    }

    InetSocketAddress getIP() {
        Random r = new Random();
        return ip_list.get(r.nextInt(ip_list.size()));
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
