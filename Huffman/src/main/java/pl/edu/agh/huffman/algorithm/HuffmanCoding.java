package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Jakub Fortunka on 07.11.2016.
 */
public abstract class HuffmanCoding {

    private long executionTime = 0;

    public BinaryTree constructTree(Map<String, Integer> map) {
        PriorityQueue<Map.Entry<String, Integer>> list = new PriorityQueue<>((left, right) -> left.getValue().compareTo(right.getValue()));
        map.entrySet().forEach(list::offer);
        long start = System.nanoTime();
        BinaryTree ans = innerConstructTree(list);
        executionTime = System.nanoTime() - start;
        return ans;
    }

    protected abstract BinaryTree innerConstructTree(PriorityQueue<Map.Entry<String, Integer>> list);

    public long getExecutionTime() {
        return executionTime;
    }
}
