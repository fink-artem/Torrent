package com.torrent;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ParserArgTest {

    @Test
    public void testParserMode() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        assertTrue(argsParser.getMode() == 0);
    }

    @Test
    public void testParserPort() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        assertTrue(argsParser.getMyHost().getPort() == 20201);
    }

    @Test
    public void testParserFile() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        assertTrue(argsParser.getFilePath().equals("aaa.torrent"));
    }

    @Test
    public void testParserHostName() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        assertTrue(argsParser.getMyHost().getHostName().equals("127.0.0.1"));
    }

    @Test
    public void testParserExpMode() throws Exception {
        String[] args = {"7", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParserExpFile() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.t", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParserNumberArg() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.t"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParserList() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202", "127.0.0.1:20202", "127.0.0.1:20202", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        argsParser.parse(args);
        assertTrue(argsParser.getListIp().size() == 4);

    }

    @Test
    public void testParserArgParam() throws Exception {
        String[] args = {"0", "127.0.0.1:20201"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }

    }
    @Test
    public void testParserArgParamClient() throws Exception {
        String[] args = {"0", "127.0.0.1:20201","a.torrent"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testParserServerFile() throws Exception {
        String[] args = {"1", "127.0.0.1:20201", "aaa.t", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testParserServer() throws Exception {
        String[] args = {"1ss", "127.0.0.1:20201", "aaa.t", "127.0.0.1:20202"};
        ArgsParser argsParser = new ArgsParser();
        try {
            argsParser.parse(args);
        } catch (BadArgumentsException e) {
            assertTrue(true);
        }
    }
    @Test
    public void testParserSer() throws Exception {
        String[] args = {"1", "127.0.0.1:20201", "aaa.torrent"};
        ArgsParser argsParser = new ArgsParser();
       
            argsParser.parse(args);
            assertTrue(argsParser.getMode() == 1);
        
    }
     @Test
    public void testParserSerMode() throws Exception {
        String[] args = {"1", "127.0.0.1:20201", "aaa.torrent"};
        ArgsParser argsParser = new ArgsParser();
       
            argsParser.parse(args);
            assertTrue(argsParser.getMode() == 1);
        
    }
}
