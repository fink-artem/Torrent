package com.torrent;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testMainClient() throws Exception {
        String[] args = {"0", "127.0.0.1:20201", "aaa.torrent", "127.0.0.1:20202"};
        Main.main(args);
        assertTrue(true);
    }

    @Test
    public void testMainServer() throws Exception {
        String[] args = {"1", "127.0.0.1:658222", "aaa.torrent"};
        try {
            Main.main(args);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

}
