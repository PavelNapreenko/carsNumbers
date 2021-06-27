import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Loader {
    private static final String filePath = "res/numbers.txt";

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        char[] let = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        multithreadingWriting(let);
    }

    private static void multithreadingWriting(char[] array)
        throws FileNotFoundException, InterruptedException {
        long start = System.currentTimeMillis();
        final int threadCount = Runtime.getRuntime().availableProcessors();

        if (array.length == 0) {
            System.out.println("Array is empty.");
        } else {
            int from = 0;
            for (int i = 0; i < threadCount; i++) {
                StringBuilder builder = new StringBuilder();
                int to = (from + (int) Math.ceil((array.length - from) /
                    (double) (threadCount - i)));
                PrintWriter writer = new PrintWriter(filePath
                    .replaceAll("\\.", i + "\\."));
                char[] letters = Arrays.copyOfRange(array, from, to);
                for (int regionCode = 1; regionCode < 100; regionCode++) {
                    for (int number = 1; number < 1000; number++) {
                        for (char firstLetter : letters) {
                            for (char secondLetter : letters) {
                                for (char thirdLetter : letters) {
                                    builder.append(firstLetter)
                                        .append(padNumber(number))
                                        .append(secondLetter)
                                        .append(thirdLetter)
                                        .append(padNumber(regionCode))
                                        .append("\n");
                                }
                            }
                        }
                    }
                }
                fileWriter(writer,builder);
                from = to;
                writer.flush();
            }
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }

    private static void fileWriter(PrintWriter writer, StringBuilder builder)
        throws InterruptedException {
        Thread thread = new Thread(() -> writer.write(builder.toString()));
        thread.start();
        thread.join();
    }

    private static String padNumber(int number) {
        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
        int padSize = 3 - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }
        return numberStr.toString();
    }
}
