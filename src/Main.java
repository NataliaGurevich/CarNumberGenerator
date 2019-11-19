import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Generation> carNumbers = new ArrayList<>();
        //carNumbers.add(new NoOptimization());
        //carNumbers.add(new FirstOptimization());
        //carNumbers.add(new SecondOptimization());
        //carNumbers.add(new ThirdOptimization());
        carNumbers.add(new ForthOptimization());
        carNumbers.add(new MoreFilesOptimization());

        File file = new File("res/results.txt");

        FileWriter writer = new FileWriter(file);
        String str = "                                 Code analysis by time    \n";
        str += "-------------------------------------------------------------------------------------------- \n";
        writer.write(str);

        for (Generation numbers : carNumbers) {
            System.out.println("doing " + numbers.getName() + "...");
            writer.write(numbers.generator());
        }

        writer.flush();
        writer.close();

        System.out.println("\n The work is over. Look at results.txt");
    }
}