import java.io.File;
import java.io.PrintWriter;

public class FirstOptimization implements Generation {

    private String fileName;
    private String status = "Optimization from SkillBox :";

    public String generator() throws Exception {

        fileName = "res/numbersFirst.txt";
        long start = System.currentTimeMillis();

        PrintWriter writer = new PrintWriter(fileName);

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};

        for (int regionCode = 0; regionCode < 100; regionCode++) {

            StringBuilder builder = new StringBuilder();

            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            builder.append(firstLetter);
                            builder.append(padNumber(number, 3));
                            builder.append(secondLetter).append(thirdLetter);
                            builder.append(padNumber(regionCode, 2));
                            builder.append("\n");
//                            if(builder.length() > 1024){
//                                writer.write(builder.toString().getBytes());
//                                builder = new StringBuilder();
//                            }
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
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
