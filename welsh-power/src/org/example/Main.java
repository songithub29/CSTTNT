package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Main {

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

// Giải bài toán tô màu đồ thị bằng cách duyệt qua các đỉnh 
//        và gán màu cho các đỉnh sao cho mỗi đỉnh có màu khác với tất cả các đỉnh kề của nó.
  public void baiToanToMau(List<Node> nodes) {

    List<Node> sorted = nodes.stream().sorted(new SortCustom()).collect(Collectors.toList());
    List<Node> coloured = new ArrayList<>();

    Node colouredVertex = null;
    colouredVertex = sorted.get(0);
    colouredVertex.color = 1;
    int n = sorted.size();
    for (int i = 1; i < n; i++) {
      for (int j = 1; j < n; j++) {
        int finalI = i;
        if (sorted.get(j).neighbors.stream().noneMatch(neighbor -> neighbor.color == finalI) && sorted.get(j).color == 0) {
          sorted.get(j).color = i;
          coloured.add(sorted.get(j));
        }
      }

    }
    sorted.forEach(node -> System.out.println("Tô " + node.name + " với màu " + node.color));

  }
}