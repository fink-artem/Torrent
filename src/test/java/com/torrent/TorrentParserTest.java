package com.torrent;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TorrentParserTest {

    @Test
    public void testPiecesLength() throws BadArgumentsException {
        String path = "data_torrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getPiecesLength()==16384);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
    @Test
    public void testNumberPieces() throws BadArgumentsException {
        String path = "data_torrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getNumberPieces()==51);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
    @Test
    public void testAllLength() throws BadArgumentsException {
        String path = "data_torrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getAllLength()==823177);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
    @Test
    public void testBadArgument() throws BadArgumentsException {
        String path = "data_orrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            
        } catch (IOException ex) {
            assertTrue(true);
        }
    }
    @Test
    public void testNumberFiles() throws BadArgumentsException {
        String path = "data_torrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getNumberFiles()==1);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
    @Test
    public void testListPieces() throws BadArgumentsException {
        String path = "data_torrent.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getPieces().size()==51);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
    @Test
    public void testManyFiles() throws BadArgumentsException {
        String path = "data.torrent";
        try {
            TorrentParser torrentParser = new TorrentParser(path);
            assertTrue(torrentParser.getName().size() == 7);
        } catch (IOException ex) {
            throw  new BadArgumentsException();
        }
    }
}
