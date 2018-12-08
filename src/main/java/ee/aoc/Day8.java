package ee.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day8 {
    private static final String fileName = "src/main/resources/8_day_input.txt";

    static class TreeNode {
        List<TreeNode> childNodes = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();
        
        int nodeMetadataSum() {
            return metadata.stream()
                    .mapToInt(i -> i)
                    .sum();
        }

        int subTreeMetadataSum() {
            int sum = nodeMetadataSum();
            if (childNodes.isEmpty()) {
                return sum;
            } else {
                for (TreeNode childNode : childNodes) {
                        sum += childNode.subTreeMetadataSum();
                }
                return sum;
            }
        }

        int value() {
            // If a node has no child nodes, its value is the sum of its metadata entries
            if (childNodes.isEmpty()) {
                return nodeMetadataSum();
            // The value of this node is the sum of the values of the child nodes referenced by the metadata entries
            } else {
                int value = 0;
                for (Integer index : metadata) {
                    if (index <= childNodes.size()) {
                        TreeNode childNode = childNodes.get(index - 1);
                        value += childNode.value();
                    }
                }
                return value;
            }
        }
    }

    static int buildTree(int index, TreeNode current, List<Integer> data) {
        int childrenCnt = data.get(index++);
        int metadataCnt = data.get(index++);
        for (int i = 0; i < childrenCnt; i++) {
            TreeNode childNode = new TreeNode();
            current.childNodes.add(childNode);
            index = buildTree(index, childNode, data);
        }
        for (int i = 0; i < metadataCnt; i++) {
            Integer metadata = data.get(index + i);
            current.metadata.add(metadata);
        }
        return index + metadataCnt;

    }


    public static void main(String[] args) {
        List<String> input = Arrays.asList(readLinesFromFile(fileName).get(0).split(" "));
        List<Integer> data = input.stream().map(Integer::parseInt).collect(Collectors.toList());

        TreeNode root = new TreeNode();
        buildTree(0, root, data);

        System.out.println("Answer 1: " + root.subTreeMetadataSum());
        System.out.println("Answer 2: " + root.value());
    }
}
