package com.torrent;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/***
 * Класс ArgsParser парсит аргументы
 * 
 */

public class ArgsParser {

    private static final int MIN_ARGS_LENGTH = 3;
    private static final int MIN_CLIENT_ARGS_LENGTH = 4;
    public static final int CLIENT_MODE = 0;
    public static final int SERVER_MODE = 1;
    private int mode;
    private InetSocketAddress myHost;
    private String filePath;
    private List<InetSocketAddress> listIp;
    
/***
 * Функция парсит аргументы и определяет, какой режим запущен
 * @param args аргументы
 * @throws BadArgumentsException  неверное количество аргументов, неверный формат аргументов, неверное значение типа
 */
    public void parse(String[] args) throws BadArgumentsException {
        if (args.length < MIN_ARGS_LENGTH) {
            throw new BadArgumentsException();
        }
        try {
            mode = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsException();
        }
        switch (mode) {
            case CLIENT_MODE:
                parseClientArgs(args);
                break;
            case SERVER_MODE:
                parseServerArgs(args);
                break;
            default:
                throw new BadArgumentsException();
        }
    }
/***
 * Функции парсит аргументы клиента
 * @param args аргументы клиента
 * @throws BadArgumentsException файл, представленный в документах, имеет формат, отличный от .torrent
 * или неверное количество аргументов
 */
    private void parseClientArgs(String[] args) throws BadArgumentsException {
        myHost = parseAddress(args[1]);
        filePath = args[2];
        if (!filePath.endsWith(".torrent")) {
            throw new BadArgumentsException();
        }
        if (args.length < MIN_CLIENT_ARGS_LENGTH) {
            throw new BadArgumentsException();
        }
        listIp = new ArrayList<>();
        for (int i = 3; i < args.length; i++) {
            listIp.add(parseAddress(args[i]));
        }
    }
/***
 * Функция парсит аргументы сервера
 * @param args аргументы с режимом 1
 * @throws BadArgumentsException  файл, представленный в документах, имеет формат, отличный от .torrent
 */
    private void parseServerArgs(String[] args) throws BadArgumentsException {
        myHost = parseAddress(args[1]);
        filePath = args[2];
        if (!filePath.endsWith(".torrent")) {
            throw new BadArgumentsException();
        }
    }
/**
 * 
 * @param address адрес формата xxx.xxx.xxx.xxxx:xxxx
 * @return класс inetSocketAddress
 * @throws BadArgumentsException 
 * возможная ошибка - неправильный формат входных данных,
 */
    private InetSocketAddress parseAddress(String address) throws BadArgumentsException {
        int search = address.indexOf(":");
        InetSocketAddress inetSocketAddress = null;
        try {
            String url = address.substring(0, search);
            int port = Integer.parseInt(address.substring(search + 1));
            inetSocketAddress = new InetSocketAddress(url, port);
        } catch (IllegalArgumentException e) {
            throw new BadArgumentsException();
        }
        return inetSocketAddress;
    }
/***
 * 
 * @return тип (Клиент или Сервер)
 */
    public int getMode() {
        return mode;
    }
/***
 * 
 * @return номер хоста
 */
    public InetSocketAddress getMyHost() {
        return myHost;
    }
/***
 * 
 * @return путь к файлу
 */
    public String getFilePath() {
        return filePath;
    }
/***
 * 
 * @return  список ip адресов для клиента
 */
    public List<InetSocketAddress> getListIp() {
        return listIp;
    }

}
