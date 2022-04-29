import org.jetbrains.annotations.NotNull;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

public class Ciphxor {
    @Option(name = "-c", forbids = {"-d"})
    private String code;

    @Option(name = "-d", forbids = {"-c"})
    private String decode;

    @Option(name = "-o")
    private String outPutNameFile;

    @Argument
    private List<String> arguments = new ArrayList<String>(1);

    public void parser(String[] args) throws IOException {
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
        ciphxor(code, decode, outPutNameFile, file);
    }

    public void ciphxor(String code, String decode, String outPutNameFile, String file) throws IOException {


        if (code != null) {
            StringBuilder result = new StringBuilder();  // сюда потихоньку запишем прочитанный файл
            BufferedReader read = new BufferedReader(new FileReader(file)); //читаем файл
            String str = read.readLine();
            while (str != null) {
                result.append(str).append("\n");
                str = read.readLine();
            }

            String enter = encrypt(result.toString(), code);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/" + (outPutNameFile == null ? file.substring(file.lastIndexOf("/")).split("\\.")[0] : outPutNameFile) + ".txt"))) {
                writer.write(String.valueOf(enter));
            }

        } else if (decode != null) {
            StringBuilder result = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str1 = reader.readLine();

            while (str1 != null) {
                result.append(str1);
                str1 = reader.readLine();
            }

            String[] masForString = result.toString().split(" ");
            int[] masForInt = new int[masForString.length];
            for (int i = 0; i < masForString.length; i++) {
                masForInt[i] = Integer.parseInt(masForString[i]);
            }

            byte[] masForByte = new byte[masForInt.length];
            for (int i = 0; i < masForInt.length; i++) {
                masForByte[i] = Byte.parseByte(masForString[i]);
            }


            String enter = decrypt(masForByte, decode);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/" + (outPutNameFile == null ? file.substring(file.lastIndexOf("/")).split("\\.")[0] : outPutNameFile) + ".txt"))) {
                writer.write(String.valueOf(enter));
            }
        }


    }

    // шифрует
    public String encrypt(@NotNull String text, @NotNull String keyWord) {
        StringBuilder res = new StringBuilder();
        byte[] arr = text.getBytes();
        byte[] keyarr = keyWord.getBytes();
        for (int i = 0; i < arr.length; i++) {
            res.append((byte) arr[i] ^ (keyarr[i % keyarr.length])).append(" ");
        }
        return res.toString();
    }

    // дешифрует
    public String decrypt(byte @NotNull [] text, @NotNull String keyWord) {
        byte[] result = new byte[text.length];
        byte[] keyarr = keyWord.getBytes();
        for (int i = 0; i < text.length; i++) {
            result[i] = (byte) (text[i] ^ keyarr[i % keyarr.length]);
        }
        return new String(result);
    }

    public static void main(String[] args) throws IOException {
        new Ciphxor().parser(args);
    }

}