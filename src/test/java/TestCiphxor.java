import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCiphxor {

    private void equalsFiles(String inputName, String expectedOutputInFile) throws IOException {

        StringBuilder result1 = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(inputName))) {
            String str = read.readLine();
            while (str != null) {
                result1.append(str).append("\n");
                str = read.readLine();
            }
        }
        StringBuilder result2 = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(expectedOutputInFile))) {
            String str1 = read.readLine();
            while (str1 != null) {
                result2.append(str1).append("\n");
                str1 = read.readLine();
            }
        }

        assertEquals(result2.toString().trim(), result1.toString().trim());
    }

    @Test
    void test() throws IOException {
        Ciphxor.main("-c 2353239 -o file3 input/input.txt".trim().split(" "));
        equalsFiles("output/file3.txt", "tests/testfile3.txt");

        Ciphxor.main("-d 2353239 -o file4 output/file3.txt".trim().split(" "));
        equalsFiles("input/input.txt", "output/file4.txt");
        new File("output/file3.txt").delete(); // не хочет
        new File("output/file4.txt").delete();


        Ciphxor.main("-c 2353239 input/input.txt".trim().split(" "));
        equalsFiles("output/input.txt", "tests/testfile3.txt");

        Ciphxor.main("-d 2353239 -o file4 output/file3.txt".trim().split(" "));
        equalsFiles("input/input.txt", "output/file4.txt");
        new File("output/input.txt").delete();
        new File("output/file4.txt").delete();


    }
}
