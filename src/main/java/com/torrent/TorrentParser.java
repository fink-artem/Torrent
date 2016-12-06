
package com.torrent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.ardverk.coding.*;

public class TorrentParser {
  
    private List<String> name = new ArrayList<>();
    private List<Integer> length = new ArrayList<>();
    private int all_length = 0;
    private int piece_length;
    private String pieces;
    private int number_pieces;
    private final List<String> list_pieces = new ArrayList<>();
    private final int length_hash = 20;
    private int number_files;

    TorrentParser(String path) throws IOException {
        try (BencodingInputStream in = new BencodingInputStream(new FileInputStream(path))) {
            Map<String, ?> s = in.readMap();
            Map<String, ?> s2 = (Map<String, ?>) s.get("info");
            if (s2.containsKey("files")) {
                List s3 = (List<?>) s2.get("files");
                number_files = s3.size();
                for (int i = 0; i < number_files; i++) {
                    Map<String, ?> map = (Map<String, ?>) s3.get(i);
                    length.add(Integer.parseInt(map.get("length").toString()));
                    all_length += length.get(i);
                    name.add(new String((byte[]) ((List<?>) map.get("path")).get(0), "windows-1251"));
                }
            } else {
                length.add(Integer.parseInt(s2.get("length").toString()));
                name.add(new String((byte[]) s2.get("name"), "windows-1251"));
                number_files = 1;
                all_length += length.get(0);
            }
            piece_length = Integer.parseInt(s2.get("piece length").toString());
            pieces = new String((byte[]) s2.get("pieces"), "windows-1251");
            number_pieces = pieces.length() / length_hash;
            for (int i = 0; i < number_pieces; i++) {
                list_pieces.add(pieces.substring(i * length_hash, (i + 1) * length_hash));
            }
        } catch (FileNotFoundException ex) {
        }
    }

    List<String> getName() {
        return name;
    }

    List<Integer> getLength() {
        return length;
    }

    int getPiecesLength() {
        return piece_length;
    }

    List<String> getPieces() {
        return list_pieces;
    }

    int getNumberPieces() {
        return number_pieces;
    }

    int getNumberFiles() {
        return number_files;
    }
    
    int getAllLength(){
        return all_length;
    }

}


