package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Main {
//Phương thức này nhận đầu vào là một danh sách các đối tượng Node và số lượng màu, và in ra ma trận đại diện cho đồ thị đã được tô màu.
  public void print(List<Node> nodes, Integer colors) {
    char[][] board = new char[nodes.size()][nodes.size()];
    System.out.println();
    System.out.println("Bảng tô màu:");
    nodes.forEach(node -> System.out.print("    "+node.name));
    System.out.println();
    for (int i = 1; i <= colors; i++) {
      System.out.print(i + "   ");
      for(int j = 0; j < nodes.size(); j++) {
        if(nodes.get(j).color == i) {
          board[i][j] = 'O';
          System.out.print(board[i][j] + "    ");
        } else {
          System.out.print("     ");
        }
      }
      System.out.println();
    }
  }


  public static void main(String[] args) {

    List<Integer> colors = new ArrayList<>();
    colors.add(1);
    colors.add(2);
    colors.add(3);
    colors.add(4);

    List<org.example.Node> adjacencyList = new ArrayList<>();
    org.example.Node nodeA = new org.example.Node();
    nodeA.name = 'A';
    Node nodeB = new Node();
    nodeB.name = 'B';
    Node nodeC = new Node();
    nodeC.name = 'C';
    Node nodeD = new Node();
    nodeD.name = 'D';
    Node nodeG = new Node();
    nodeG.name = 'G';
    Node nodeH = new Node();
    nodeH.name = 'H';

    adjacencyList.add(nodeA);
    adjacencyList.add(nodeB);
    adjacencyList.add(nodeC);
    adjacencyList.add(nodeD);
    adjacencyList.add(nodeG);
    adjacencyList.add(nodeH);

    nodeA.neighbors.add(nodeC);
    nodeA.neighbors.add(nodeD);
    nodeA.neighbors.add(nodeG);

    nodeB.neighbors.add(nodeH);


    nodeC.neighbors.add(nodeA);
    nodeC.neighbors.add(nodeD);
    nodeC.neighbors.add(nodeG);

    nodeD.neighbors.add(nodeC);
    nodeD.neighbors.add(nodeA);
    nodeD.neighbors.add(nodeG);

    nodeG.neighbors.add(nodeA);
    nodeG.neighbors.add(nodeC);
    nodeG.neighbors.add(nodeD);

    nodeH.neighbors.add(nodeB);


     Main main = new Main();
    main.baiToanToMau(adjacencyList);
    main.print(adjacencyList, colors.size());
  }


  public void baiToanToMau(List<Node> nodes) {

    PriorityQueue<Node> queue = new PriorityQueue<>(new DSATURComparator());
    queue.addAll(nodes);

    while (!queue.isEmpty()) {
        Node node = queue.poll();
        if (node.color != 0) {
            continue;
        }

        boolean[] usedColors = new boolean[nodes.size() + 1];
        for (Node neighbor : node.neighbors) {
            if (neighbor.color != 0) {
                usedColors[neighbor.color] = true;
            }
        }

        int color = 1;
        while (usedColors[color]) {
            color++;
        }

        node.color = color;

        for (Node neighbor : node.neighbors) {
            if (neighbor.color == 0) {
                queue.remove(neighbor);
                neighbor.degree++;
                neighbor.saturationDegree = computeSaturationDegree(neighbor, nodes);
                queue.offer(neighbor);
            }
        }
    }

    nodes.forEach(node -> System.out.println("Tô " + node.name + " với màu " + node.color));

}
// ta tính toán độ bão hoà (saturation degree) của một nút bằng cách kiểm 
//  tra màu đã được sử dụng trong các nút láng giềng và tăng giá trị độ bão hoà cho mỗi màu đã sử dụng.
private int computeSaturationDegree(Node node, List<Node> nodes) {
    boolean[] usedColors = new boolean[nodes.size() + 1];
    for (Node neighbor : node.neighbors) {
        if (neighbor.color != 0) {
            usedColors[neighbor.color] = true;
        }
    }

    int saturationDegree = 0;
    for (int i = 1; i <= nodes.size(); i++) {
        if (usedColors[i]) {
            saturationDegree++;
        }
    }
    return saturationDegree;
}
//ta cài đặt một Comparator cho lớp Node để sắp xếp các nút theo thứ tự giảm dần của độ bão hoà và độ bậc.
private static class DSATURComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if (o1.saturationDegree != o2.saturationDegree) {
            return Integer.compare(o2.saturationDegree, o1.saturationDegree);
        } else {
            return Integer.compare(o2.degree, o1.degree);
        }
    }
}

}