import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class Graphs {
    //DFS
//    Trong phương thức dfs, một ngăn xếp được tạo ra để lưu trữ các đường đi, 
//      và đường đi ban đầu là nút bắt đầu (start). Sau đó, chương trình 
//     duyệt các nút con của nút cuối cùng của đường đi hiện tại và tạo ra một đường đi mới với mỗi nút con.
    public void dfs(Node start, Node end) {
       Stack <List<Node>> stack = new Stack<>();
        List<Node> path = new LinkedList<>();
            path.add(start);
        stack.push(path);

        // Loop through all adjacent nodes
        while (!stack.isEmpty()) {
            path = stack.pop();

            Node last = path.get(path.size() - 1);

            if (last == end) {
                printPath(path);
            }

            List<Node> lastNode = last.children;
            for (int i = 0; i < lastNode.size(); i++) {
                if (isNotVisited(lastNode.get(i), path)) {
                    List<Node> newpath = new ArrayList<>(path);
                    newpath.add(lastNode.get(i));
                    stack.push(newpath);
                }
            }

        }

    }

//Lớp Graphs chứa phương thức main và phương thức dfs để thực hiện tìm kiếm đường đi. 
    public static void main(String[] args) {
        Graphs graphs = new Graphs();
        Node nodeStart = new Node(null);
        nodeStart.val = new int[]{0, 0};

        Node node4and0 = new Node();
        node4and0.val = new int[]{4, 0};

        Node node0and3 = new Node();
        node0and3.val = new int[]{0, 3};

        Node node4and1 = new Node();
        node4and1.val = new int[]{4, 1};

        Node node4and3 = new Node();
        node4and3.val = new int[]{4, 3};

        Node node1and3 = new Node();
        node1and3.val = new int[]{1, 3};

        Node node3and0 = new Node();
        node3and0.val = new int[]{3, 0};

        Node node2and3 = new Node();
        node2and3.val = new int[]{2, 3};

        Node node0and1 = new Node();
        node0and1.val = new int[]{0, 1};

        Node node1and0 = new Node();
        node1and0.val = new int[]{1, 0};

        Node node3and3 = new Node();
        node3and3.val = new int[]{3, 3};

        Node node4and2 = new Node();
        node4and2.val = new int[]{4, 2};

        //add children
        nodeStart.children.add(node4and0);
        nodeStart.children.add(node0and3);

        node4and0.children.add(nodeStart);
        node4and0.children.add(node4and0);
        node4and0.children.add(node4and3);
        node4and0.children.add(node1and3);

        node0and3.children.add(nodeStart);
        node0and3.children.add(node0and3);
        node0and3.children.add(node4and3);
        node0and3.children.add(node3and0);

        node4and1.children.add(node4and0);
        node4and1.children.add(node4and1);
        node4and1.children.add(node0and1);
        node4and1.children.add(node4and3);
        node4and1.children.add(node2and3);

        node4and3.children.add(node4and0);
        node4and3.children.add(node0and3);
        node4and3.children.add(node4and3);

        node1and3.children.add(node4and3);
        node1and3.children.add(node1and0);
        node1and3.children.add(node0and3);
        node1and3.children.add(node4and0);
        node1and3.children.add(node1and3);

        node3and0.children.add(node3and0);
        node3and0.children.add(node4and0);
        node3and0.children.add(node3and3);
        node3and0.children.add(node0and3);

        node0and1.children.add(node0and1);
        node0and1.children.add(node1and0);
        node0and1.children.add(node4and1);

        node3and3.children.add(node4and2);
        node3and3.children.add(node3and3);
        node3and3.children.add(node0and3);
        node3and3.children.add(node3and0);
        node3and3.children.add(node4and3);
        graphs.dfs(nodeStart, node4and2);


    }

    private static void printPath(List<Node> path) {
        int size = path.size();
        for (Node v : path) {
            System.out.print(v + " ");
        }
        System.out.println();
    }


    private static boolean isNotVisited(Node x, List<Node> path) {
        int size = path.size();
        for (int i = 0; i < size; i++) {
            if (path.get(i) == x) {
                return false;
            }
        }

        return true;
    }
//Lớp Node định nghĩa một đối tượng đại diện cho một nút trên đồ thị, 
//        với các thuộc tính như giá trị của nút (val), danh sách các con (children) và nút cha (parent). 
    static class Node {

        int[] val = new int[2];
        List<Node> children;
        Node parent;


        public Node(Node parent) {
            children = new ArrayList<>();
            this.parent = parent;
        }

        public Node() {
            children = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + Arrays.toString(val) +
                    '}';
        }
    }
}
