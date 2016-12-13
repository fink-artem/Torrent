package com.torrent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
/***
 * Торрент сервер
 * 
 */

public class Server {
/***
 * 
 * @param myHost номер хоста, на котором находится сервер
 * @param par объект  TorrentParser 
 */
    
    static void run(InetSocketAddress myHost, TorrentParser par) {
        try (Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); ServerSocket serverSocket = serverSocketChannel.socket();) {
            serverSocket.bind(new InetSocketAddress(myHost.getPort()));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (0 == selector.select()) {
                    continue;
                }
                Set keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    Socket socket;
                    SocketChannel channel;
                    if (key.isAcceptable()) {
                        socket = serverSocket.accept();
                        channel = socket.getChannel();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        try {
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            int piece;
                            int length_piece = par.getPiecesLength();
                            System.out.println(length_piece);
                            byte buffer[] = new byte[length_piece];
                            ByteBuffer sharedBuffer = ByteBuffer.allocate(length_piece);
                            socketChannel.read(sharedBuffer);
                            sharedBuffer.flip();
                            CharBuffer charBuffer = StandardCharsets.US_ASCII.decode(sharedBuffer);
                            piece = Integer.parseInt(charBuffer.toString().substring(0, charBuffer.toString().indexOf("\r")));
                            InputStream in = new FileInputStream("./data/one.avi");
                            System.out.println(piece * length_piece + " " + length_piece);
                            in.skip(piece * length_piece);
                            int count = in.read(buffer, 0, length_piece);
                            System.out.println(count);
                            socketChannel.write(ByteBuffer.wrap(Arrays.copyOfRange(buffer, 0, count)));
                            sharedBuffer.clear();
                        } catch (StringIndexOutOfBoundsException ex) {
                        }
                    }
                }
                keys.clear();
            }
        } catch (IOException ex) {
        }
    }

}
