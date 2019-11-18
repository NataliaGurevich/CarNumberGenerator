import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThirdOptimization implements Generation {

    private String fileName = "res/numbersThird.txt";
    private String status = "Optimization with Threads (Queue) :";
    private Path path;
    private static Queue<String> queue;
    private static volatile boolean cycle;

    public ThirdOptimization() throws IOException {
        queue = new ConcurrentLinkedQueue<>();
        File file = new File(fileName);
        if (file.exists()) file.delete();
        file.createNewFile();
    }

    public String generator() throws Exception {

        long start = System.currentTimeMillis();

        path = Paths.get(fileName);
        Thread producer1 = new Thread(new Producer(0, 24));
        Thread producer2 = new Thread(new Producer(25, 49));
        Thread producer3 = new Thread(new Producer(50, 74));
        Thread producer4 = new Thread(new Producer(75, 99));
        Thread consumer = new Thread(new Consumer());

        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        consumer.start();

        producer1.join();
        producer2.join();
        producer3.join();
        producer4.join();
        consumer.join();

        long duration = System.currentTimeMillis() - start;
        double length = (new File(fileName).length()) / 1024.0 / 1024.0;

        String info = String.format("%-35s %6d ms,\t %-15s\t--->\t%5.2f MB \n", status, duration, fileName, length);

        return info;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    static class Producer implements Runnable {
        int start;
        int end;
        StringBuffer buffer;

        Producer(int start, int end) {
            this.start = start;
            this.end = end;
            buffer = new StringBuffer();
            cycle = true;
        }

        @Override
        public void run() {
            //System.out.println("Producer started");
            char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

            for (int i = start; i <= end; i++) {
                for (int number = 1; number < 1000; number++) {
                    for (char firstLetter : letters) {
                        for (char secondLetter : letters) {
                            for (char thirdLetter : letters) {
                                buffer.append(firstLetter)
                                        .append(padNumber(number, 3))
                                        .append(secondLetter)
                                        .append(thirdLetter)
                                        .append(padNumber(i, 2))
                                        .append("\n");
                            }
                        }
                    }
                    queue.add(buffer.toString());
                    buffer = new StringBuffer();
                }
            }
            cycle = false;
        }

        private static String padNumber(int number, int numberLength) {
            String numberStr = Integer.toString(number);
            int padSize = numberLength - numberStr.length();
            for (int i = 0; i < padSize; i++) {
                numberStr = '0' + numberStr;
            }
            return numberStr;
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            String str;
            //System.out.println("Consumer started");
            while (cycle || queue.size() > 0) {
                if ((str = queue.poll()) != null) {
                    try {
                        Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
