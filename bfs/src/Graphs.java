import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graphs {
    //bfs phương thức: sử dụng hàng đợi để duyệt qua các đỉnh 
//        và cạnh của đồ thị và tìm đường đi từ đỉnh khởi đầu đến đỉnh kết thúc
  public void bfs(int[][] vertices, int start, int end) {
    Queue<List<Integer>> queue = new LinkedList<>();
    List<Integer> path = new ArrayList<>();
    path.add(start);
    queue.offer(path);

    while (!queue.isEmpty()) {
      path = queue.poll();
      int last = path.get(path.size() - 1);
      if (last == end) {
        printPath(path);
      }
      List<Integer> neighbors = getNeighbors(vertices, last);
      for (int neighbor : neighbors) {
        if (isNotVisited(neighbor, path)) {
          List<Integer> newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.offer(newPath);
        }
      }
    }
  }
//phương thức: khởi tạo một mảng 2D các đỉnh và hai đỉnh khởi đầu và kết thúc, 
//        sau đó gọi phương thức bfs để tìm đường đi ngắn nhất giữa hai đỉnh.
  public static void main(String[] args) {
    int[][] vertices = {
      {0, 0}, {4, 0}, {0, 3}, {4, 1}, {4, 3},
      {1, 3}, {3, 0}, {2, 3}, {0, 1}, {1, 0}, {3, 3}, {4, 2}
    };
    int startVertex = 0;
    int endVertex = 11;

    Graphs graphs = new Graphs();
    graphs.bfs(vertices, startVertex, endVertex);
  }
//printPath phương thức: in ra một đường đi, được biểu diễn bằng danh sách các đỉnh.
  private static void printPath(List<Integer> path) {
    for (int v : path) {
      System.out.print("[" + v + "]");
    }
    System.out.println();
  }
//isNotVisited phương thức: kiểm tra xem một đỉnh đã được thăm qua hay chưa.
  private static boolean isNotVisited(int v, List<Integer> path) {
    return !path.contains(v);
  }
//getNeighbors phương thức: tìm tất cả các đỉnh láng giềng của một đỉnh trong đồ thị.
  private List<Integer> getNeighbors(int[][] vertices, int v) {
    List<Integer> neighbors = new ArrayList<>();
    for (int i = 0; i < vertices.length; i++) {
      if (i != v) {
        int[] vi = vertices[i];
        int[] vv = vertices[v];
        if ((vi[0] == vv[0] && Math.abs(vi[1] - vv[1]) <= 3) ||
            (vi[1] == vv[1] && Math.abs(vi[0] - vv[0]) <= 4)) {
          neighbors.add(i);
        }
      }
    }
    return neighbors;
  }

}