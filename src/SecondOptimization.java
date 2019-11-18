import java.io.File;
import java.io.PrintWriter;

public class SecondOptimization implements Generation {

    private String fileName;
    private String status = "Optimization padNumber-method :";

    public String generator() throws Exception {

        fileName = "res/numbersSecond.txt";

        long start = System.currentTimeMillis();

        PrintWriter writer = new PrintWriter(fileName);

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int regionCode = 0; regionCode < 100; regionCode++) {

            StringBuilder builder = new StringBuilder();

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
        if (padSize > 0) {
            StringBuilder zero = new StringBuilder();
            zero.append("0".repeat(padSize));
            return zero.append(numberStr).toString();
        }
        return numberStr;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
