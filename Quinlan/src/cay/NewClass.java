/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cay;



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
    dt.build(people);

    // Dự đoán sản phẩm sẽ được mua bởi một người cụ thể
    Person person = new Person(">40", "Female", "High", false);
    String prediction = dt.predict(person);
    System.out.println("Prediction: " + prediction);
}
}
