package com.torrent;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testMain() throws BadArgumentsException {
        String[] args = {"1", "127.0.0.1:20201"};
        Main.main(args);
        assertTrue(true);
    }

}
