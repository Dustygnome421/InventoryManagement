package com.example.springboot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "INVENTORY")
public class InventoryItem {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "COUNT")
    private int count;
}



// package com.example.springboot;

// import java.io.FileReader;
// import com.opencsv.CSVReader;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.Data;

// @Entity
// @Data
// @Table(name = "INVENTORY")
// public class InventoryItem {
    

//     @Id
//     @Column(name = "ID")
//     private Long id;

//     @Column(name = "COUNT")
//     private int count;

//     public static void main(String[] args) {
        
//         System.out.println("Hello, World!");
        
//         try {
//             CSVReader reader = new CSVReader(new FileReader("C:\\Users\\amana\\OneDrive\\Private\\Personal_Projects\\InventoryManagement\\gs-spring-boot\\complete\\src\\main\\java\\com\\example\\springboot\\inventory.csv"));
//             String[] lines;
//             while((lines = reader.readNext()) != null) {
//                 System.out.println(lines[1]);
//             }

//         } catch (Exception e) {
//             System.out.println("Error: " + e);
//         }

//     }
// }
