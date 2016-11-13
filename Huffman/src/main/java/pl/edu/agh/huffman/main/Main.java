package pl.edu.agh.huffman.main;

import javafx.util.Pair;
import pl.edu.agh.huffman.algorithm.HuffmanCodingRecursive;
import pl.edu.agh.huffman.utils.FileUtils;
import pl.edu.agh.huffman.algorithm.HuffmanCodingIterative;
import pl.edu.agh.huffman.algorithm.HuffmanCoding;
import pl.edu.agh.huffman.model.BinaryTree;

import java.io.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class Main {

    private static final int CODING_LENGTH = 3;

    private static final File INPUT_FILE = new File("seneca.txt");

    private static final File COMPRESSED_FILE = new File("compressed");
    private static final File OUTPUT_FILE = new File("decoded");

    private static final int CHAR_SIZE = 7;

    public static void main(String[] args) {
        try {

            HuffmanCoding huffmanIterative = new HuffmanCodingIterative();
            HuffmanCoding huffmanRecursive = new HuffmanCodingRecursive();
            for (int i=1;i<=CODING_LENGTH;i++) {
                Pair<Map<String, Integer>, Integer> occurrencesAndCount = FileUtils.readInputFile(INPUT_FILE, i);
                BinaryTree btIterative = huffmanIterative.constructTree(occurrencesAndCount.getKey());
                BinaryTree btRecursive = huffmanRecursive.constructTree(occurrencesAndCount.getKey());

                FileUtils.compress(INPUT_FILE, COMPRESSED_FILE, btRecursive, i);
                long compressedFileCharsCountRecursive = FileUtils.decompress(COMPRESSED_FILE, OUTPUT_FILE, btRecursive);

                FileUtils.compress(INPUT_FILE, COMPRESSED_FILE, btIterative, i);
                long compressedFileCharsCountIterative = FileUtils.decompress(COMPRESSED_FILE, OUTPUT_FILE, btIterative);

                long inputFileCharsCount = occurrencesAndCount.getValue()*CHAR_SIZE;

                System.out.println("Coding length: " + i);
                System.out.println("K (recursive) = " + (((double)(inputFileCharsCount - compressedFileCharsCountRecursive))/(double)inputFileCharsCount));
                System.out.println("K (iterative) = " + (((double)(inputFileCharsCount - compressedFileCharsCountIterative))/(double)inputFileCharsCount));
                System.out.println("Execution time iterative: " + TimeUnit.NANOSECONDS.toMillis(huffmanIterative.getExecutionTime()));
                System.out.println("Execution time recursive: " + TimeUnit.NANOSECONDS.toMillis(huffmanRecursive.getExecutionTime()));
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
