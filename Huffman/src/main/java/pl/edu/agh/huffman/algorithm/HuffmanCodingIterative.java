package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.*;

/**
 * Created by Jakub Fortunka on 07.11.2016.
 */
public class HuffmanCodingIterative extends HuffmanCoding {

    @Override
    protected BinaryTree innerConstructTree(PriorityQueue<Map.Entry<String, Integer>> list) {
        PriorityQueue<BinaryTree> nodes = new PriorityQueue<>((left, right) -> Integer.valueOf(left.getOccurrences()).compareTo(right.getOccurrences()));
        list.stream()
                .map(e -> new BinaryTree(null, null, e.getKey(), e.getValue(), ""))
                .forEach(nodes::offer);
        int n = nodes.size();
        while (n >= 2) {
            BinaryTree x = nodes.remove();
            BinaryTree y = nodes.remove();
            BinaryTree tmp = new BinaryTree(x,y,x.getCharacters()+y.getCharacters(), x.getOccurrences()+y.getOccurrences(), "");
            nodes.offer(tmp);
            n-=1;
        }
        BinaryTree ans = nodes.poll();
        ans.computePrefixes();
        return ans;
    }

}
