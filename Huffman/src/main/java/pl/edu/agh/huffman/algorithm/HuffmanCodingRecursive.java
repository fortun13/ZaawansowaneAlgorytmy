package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.*;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class HuffmanCodingRecursive implements IHuffmanCoding {

    public BinaryTree constructTree(Map<String, Integer> map) {
        int leftOcc = Integer.MAX_VALUE;
        int rightOcc = Integer.MAX_VALUE;
        String leftCh = "";
        String rightCh = "";
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int val = entry.getValue();
            if (val < leftOcc) {
                rightOcc = leftOcc;
                rightCh = leftCh;
                leftOcc = val;
                leftCh = entry.getKey();
            } else if (val < rightOcc) {
                rightOcc = val;
                rightCh = entry.getKey();
            }
        }
        BinaryTree ans;
        if (map.size() == 2) {
            ans = new BinaryTree(
                    new BinaryTree(null, null, leftCh, leftOcc, "0"),
                    new BinaryTree(null, null, rightCh, rightOcc, "1"),
                    leftCh+rightCh, leftOcc+rightOcc, "");
        } else {
            Map<String, Integer> mapCopy = new HashMap<>(map);
            mapCopy.remove(leftCh);
            mapCopy.remove(rightCh);
            mapCopy.put(leftCh+rightCh, leftOcc+rightOcc);
            BinaryTree tmp = constructTree(mapCopy);
            BinaryTree tmp2 = tmp.findByCharacters(leftCh+rightCh);
//            int mid = tmp2.getPrefix().length() / 2;
            tmp2.modify(
                    new BinaryTree(null, null, leftCh, leftOcc, tmp2.getPrefix()+"0"),
                    new BinaryTree(null, null, rightCh, rightOcc, tmp2.getPrefix()+"1"),
                    leftCh+rightCh, leftOcc+rightOcc, tmp2.getPrefix()
            );
            ans = tmp;
        }
        return ans;
    }
}
