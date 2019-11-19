import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MoreFilesOptimization implements Generation {

    private String fileName = "res/executor-service/";;
    private String status = "Optimization Executor-Service(more files):";
    private String currentFileName;

    public String generator() throws IOException, InterruptedException {

        long start = System.currentTimeMillis();

        File file = new File(fileName);
        if (file.exists()) file.delete();
        file.mkdir();

        int countThreads = Runtime.getRuntime().availableProcessors();

        Queue<PrintWriter> writers = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < countThreads; i++) {
            currentFileName = fileName + "numbers_" + i + ".txt";
            writers.add(new PrintWriter(currentFileName));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);

        for (int regionCode = 0; regionCode < 100; regionCode++) {
            PrintWriter pw = writers.poll();
            writers.add(pw);
            executorService.submit(new Work2(regionCode, pw));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        for(PrintWriter writer : writers){
            writer.close();
        }

        long duration = System.currentTimeMillis() - start;

        File[] files = file.listFiles();
        double size = 0.0;

        for( File f: files){
            if (f.isFile()){
                size += f.length();
            }
        }
        return String.format("%-35s %6d ms,\t %-15s\t--->\t%5.2f MB \n", status, duration, fileName, (size / 1024.0 / 1024.0));
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}

class Work2 implements Runnable {
    private int regionCode;
    private PrintWriter writer;

    Work2(int regionCode, PrintWriter writer) {
        this.regionCode = regionCode;
        this.writer = writer;
    }

    @Override
    public void run() {
        StringBuffer builder = new StringBuffer();
        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int number = 1; number < 1000; number++) {
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        builder.append(firstLetter)
                                .append(padNumber(number, 3))
                                .append(secondLetter)
                                .append(thirdLetter)
                                .append(padNumber(regionCode, 2))
                                .append("\n");
                    }
                }
            }
        }
        writer.write(builder.toString());
        writer.flush();
        //writer.close();
    }
    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        if (padSize > 0) {
            StringBuilder zero = new StringBuilder();
            zero.append("0".repeat(padSize));
            return zero.append(numberStr).toString();
        }
        return numberStr;
    }
}