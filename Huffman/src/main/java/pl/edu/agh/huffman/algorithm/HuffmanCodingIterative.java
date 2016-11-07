package pl.edu.agh.huffman.algorithm;

import javafx.util.Pair;
import pl.edu.agh.huffman.model.BinaryTree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Jakub Fortunka on 07.11.2016.
 */
public class HuffmanCodingIterative implements IHuffmanCoding {

    public BinaryTree constructTree(Map<String, Integer> map) {
        List<BinaryTree> nodes = map.entrySet().stream()
                .map(e -> new BinaryTree(null, null, e.getKey(), e.getValue(), ""))
                .collect(Collectors.toCollection(LinkedList::new));
        int n = nodes.size();
        while (n >= 2) {
            Pair<Optional<BinaryTree>, Optional<BinaryTree>> lowest = getLowestTwo(nodes);
            Optional<BinaryTree> maybeX = lowest.getKey();
            Optional<BinaryTree> maybeY = lowest.getValue();
            if (maybeX.isPresent() && maybeY.isPresent()) {
                BinaryTree x = maybeX.get();
                BinaryTree y = maybeY.get();
                nodes.remove(x);
                nodes.remove(y);
                BinaryTree tmp = new BinaryTree(x,y,x.getCharacters()+y.getCharacters(), x.getOccurrences()+y.getOccurrences(), "");
                nodes.add(tmp);
            } else {
                System.out.println("YABAIDE :(");
                return null;
            }
            n-=1;
        }
        System.out.println("BREAKPOINT");
        BinaryTree ans = nodes.get(0);
        ans.computePrefixes();
        return nodes.get(0);
//        return null;
    }

    private Pair<Optional<BinaryTree>, Optional<BinaryTree>> getLowestTwo(List<BinaryTree> nodes) {
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        Optional<BinaryTree> btLeft = Optional.empty();
        Optional<BinaryTree> btRight = Optional.empty();
        for (int i=0;i<nodes.size();i++) {
            BinaryTree bt = nodes.get(i);
            int val = bt.getOccurrences();
            if (val < left) {
                right = left;
                btRight = btLeft;
                left = val;
                btLeft = Optional.of(bt);
            } else if (val < right) {
                right = val;
                btRight = Optional.of(bt);
            }
        }
        return new Pair<>(btLeft, btRight);
    }
}
