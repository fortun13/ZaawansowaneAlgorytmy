package pl.edu.agh.huffman.utils;

import javafx.util.Pair;
import pl.edu.agh.huffman.model.BinaryTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Jakub Fortunka on 10.11.2016.
 */
public class FileUtils {

    public static Pair<Map<String, Integer>, Long> readInputFile(File f, int codingLength) throws IOException {
        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(f));
        int r;
        while ((r = br.read()) != -1) {
            String c = String.valueOf((char)r);
            for (int i=1;i<codingLength;i++) {
                r = br.read();
                c += String.valueOf((char)r);
            }
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        br.close();
        return new Pair<>(map, f.length());
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
            Optional<BinaryTree> tmp = coding.findByCharacters(c);
            if (tmp.isPresent()) {
                bw.write(tmp.get().getPrefix());
            }
        }
        bw.flush();
        br.close();
        bw.close();
    }

    public static long decompress(File source, File destination, BinaryTree coding) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(source));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destination));
        int r;
        while ((r = br.read()) != -1) {
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
                }
            }
            bw.write(leaf.getCharacters());
        }
        bw.flush();
        bw.close();
        br.close();
        return source.length();
    }
}
