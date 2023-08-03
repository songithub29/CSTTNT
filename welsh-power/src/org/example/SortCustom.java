package org.example;

import java.util.Comparator;

public class SortCustom implements Comparator<Node> {

  @Override
  public int compare(Node o1, Node o2) {
    return Integer.compare(o2.neighbors.size(), o1.neighbors.size());
  }
}
