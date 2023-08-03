    import java.util.*;
    import java.util.stream.IntStream;

    public class quinland {
       public static String[][] arr = {
        {"mau", " thoi tiet", "la cay", " nhiet do ", "quyet dinh"},
        {"1", "  mua    ", "rung ", "  thap  ", "   dong   "},
        {"2", "  nang  ", "xanh ", "trung binh", "   xuan   "},
        {"3", "  nang   ", "vang ", "trung binh", "   thu   "},
        {"4", "  nang   ", "xanh ", "   cao   ", "    he    "},
        {"5", "  nang   ", "rung ", "  thap  ", "   dong   "},
        {"6", " tuyet   ", "vang ", "  thap  ", "   dong   "},
        {"7", "   mua   ", "rung ", "trung binh", "   thu   "},
        {"8", "  mua    ", "xanh ", "   cao   ", "    he    "},
        {"9", " tuyet   ", "xanh ", "  thap  ", "   dong   "},
        {"10", "  tuyet  ", "rung ", "  thap  ", "   dong   "},
        {"11", "  mua    ", "vang ", "trung binh", "   thu   "},
        {"12", "  mua    ", "xanh ", "trung binh", "   xuan   "},
        {"x", "  mua    ", "vang", "   cao   ", "    ?    "},
        {"y", " tuyet   ", "rung ", "trung binh", "    ?   "},
        {"z", " tuyet   ", "vang ", "trung binh", "    ?   "}
    };

    public static void printTable(String[][] arr) {
        int[] colWidths = new int[arr[0].length];

        // Tính độ rộng của mỗi cột dựa trên giá trị lớn nhất trong cột đó
        for (String[] row : arr) {
            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                int width = row[colIndex].trim().length();
                if (width > colWidths[colIndex]) {
                    colWidths[colIndex] = width;
                }
            }
        }

        // In bảng với khung
        for (String[] row : arr) {
            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                String cell = row[colIndex].trim();
                int width = colWidths[colIndex];
                String format = "| %-" + width + "s ";

                System.out.format(format, cell);
            }
            System.out.println("|");

            if (row == arr[0]) {
                String line = "+" + "-".repeat(row.length * (colWidths[0] + 3) - 1) + "+";
                System.out.println(line);
            }
        }
    }

    public static void bang(String[] args) {
        printTable(arr);
    }


        public static void main(String[] args) {


            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    System.out.print(arr[i][j] + "\t");
                }
                System.out.println();
            }

            //thời tiết
            String[] weather = {"nang", "mua", "tuyet"};
            double[][] weatherVector = new double[3][4];
            System.out.println("+++++weather properties+++++");
            for (int i = 0; i < weather.length; i++) {
                calculateVectorWeather(arr, weather[i], weatherVector[i]);
                System.out.println("vector cho thoi tiet\"" + weather[i] + "\": " + Arrays.toString(weatherVector[i]));
            }

            //lá cây
            String[] trees = {"vang", "xanh", "rung"};
            double[][] treeVectors = new double[3][4];
            System.out.println("+++++tree properties+++++");
            for (int i = 0; i < trees.length; i++) {
                calculateTreeVector(arr, trees[i], treeVectors[i]);
                System.out.println("vector cho cay\"" + trees[i] + "\": " + Arrays.toString(treeVectors[i]));
            }

            //nhiệt độ
            System.out.println("+++++temperature properties+++++");
            double[] vectorTrungBinh = calculateTemperatureVector(arr, "trung binh");
            double[] vectorThap = calculateTemperatureVector(arr, "thap");
            double[] vectorCao = calculateTemperatureVector(arr, "cao");

            System.out.println("vector cho nhiet do \"trung binh\": " + Arrays.toString(vectorTrungBinh));
            System.out.println("vector cho nhiet do \"thap\": " + Arrays.toString(vectorThap));
            System.out.println("vector cho nhiet do \"cao\": " + Arrays.toString(vectorCao));

            System.out.println("\t");
           String[][] matrix = {
        {"", "", "nhiet do", "", ""},
        {"thap", "", "trung binh", "", "cao"},
        {"dong", "", "     ?", "                he"}
    };

    System.out.println("+++++Chon thuoc tinh nhiet do lam thuoc tinh phan loai+++++");
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            String element = matrix[i][j];
            if (element == null) {
                element = "";
            }
            System.out.print(String.format("| %-15s ", element));
        }
        System.out.println("|");
    }


     System.out.println("\t");
     System.out.println("CSDL ung voi nhit do bang trung binh");
     System.out.println("\t");
            String[][] filteredData = filterData(arr);
            printData(filteredData);
            System.out.println("\t");

            //thuoctinh thoitiet
            double[] vectorNang = calculateVectorAttribute(arr, "nang");
            System.out.println("vector cho thoi tiet \"nang\":(" + vectorNang[0] + "," + vectorNang[1] + "," + vectorNang[2] + "," + vectorNang[3] + ")");

            double[] vectorMua = calculateVectorAttribute(arr, "mua");
            System.out.println("vector cho thoi tiet \"mua\":(" + vectorMua[0] + "," + vectorMua[1] + "," + vectorMua[2] + "," + vectorMua[3] + ")");
            System.out.println("\t");
            //thuoctinh lacay
            double[] vectorVang = calculateVectorAttribute1(filteredData, "vang");
            double[] vectorXanh = calculateVectorAttribute1(filteredData, "xanh");
            double[] vectorRung = calculateVectorAttribute1(filteredData, "rung");

            System.out.println("vector cho cay \"vang\":(" + vectorVang[2] + "," + vectorVang[1] + "," + vectorVang[0] + "," + vectorVang[3] + ")");
            System.out.println("vector cho cay \"xanh\":(" + vectorXanh[2] + "," + vectorXanh[1] + "," + vectorXanh[0] + "," + vectorXanh[3] + ")");
            System.out.println("vector cho cay \"rung\":(" + vectorRung[2] + "," + vectorRung[1] + "," + vectorRung[0] + "," + vectorRung[3] + ")");
    //cay quyet dinh
            String[][] decisionTree = {
                    { null, null,"nhiet do", null, null},
                    {"thap", null, "trung binh",null, "cao"},
                    {"dong",null, null, null, "     he"},
                    { null, null,"la cay", null, null},
                    {"vang",null, "xanh", null,  "rung"},
                    {"thu", null,"     xuan", null,  "thu"}
            };
            System.out.println("ta co cay quyet dinh ");
            printDecisionTree(decisionTree);
             System.out.println("Luật 1: Nếu \"nhiet do\" là \"thap\" , thì kết quả là \"mua dong\".");
            System.out.println("Luật 2: Nếu \"nhiet do\" là \"cao\" , thì kết quả là \"mua he\".");
            System.out.println("Nếu \"nhiet do\" là \"trung binh\" và là \"xanh\" , thì kết quả là \"mua xuan\".");
            System.out.println("Luật 4: Nếu \"nhiet do\" là \"trung binh\" và là \"vang\", thì kết quả là \"mua thu\".");
            
        }


        //thời tiết
        public static double[] calculateVectorWeather(String[][] arr, String weather, double[] vector) {
            int[] count = new int[4];
            int total = 0;

            // Đếm số lần xuất hiện của weather trong mỗi mùa
            for (int i = 1; i < arr.length; i++) {
                if (arr[i][1].trim().equals(weather)) {
                    switch (arr[i][4].trim()) {
                        case "xuan":
                            count[0]++;
                            break;
                        case "he":
                            count[1]++;
                            break;
                        case "thu":
                            count[2]++;
                            break;
                        case "dong":
                            count[3]++;
                            break;
                    }
                    total++;
                }
            }


            // Tính tỷ lệ xuất hiện của weather trong mỗi mùa
            for (int i = 0; i < 4; i++) {
                if (weather.equals("mua")) {
                    vector[i] = (total != 0) ? (double) count[i] / (total - count[3]) : 0.0;
                } else if (weather.equals("tuyet")) {
                    vector[i] = (total != 0) ? (double) count[i] / total : 0.0;
                } else {
                    vector[i] = (total != 0) ? (double) count[i] / total : 0.0;
                }
            }
            return vector;
        }


        //tree
        public static void calculateTreeVector(String[][] arr, String tree, double[] vector) {
            int[] count = new int[4];
            int total = 0;

            // Count the occurrences of the tree in each season
            for (int i = 1; i < arr.length; i++) {
                if (arr[i][2].trim().equals(tree)) {
                    switch (arr[i][4].trim()) { // Corrected index to 4 for season column
                        case "xuan":
                            count[0]++;
                            break;
                        case "he":
                            count[1]++;
                            break;
                        case "thu":
                            count[2]++;
                            break;
                        case "dong":
                            count[3]++;
                            break;
                    }
                    total++;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (tree.equals("vang")) {
                    vector[i] = (total != 0 && count[2] != 0) ? (double) count[i] / count[2] : 0.0;
                } else if (tree.equals("xanh")) {
                    vector[i] = (total != 0 && count[3] != 0) ? (double) count[i] / count[3] : 0.0;
                } else if (tree.equals("rung")) {
                    vector[i] = (total != 0 && count[2] != 0) ? (double) count[i] / count[2] : 0.0;
                }
            }

            // Normalize the vector
            double sum = Arrays.stream(vector).sum();
            if (sum != 0) {
                for (int i = 0; i < 4; i++) {
                    vector[i] /= sum;
                }
            }
        }

        //nhiệt độ
        public static double[] calculateTemperatureVector(String[][] arr, String temperature) {
            int[] count = new int[4];
            int total = 0;

            // Count the number of times a decision appears in each season for the given temperature
            for (int i = 1; i < arr.length; i++) {
                if (arr[i][3].trim().equals(temperature)) {
                    switch (arr[i][4].trim()) {
                        case "xuan":
                            count[0]++;
                            break;
                        case "he":
                            count[1]++;
                            break;
                        case "thu":
                            count[2]++;
                            break;
                        case "dong":
                            count[3]++;
                            break;
                    }
                    total++;
                }
            }
            // Calculate the occurrence rate of a decision in each season for the given temperature
            double[] vector = new double[4];
            for (int i = 0; i < 4; i++) {
                if (temperature.equals("trung binh")) {
                    vector[i] = (total - count[3] != 0) ? (double) count[i] / (count[0] + count[1] + count[2]) : 0.0;
                } else if (temperature.equals("thap")) {
                    vector[i] = (total != 0) ? (double) count[i] / total : 0.0;
                } else if (temperature.equals("cao")) {
                    vector[i] = (total != 0 && count[1] != 0) ? (double) count[i] / count[1] : (i == 3 ? 1.0 : 0.0);
                }
            }
            return vector;
        }

        //csdl ứng với nhiệt độ trung bình
        public static String[][] filterData(String[][] arr) {
            List<String[]> filteredData = new ArrayList<>();
            filteredData.add(new String[]{"mau", "thoi tiet", "la cay", "quyet dinh"});
            for (int i = 1; i < arr.length; i++) {
                if (arr[i][3].trim().equals("trung binh")) {
                    String[] row = {arr[i][0].trim(), arr[i][1].trim(), arr[i][2].trim(), arr[i][4].trim()};
                    if (Arrays.asList("xuan", "he", "thu", "dong").contains(row[3])) {
                        filteredData.add(row);
                    }
                }
            }
            return filteredData.toArray(new String[0][0]);
        }

        public static void printData(String[][] arr) {
            System.out.println(String.format("%-10s %-15s %-10s %-15s", arr[0][0], arr[0][1], arr[0][2], arr[0][3]));
            for (int i = 1; i < arr.length; i++) {
                System.out.println(String.format("%-10s %-15s %-10s %-15s", arr[i][0], arr[i][1], arr[i][2], arr[i][3]));
            }
        }

        //thuoctinh thoitiet
        public static double[] calculateVectorAttribute(String[][] data, String attribute) {
            double[] vector = new double[4];
            for (int i = 1; i < data.length; i++) {
                if (data[i][1].trim().equals(attribute)) {
                    switch (data[i][3].trim()) {
                        case "trung binh":
                            vector[0]++;
                            break;
                        case "thap":
                            vector[1]++;
                            break;
                        case "cao":
                            vector[2]++;
                            break;
                        case "rat cao":
                            vector[3]++;
                            break;
                        default:
                            break;
                    }
                }
            }
            double sum = IntStream.range(0, vector.length).mapToDouble(i -> vector[i]).sum();
            for (int i = 0; i < vector.length; i++) {
                vector[i] = vector[i] / sum;
            }

            // Swap elements to match the desired output
            double temp = vector[0];
            vector[0] = vector[1];
            vector[1] = temp;

            return vector;
        }

        //thuoctinh lacay
        public static double[] calculateVectorAttribute1(String[][] data, String attribute) {
            double[] vector = new double[4];
            for (int i = 1; i < data.length; i++) {
                if (data[i][2].trim().equals(attribute)) {
                    switch (data[i][3].trim()) {
                        case "xuan":
                            vector[0]++;
                            break;
                        case "he":
                            vector[1]++;
                            break;
                        case "thu":
                            vector[2]++;
                            break;
                        case "dong":
                            vector[3]++;
                            break;
                        default:
                            break;
                    }
                }
            }
            double sum = IntStream.range(0, vector.length).mapToDouble(i -> vector[i]).sum();
            if (sum != 0) {
                for (int i = 0; i < vector.length; i++) {
                    vector[i] = vector[i] / sum;
                }
            }

            // Swap elements to match the desired output
            double temp = vector[0];
            vector[0] = vector[2];
            vector[2] = temp;

            return vector;
        }
        //cay quyet dinh
        public static void printDecisionTree(String[][] decisionTree) {
        int numRows = decisionTree.length;
        int numCols = decisionTree[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String element = decisionTree[i][j];
                if (element == null) {
                    element = "";
                }

                int columnWidth = 15; // Độ rộng cột cố định
                int elementWidth = element.length();
                int padding = columnWidth - elementWidth;

                // Căn chỉnh và in phần tử
                if (j == 0) {
                    System.out.print("| ");
                }
                System.out.print(element);
                for (int k = 0; k < padding; k++) {
                    System.out.print(" ");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
    }

