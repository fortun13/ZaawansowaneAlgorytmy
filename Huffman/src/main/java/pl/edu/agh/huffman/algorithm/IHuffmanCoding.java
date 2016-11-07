package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.Map;

/**
 * Created by Jakub Fortunka on 07.11.2016.
 */
public interface IHuffmanCoding {

    BinaryTree constructTree(Map<String, Integer> map);
}
