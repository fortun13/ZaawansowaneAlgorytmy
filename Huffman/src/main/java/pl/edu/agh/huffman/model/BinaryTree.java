package pl.edu.agh.huffman.model;

import java.util.Optional;

/**
 * Created by Kuba Fortunka on 07.11.2016.
 */
public class BinaryTree {

    private BinaryTree left;
    private BinaryTree right;
    private String characters;
    private int occurrences;
    private String prefix;

    public BinaryTree(BinaryTree left, BinaryTree right, String characters, int occurrences, String prefix) {
        this.left = left;
        this.right = right;
        this.characters = characters;
        this.occurrences = occurrences;
        this.prefix = prefix;
    }

    public Optional<BinaryTree> findByCharacters(String s) {
        if (characters.equals(s)) {
            return Optional.of(this);
        } else {
            if (left == null && right == null) {
                return Optional.empty();
            }
            Optional<BinaryTree> tmp = left.findByCharacters(s);
            if (!tmp.isPresent()) {
                return right.findByCharacters(s);
            } else {
                return tmp;
            }
        }
    }

    public void modify(BinaryTree left, BinaryTree right, String characters, int occurences, String prefix) {
        this.left = left;
        this.right = right;
        this.characters = characters;
        this.occurrences = occurences;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    private String getData() {
        return "characters: " + characters + "; occurrences: " + occurrences + "; prefix: " + prefix;
    }

    @Override
    public String toString() {
        return toString(new StringBuilder(), this).toString();
    }

    private static StringBuilder toString(StringBuilder string, BinaryTree node) {
        string.append('{').append("\n");
        if (node != null) {
            string.append(node.getData());
            toString(string.append(", "), node.getLeft());
            toString(string.append(", "), node.getRight());
        }
        return string.append('}');
    }

    public BinaryTree getLeft() {
        return left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public String getCharacters() {
        return characters;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void computePrefixes() {
        if (left != null) {
            left.setPrefix("0");
        }
        if (right != null) {
            right.setPrefix("1");
        }
    }

    private void setPrefix(String s) {
        this.prefix = s;
        if (left != null) {
            left.setPrefix(s + "0");
        }
        if (right != null) {
            right.setPrefix(s + "1");
        }
    }

}
