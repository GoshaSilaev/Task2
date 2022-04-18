import org.jetbrains.annotations.NotNull;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ciphxor {
    @Option(name = "-c", forbids = {"-d"})
    private String code;

    @Option(name = "-d", forbids = {"-o"})
    private String decode;

    @Option(name = "-0")
    private String outPutNameFile;

    @Argument
    private List<String> arguments = new ArrayList<String>(1);

    public void parser(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (arguments.isEmpty()) {
                System.out.println("ERROR of enter");
            }

        } catch (Exception CmdLineException) {
            System.err.println(CmdLineException.getMessage());
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        String file = arguments.get(0);

    }

    public void ciphxor(String code, String decode, String outPutNameFile, String file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader read = new BufferedReader(new FileReader(file));
        String str = read.readLine();
        while (str != null) {
            result.append(str).append("\n");
            str = read.readLine();
        }
        if (outPutNameFile != null) {


            if (code != null) encrypt(result.toString(), code);
            else if (decode != null) {
                int sizeOfResult = result.toString().length();
                for (int i = 0 ; i<sizeOfResult; i++)



                decrypt(result.toString(), decode);
            } else throw new IllegalArgumentException("No Bids");


        } else {
            throw new IllegalArgumentException("No Name To New Exit File");

        }
    }

    // шифрует
    public void encrypt(@NotNull String text, @NotNull String keyWord) {
        byte[] arr = text.getBytes();
        byte[] keyarr = keyWord.getBytes();
        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = (byte) (arr[i] ^ keyarr[i % keyarr.length]);
        }

    }

    // дешифрует
    public void decrypt(byte @NotNull [] text, @NotNull String keyWord) {
        byte[] result = new byte[text.length];
        byte[] keyarr = keyWord.getBytes();
        for (int i = 0; i < text.length; i++) {
            result[i] = (byte) (text[i] ^ keyarr[i % keyarr.length]);
        }
        //return new String(result);
    }
}

//