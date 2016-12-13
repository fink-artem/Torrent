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
/***
 * Очередь загрузчиков
 *
 */
public class QueueDownloader {

    private final List<InetSocketAddress> ipList;
    private List<Integer> pieceList = new ArrayList<>();
    private int length;
    private final int end = -1;
    private final Object monitor = new Object();
    private FileWriter out = null;
    private final String client = "./client/";
/***
 * 
 * @param ipList список ip адресов, у которых можно скачивать файл
 * @param numberPieces количество кусков для скачивания
 * @param myHost номер хоста клиента, который скачивает
 */
    public QueueDownloader(List<InetSocketAddress> ipList, int numberPieces, InetSocketAddress myHost) {
        String path = client + "client" + myHost + "/";
        File myPath = new File(path);
        myPath.mkdirs();
        this.ipList = ipList;
        for (int i = 0; i < numberPieces; i++) {
            pieceList.add(i);
        }
        try (Scanner reader = new Scanner(new FileInputStream(path + "data.txt"))) {
            while (reader.hasNext()) {
                pieceList.remove((Integer) reader.nextInt());
            }
        } catch (FileNotFoundException ex) {
        }
        length = pieceList.size();
        try {
            out = new FileWriter(path + "data.txt", true);
        } catch (IOException ex) {
        }
    }
/***
 * 
 * @return номер куска для скачивания
 */
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
/***
 * 
 * @return получить рандомный адрес для скачивания
 */
    
     
    InetSocketAddress getIP() {
        Random r = new Random();
        return ipList.get(r.nextInt(ipList.size()));
    }
/***
 * Записывает в выходной поток номер скаченного куска
 * @param piece номер куска
 * 
 */
    
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
