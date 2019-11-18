import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ForthOptimization implements Generation {

    private String fileName = "res/numbersForth.txt";;
    private String status = "Optimization Executor-Service :";

    public String generator() throws IOException, InterruptedException {

        long start = System.currentTimeMillis();

        File file = new File(fileName);
        if (file.exists()) file.delete();
        file.createNewFile();

        int countThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(countThreads);

        for (int regionCode = 0; regionCode < 100; regionCode++) {
            executorService.submit(new Work(regionCode, fileName));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);

        long duration = System.currentTimeMillis() - start;
        double length = (new File(fileName).length()) / 1024.0 / 1024.0;

        String info = String.format("%-35s %6d ms,\t %-15s\t--->\t%5.2f MB \n", status, duration, fileName, length);

        return info;
    }


    public String getName() {
        return this.getClass().getSimpleName();
    }
}

class Work implements Runnable {
    private int regionCode;
    private Path path;

    Work(int regionCode, String fileName) throws IOException {

        path = Paths.get(fileName);
        this.regionCode = regionCode;
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
        try {
            Files.write(path, builder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
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