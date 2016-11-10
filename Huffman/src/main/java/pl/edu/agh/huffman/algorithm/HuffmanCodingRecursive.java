package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.*;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class HuffmanCodingRecursive extends HuffmanCoding {

    protected BinaryTree innerConstructTree(PriorityQueue<Map.Entry<String, Integer>> list) {
        /*int leftOcc = Integer.MAX_VALUE;
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
        }*/
        BinaryTree ans;
        if (list.size() == 2) {
            Map.Entry<String, Integer> left = list.remove();
            Map.Entry<String, Integer> right = list.remove();
            String leftCh = left.getKey();
            String rightCh = right.getKey();
            int leftOcc = left.getValue();
            int rightOcc = right.getValue();
            ans = new BinaryTree(
                    new BinaryTree(null, null, leftCh, leftOcc, "0"),
                    new BinaryTree(null, null, rightCh, rightOcc, "1"),
                    leftCh+rightCh, leftOcc+rightOcc, "");
        } else {
            PriorityQueue<Map.Entry<String, Integer>> listCopy = new PriorityQueue<>(list);
            Map.Entry<String, Integer> left = listCopy.remove();
            Map.Entry<String, Integer> right = listCopy.remove();
            String leftCh = left.getKey();
            String rightCh = right.getKey();
            int leftOcc = left.getValue();
            int rightOcc = right.getValue();
            listCopy.offer(new AbstractMap.SimpleEntry<>(leftCh+rightCh, leftOcc+rightOcc));
            BinaryTree tmp = innerConstructTree(listCopy);
            BinaryTree tmp2 = tmp.findByCharacters(leftCh+rightCh);
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
