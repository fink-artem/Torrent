package com.torrent;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
/***
 * 
 * Класс загрузчик
 */
public class Downloader implements Runnable{

    private final QueueDownloader queue;
    private final List<String> pieces;
    private final int end = -1;
    private final int lengthPiece;
    private final int numberPiece;
    private final int lastLengthPiece;
    private final String path;
/***
 * 
 * @param queue очередь ip адресов, у которых можно скачивать
 * @param pieces список кусков для скачивания
 * @param length_piece длина каждого куска
 * @param length общая длина
 * @param path  путь к файлу
 */
    public Downloader(QueueDownloader queue, List<String> pieces, int length_piece, int length, String path) {
        this.path = path;
        this.queue = queue;
        this.pieces = pieces;
        this.lengthPiece = length_piece;
        this.numberPiece = pieces.size() - 1;
        this.lastLengthPiece = length - this.numberPiece * lengthPiece;
    }
/***
 * Запускает скачивание и запись файла
 */
    @Override
    public void run(){
        InetSocketAddress ip_downloader = queue.getIP();
        int piece;
        int count = 0;
        byte[] buffer = new byte[lengthPiece];
        try (Socket socket = new Socket(ip_downloader.getHostName(), ip_downloader.getPort()); InputStream in = socket.getInputStream();) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            try (RandomAccessFile file = new RandomAccessFile(path + "text.txt", "rw")) {
                while ((piece = queue.getPiece()) != end) {
                    one:
                    do {
                        out.println(piece);
                        count = 0;
                        while (count != lengthPiece && (piece != numberPiece || count != lastLengthPiece)) {
                            count += in.read(buffer, count, lengthPiece - count);
                            if (-1 == count) {
                                continue one;
                            }
                        }
                        if (new String(DigestUtils.getSha1Digest().digest(buffer), "windows-1251").compareTo(pieces.get(piece)) == 0) {
                            break;
                        } else {
                            if (piece == numberPiece && new String(DigestUtils.getSha1Digest().digest(Arrays.copyOfRange(buffer, 0, count)), "windows-1251").compareTo(pieces.get(piece)) == 0) {
                                break;
                            }
                        }
                    } while (true);
                    System.out.println(piece);
                    queue.setPiece(piece);
                    file.seek(piece * lengthPiece);
                    file.write(buffer, 0, count);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println(ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }  
}
