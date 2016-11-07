package pl.edu.agh.huffman.main;

import pl.edu.agh.huffman.algorithm.HuffmanCoding;
import pl.edu.agh.huffman.model.BinaryTree;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class Main {

    //TODO length of input text should be multiplied by 8 (since one character is represented by 8 bits)

    private static int codingLength = 1;

    private static Map<String, Integer> map = new HashMap<>();
    private static final File INPUT_FILE = new File("tobe.txt");

    public static void main(String[] args) {
        try {
            readFile(INPUT_FILE);
//            System.out.println(map.toString());
            HuffmanCoding hc = new HuffmanCoding();
            BinaryTree bt = hc.constructTree(map);
            System.out.println(bt.toString());
            compress(INPUT_FILE, new File("output.txt"), bt);
            decompress(new File("output.txt"), new File("decoded.txt"), bt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(File f) throws IOException {
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
    }

    public static void compress(File source, File destination, BinaryTree coding) throws IOException {
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

    public static void decompress(File source, File destination, BinaryTree coding) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(source));
        BufferedWriter bw = new BufferedWriter(new FileWriter(destination));
        int r;
        while ((r = br.read()) != -1) {
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
                }
            }
            bw.write(leaf.getCharacters());
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
