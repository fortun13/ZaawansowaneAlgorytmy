package pl.edu.agh.huffman.algorithm;

import pl.edu.agh.huffman.model.BinaryTree;

import java.util.*;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class HuffmanCoding {

    public BinaryTree constructTree(Map<String, Integer> map) {
        List<String> characters = new ArrayList<>(map.keySet());
        Collections.sort(characters, (s1, s2) -> {
            Integer popularity1 = map.get(s1);
            Integer popularity2 = map.get(s2);
            return popularity1.compareTo(popularity2);
        });
        BinaryTree ans;
        String leftCh = characters.get(0);
        String rightCh = characters.get(1);
        Integer leftOcc = map.get(leftCh);
        Integer rightOcc = map.get(rightCh);
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
