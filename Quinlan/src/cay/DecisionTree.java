/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cay;


import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class DecisionTree {
    private Node root;

    public DecisionTree(List<Person> people) {
        root = buildTree(people);
    }

    

    private Node buildTree(List<Person> people) {
        // Tạo một nút lá nếu tất cả các người trong danh sách đều mua sản phẩm
        if (allBought(people)) {
            return new Node("Bought");
        }
        // Tạo một nút lá nếu không có người nào mua sản phẩm
        if (noneBought(people)) {
            return new Node("Not bought");
        }
        // Chọn thuộc tính tốt nhất để phân tách danh sách
        Attribute bestAttr = getBestAttribute(people);
        Node node = new Node(bestAttr.getName());
        // Tạo cây con cho mỗi giá trị của thuộc tính được chọn
        for (String value : bestAttr.getValues()) {
            List<Person> subset = getSubset(people, bestAttr, value);
            Node child = buildTree(subset);
            node.addChild(child, value);
        }
        return node;
    }

    // Phương thức kiểm tra xem tất cả người trong danh sách có mua sản phẩm không
    private boolean allBought(List<Person> people) {
        for (Person person : people) {
            if (!person.bought) {
                return false;
            }
        }
        return true;
    }

    // Phương thức kiểm tra xem không ai trong danh sách mua sản phẩm
    private boolean noneBought(List<Person> people) {
        for (Person person : people) {
            if (person.bought) {
                return false;
            }
        }
        return true;
    }

    // Phương thức lấy thuộc tính tốt nhất để phân tách danh sách
    private Attribute getBestAttribute(List<Person> people) {
        Attribute bestAttr = null;
        double bestGain = 0.0;
        // Tính toán độ thông tin và độ lợi thông tin cho mỗi thuộc tính
        for (Attribute attr : Person.attributes) {
            double info = getInfo(people);
            double gain = getGain(people, attr, info);
            if (gain > bestGain) {
                bestGain = gain;
                bestAttr = attr;
            }
        }
        return bestAttr;
    }

    // Phương thức tính toán độ thông tin
    private double getInfo(List<Person> people) {
        int total = people.size();
        int bought = countBought(people);
        int notBought = total - bought;
        double pBought = (double) bought / total;
        double pNotBought = (double) notBought / total;
        // Sử dụng công thức entropy để tính toán độ thông tin
        return -pBought * Math.log(pBought) / Math.log(2) - pNotBought * Math.log(pNotBought) / Math.log(2);

}

// Phương thức tính toán độ lợi thông tin
private double getGain(List<Person> people, Attribute attr, double info) {
    double gain = info;
    for (String value : attr.getValues()) {
        List<Person> subset = getSubset(people, attr, value);
        double p = (double) subset.size() / people.size();
        gain -= p * getInfo(subset);
    }
    return gain;
}

// Phương thức đếm số lượng người mua sản phẩm
private int countBought(List<Person> people) {
    int count = 0;
    for (Person person : people) {
        if (person.bought) {
            count++;
        }
    }
    return count;
}

// Phương thức lấy danh sách con dựa trên giá trị của thuộc tính
private List<Person> getSubset(List<Person> people, Attribute attr, String value) {
    List<Person> subset = new ArrayList<Person>();
    for (Person person : people) {
        if (person.getAttribute(attr.getName()).equals(value)) {
            subset.add(person);
        }
    }
    return subset;
}

// Phương thức dự đoán sản phẩm sẽ được mua bởi một người cụ thể
public String predict(Person person) {
    return root.traverse(person);
}

}
class Node {
private String label;
private List<Node> children;
private List<String> values;
public Node(String label) {
    this.label = label;
    children = new ArrayList<Node>();
    values = new ArrayList<String>();
}

// Phương thức thêm một cây con với giá trị nhất định
public void addChild(Node child, String value) {
    children.add(child);
    values.add(value);
}

// Phương thức duyệt cây để tìm giá trị được dự đoán cho một người cụ thể
public String traverse(Person person) {
    int index = values.indexOf(person.getAttribute(label));
    if (index == -1) {
        return "Not sure";
    }
    Node child = children.get(index);
    if (child.children.size() == 0) {
        return child.label;
    }
    return child.traverse(person);
}
}
class Person {
public static Attribute[] attributes = {
new Attribute("Age", new String[]{"<30", "31-40", ">40"}),
new Attribute("Gender", new String[]{"Male", "Female"}),
new Attribute("Income", new String[]{"Low", "Medium", "High"})
};
private String age;
private String gender;
private String income;
public boolean bought;

public Person(String age, String gender, String income, boolean bought) {
    this.age = age;
    this.gender = gender;
    this.income = income;
    this.bought = bought;
}

// Phương thức lấy giá trị của một thuộc tính
public String getAttribute(String name) {
    if (name.equals("Age")) {
        return age;
    }
    if (name.equals("Gender")) {
        return gender;
    }
    if (name.equals("Income")) {
        return income;
    }
    return null;
}
}
class Attribute {
private String name;
private String[] values;
public Attribute(String name, String[] values) {
    this.name = name;
    this.values = values;
}

public String getName() {
    return name;
}

public String[] getValues() {
    return values;
}
}

class Main {
public static void main(String[] args) {
// Khởi tạo các đối tượng Person đại diện cho dữ liệu của cơ sở dữ liệu
Person[] people = {
new Person("<30", "Male", "Low", false),
new Person("<30", "Male", "High", false),
new Person("31-40", "Male", "Medium", true),
new Person(">40", "Male", "High", true),
new Person(">40", "Female", "High", true),
new Person(">40", "Female", "Low", false),
new Person("31-40", "Female", "Medium", true),
new Person("<30", "Male", "Medium", true),
new Person("<30", "Female", "High", true),
new Person(">40", "Male", "Medium", true),
new Person("<30", "Female", "Medium", true),
new Person("31-40", "Male", "Low", true),
new Person("31-40", "Female", "Low", true),
new Person(">40", "Male", "Low", false),
new Person("<30", "Female", "Low", true)
};
    // Khởi tạo bộ quyết định và xây dựng cây quyết định
    DecisionTree dt = new DecisionTree();
dt.buildDecisionTree(people);


    // Dự đoán sản phẩm sẽ được mua bởi một người cụ thể
    Person person = new Person(">40", "Female", "High", false);
    String prediction = dt.predict(person);
    System.out.println("Prediction: " + prediction);
}
}

