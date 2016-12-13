package com.torrent;

import java.util.ArrayList;
import java.util.List;
/***
 * 
 * Класс потоков
 */
public class ThreadPool {

    private final List<Thread> workers;
    private final int numWorkers;
/***
 * 
 * @param numWorkers количество потоков, которые могут работать
 * @param queue очередь загрузиков
 * @param pieces список кусков файла, который нужно скачать
 * @param lengthPiece длина куска файла
 * @param length общая длина
 * @param path  путь к файлу для сохранения
 */
    public ThreadPool(int numWorkers, QueueDownloader queue, List<String> pieces, int lengthPiece, int length, String path) {
        this.numWorkers = numWorkers;
        workers = new ArrayList<>(numWorkers);
        for (int i = 0; i < numWorkers; i++) {
            Thread worker = new Thread(new Downloader(queue, pieces, lengthPiece, length, path));
            workers.add(worker);
            worker.start();
        }
    }
    /***
     * ожидать завершения всех потоков
     */
    
    void joinAll(){
        for (int i = 0; i < numWorkers; i++) {
            try {
                workers.get(i).join();
            } catch (InterruptedException ex) {
            }
        }
    }
}
