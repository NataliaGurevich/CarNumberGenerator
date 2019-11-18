import java.io.File;
import java.io.FileOutputStream;

public class NoOptimization implements Generation {
    private String fileName;
    private String status = "No optimization : ";

    public String generator() throws Exception {

        fileName = "res/numbers.txt";

        long start = System.currentTimeMillis();

        FileOutputStream writer = new FileOutputStream(fileName);

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        for (int number = 1; number < 1000; number++) {
            int regionCode = 199;
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        String carNumber = firstLetter + padNumber(number, 3) +
                                secondLetter + thirdLetter + padNumber(regionCode, 2);
                        writer.write(carNumber.getBytes());
                        writer.write('\n');
                    }
                }
            }
        }

        writer.flush();
        writer.close();

        long duration = System.currentTimeMillis() - start;
        double length = (new File(fileName).length()) / 1024.0 / 1024.0;

        String info = String.format("%-35s %6d ms,\t %-15s\t--->\t%5.2f MB \n", status, duration, fileName, length);

        return info;
    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
