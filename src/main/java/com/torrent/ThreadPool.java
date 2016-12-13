package com.torrent;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> workers;
    private final int numWorkers;

    public ThreadPool(int numWorkers, QueueDownloader queue, List<String> pieces, int lengthPiece, int length, String path) {
        this.numWorkers = numWorkers;
        workers = new ArrayList<>(numWorkers);
        for (int i = 0; i < numWorkers; i++) {
            Thread worker = new Thread(new Downloader(queue, pieces, lengthPiece, length, path));
            workers.add(worker);
            worker.start();
        }
    }
    
    void joinAll(){
        for (int i = 0; i < numWorkers; i++) {
            try {
                workers.get(i).join();
            } catch (InterruptedException ex) {
            }
        }
    }
}
