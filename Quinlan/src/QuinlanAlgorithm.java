import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Phương thức khởi tạo Sample nhận hai tham số: attributes và decision. 
//Nó được sử dụng để tạo một đối tượng Sample mới với các giá trị thuộc tính và quyết định cụ thể.
class Sample {
    List<String> attributes;
    String decision;

    public Sample(List<String> attributes, String decision) {
        this.attributes = attributes;
        this.decision = decision;
    }
}

class TreeNode {
    String attribute;
    String decision;
    List<TreeNode> children;

    public TreeNode(String attribute, String decision, List<TreeNode> children) {
        this.attribute = attribute;
        this.decision = decision;
        this.children = children;
    }
}

public class QuinlanAlgorithm {
//    buildDecisionTree: Phương thức này xây dựng cây quyết định dựa trên các mẫu và thuộc tính được cung cấp. Nó sử dụng đệ quy để tạo các nút con cho cây.
    public static TreeNode buildDecisionTree(List<Sample> samples, List<String> attributes) {
        if (allSamplesSameLabel(samples)) {
            return new TreeNode(null, samples.get(0).decision, null);
        }

        if (attributes.isEmpty()) {
            return majorityVote(samples);
        }

        String bestAttribute = chooseBestAttribute(samples, attributes);
        List<String> distinctAttributeValues = getDistinctAttributeValues(samples, bestAttribute);

        List<TreeNode> children = new ArrayList<>();
        for (String value : distinctAttributeValues) {
            List<Sample> subset = getSubset(samples, bestAttribute, value);
            List<String> remainingAttributes = new ArrayList<>(attributes);
            remainingAttributes.remove(bestAttribute);
            TreeNode childNode = buildDecisionTree(subset, remainingAttributes);
            children.add(childNode);
        }

        return new TreeNode(bestAttribute, null, children);
    }
    
//allSamplesSameLabel: Phương thức này kiểm tra xem tất cả các mẫu có cùng một nhãn quyết định hay không.
    public static boolean allSamplesSameLabel(List<Sample> samples) {
        String firstLabel = samples.get(0).decision;
        for (Sample sample : samples) {
            if (!sample.decision.equals(firstLabel)) {
                return false;
            }
        }
        return true;
    }
    
//majorityVote: Phương thức này tính toán quyết định phổ biến nhất trong một tập mẫu.
    public static TreeNode majorityVote(List<Sample> samples) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Sample sample : samples) {
            String label = sample.decision;
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        String majorityLabel = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                majorityLabel = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return new TreeNode(null, majorityLabel, null);
    }

//    chooseBestAttribute: Phương thức này chọn thuộc tính tốt nhất để phân chia dựa trên chỉ số entropy.
    public static String chooseBestAttribute(List<Sample> samples, List<String> attributes) {
        double minEntropy = Double.MAX_VALUE;
        String bestAttribute = null;

        for (String attribute : attributes) {
            double entropy = calculateEntropy(samples, attribute);
            if (entropy < minEntropy) {
                minEntropy = entropy;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }

//    calculateEntropy: Phương thức này tính toán chỉ số entropy để đo lường độ không chắc chắn trong tập dữ liệu.
    public static double calculateEntropy(List<Sample> samples, String attribute) {
        List<String> distinctAttributeValues = getDistinctAttributeValues(samples, attribute);

        double entropy = 0.0;
        for (String value : distinctAttributeValues) {
            List<Sample> subset = getSubset(samples, attribute, value);
            double probability = (double) subset.size() / samples.size();
            entropy -= probability * calculateLog2(probability);
        }

        return entropy;
    }

//    getDistinctAttributeValues: Phương thức này trả về các giá trị riêng biệt của một thuộc tính trong tập dữ liệu.
    public static List<String> getDistinctAttributeValues(List<Sample> samples, String attribute) {
        List<String> distinctValues = new ArrayList<>();
        for (Sample sample : samples) {
            String value = sample.attributes.get(getAttributeIndex(attribute));
            if (!distinctValues.contains(value)) {
                distinctValues.add(value);
            }
        }
        return distinctValues;
    }

//    getSubset: Phương thức này trả về một tập con của các mẫu dựa trên một giá trị thuộc tính cụ thể.
    public static List<Sample> getSubset(List<Sample> samples, String attribute, String value) {
        List<Sample> subset = new ArrayList<>();
        for (Sample sample : samples) {
            String attributeValue = sample.attributes.get(getAttributeIndex(attribute));
            if (attributeValue.equals(value)) {
                subset.add(sample);
            }
        }
        return subset;
    }

    public static int getAttributeIndex(String attribute) {
        List<String> attributes = List.of("Outlook", "Temperature", "Humidity", "Wind");
        return attributes.indexOf(attribute);
    }

    public static double calculateLog2(double value) {
        return Math.log(value) / Math.log(2);
    }

//    printDecisionTree: Phương thức này in ra cây quyết định.
    public static void printDecisionTree(TreeNode node, int indentLevel) {
    if (node == null) {
        return;
    }

    if (indentLevel > 0) {
        System.out.print("|");
        for (int i = 0; i < indentLevel - 1; i++) {
            System.out.print("\t");
        }
        System.out.print("|---");
    }

    if (node.decision != null) {
        System.out.println("Decision: " + node.decision);
    } else {
        System.out.println("Attribute: " + node.attribute);
        if (node.children != null) {
            for (TreeNode child : node.children) {
                printDecisionTree(child, indentLevel + 1);
            }
        }
    }
}


//    printRules: Phương thức này in ra các quy tắc từ cây quyết định.
    public static void printRules(TreeNode node, List<String> rule, List<String> attributes) {
        if (node == null) {
            return;
        }

        if (node.decision != null) {
            System.out.print("IF ");
            for (int i = 0; i < rule.size(); i++) {
                System.out.print(attributes.get(i) + " = " + rule.get(i));
                if (i < rule.size() - 1) {
                    System.out.print(" AND ");
                }
            }
            System.out.println(" THEN Decision = " + node.decision);
        } else {
            rule.add(node.attribute);
            if (node.children != null) {
                for (TreeNode child : node.children) {
                    printRules(child, rule, attributes);
                }
            }
            rule.remove(rule.size() - 1);
        }
    }

//    classifySample: Phương thức này phân loại một mẫu mới bằng cách sử dụng cây quyết định.
    public static String classifySample(TreeNode node, List<String> sample, List<String> attributes) {
        if (node.decision != null) {
            return node.decision;
        }

        String attributeValue = sample.get(getAttributeIndex(node.attribute));
        for (TreeNode child : node.children) {
            if (attributeValue.equals(child.attribute)) {
                return classifySample(child, sample, attributes);
            }
        }

        return null;
    }

    public static void main(String[] args) {
        List<Sample> samples = new ArrayList<>();
        samples.add(new Sample(List.of("Sunny", "Hot", "High", "Weak"), "No"));
        samples.add(new Sample(List.of("Sunny", "Hot", "High", "Strong"), "No"));
        samples.add(new Sample(List.of("Overcast", "Hot", "High", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Rainy", "Mild", "High", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Rainy", "Cool", "Normal", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Rainy", "Cool", "Normal", "Strong"), "No"));
        samples.add(new Sample(List.of("Overcast", "Cool", "Normal", "Strong"), "Yes"));
        samples.add(new Sample(List.of("Sunny", "Mild", "High", "Weak"), "No"));
        samples.add(new Sample(List.of("Sunny", "Cool", "Normal", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Rainy", "Mild", "Normal", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Sunny", "Mild", "Normal", "Strong"), "Yes"));
        samples.add(new Sample(List.of("Overcast", "Mild", "High", "Strong"), "Yes"));
        samples.add(new Sample(List.of("Overcast", "Hot", "Normal", "Weak"), "Yes"));
        samples.add(new Sample(List.of("Rainy", "Mild", "High", "Strong"), "No"));

        List<String> attributes = List.of("Outlook", "Temperature", "Humidity", "Wind");

        TreeNode decisionTree = buildDecisionTree(samples, attributes);
        System.out.println("Decision Tree:");
        printDecisionTree(decisionTree, 0);

        System.out.println("\nRules:");
        List<String> rule = new ArrayList<>();
        printRules(decisionTree, rule, attributes);

        System.out.println("\nClassification for new samples:");
        List<Sample> newSamples = new ArrayList<>();
        newSamples.add(new Sample(List.of("Sunny", "Hot", "High", "Weak"), " NO"));
        newSamples.add(new Sample(List.of("Overcast", "Mild", "High", "Weak"), ""));
        newSamples.add(new Sample(List.of("Rainy", "Cool", "Normal", "Weak"), ""));
        for (Sample newSample : newSamples) {
            String decision = classifySample(decisionTree, newSample.attributes, attributes);
            System.out.println("Sample: " + newSample.attributes + " => Decision: " + decision);
        }
    }
}
