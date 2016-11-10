package pl.edu.agh.huffman.utils;

import javafx.util.Pair;
import pl.edu.agh.huffman.model.BinaryTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jakub Fortunka on 10.11.2016.
 */
public class FileUtils {

    public static Pair<Map<String, Integer>, Integer> readInputFile(File f, int codingLength) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        int inputFileCharsCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(f));
        int r;
        while ((r = br.read()) != -1) {
            inputFileCharsCount++;
            String c = String.valueOf((char)r);
            for (int i=1;i<codingLength;i++) {
                r = br.read();
                c += String.valueOf((char)r);
                inputFileCharsCount++;
            }
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        br.close();
        return new Pair<>(map, inputFileCharsCount);
    }

    public static void compress(File source, File destination, BinaryTree coding, int codingLength) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(destination));
        BufferedReader br = new BufferedReader(new FileReader(source));
        int r;
        while ((r = br.read()) != -1) {
            String c = String.valueOf((char)r);
            for (int i=1;i<codingLength;i++) {
                r = br.read();
                c += String.valueOf((char)r);
            }
            bw.write(coding.findByCharacters(c).getPrefix());
        }
        bw.flush();
        br.close();
        bw.close();
    }

    public static int decompress(File source, File destination, BinaryTree coding) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(source));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destination));
        int r;
        int compressedFileCharsCount = 0;
        while ((r = br.read()) != -1) {
            compressedFileCharsCount++;
//            String c = String.valueOf((char)r);
            BinaryTree leaf = coding;
            while (leaf.getLeft() != null || leaf.getRight() != null) {
                int tmp = Integer.valueOf(String.valueOf((char)r));
                if (tmp == 0) {
                    leaf = leaf.getLeft();
                } else {
                    leaf = leaf.getRight();
                }
                if (leaf.getLeft() != null || leaf.getRight() != null) {
                    r = br.read();
                    compressedFileCharsCount++;
                }
            }
            bw.write(leaf.getCharacters());
        }
        bw.flush();
        bw.close();
        br.close();
        return compressedFileCharsCount;
    }
}
