package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


@Controller
public class InventoryController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InventoryService service;


    @GetMapping("/inventory")
    public String viewInventory() {
        return "inventory";  // This should correspond to the inventory.html file in the templates directory
    }

    @GetMapping("/api/inventory")
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        List<InventoryItem> items = service.getAllInventoryItems();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/api/inventory/{itemId}")
    public ResponseEntity<InventoryItem> getInventoryItemById(@PathVariable Long itemId) {
        InventoryItem item = service.getInventoryItemById(itemId);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping("/api/inventory/{itemId}/buy")
    public ResponseEntity<String> buyItem(@PathVariable Long itemId) {
        boolean success = service.buyItem(itemId);
        if (success) {
            return ResponseEntity.ok("Item purchased successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to buy item. Item may be out of stock.");
        }
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";  // This should correspond to the upload.html file in the templates directory
    }

    @PostMapping("/process-csv")
    public RedirectView processCsv(@RequestParam("file") MultipartFile file) {
        service.saveInventoryFromCsv(file);
        return new RedirectView("/upload?success");
    }

    @GetMapping("/marketplace")
    public String marketplace(Model model) {
        return "marketplace";  // This should correspond to the marketplace.html file in the templates directory
    }

    // @GetMapping("/inventory")
    // public String viewInventory(Model model) {
    //     List<InventoryItem> items = service.getAllInventoryItems();
    //     if (items.isEmpty()) {
    //         model.addAttribute("message", "No Inventory Yet");
    //     } else {
    //         model.addAttribute("items", items);
    //     }
    //     return "inventory";  // This should correspond to the inventory.html file in the templates directory
    // }

        // @GetMapping("/inventory")
    // public String viewInventory(Model model) {
    //     String url = "http://localhost:8080/api/inventory";
    //     ResponseEntity<List<InventoryItem>> response = restTemplate.getForEntity(url, (Class<List<InventoryItem>>)(Class<?>)List.class);
    //     List<InventoryItem> items = response.getBody();
        
    //     if (items == null || items.isEmpty()) {
    //         model.addAttribute("message", "No Inventory Yet");
    //     } else {
    //         model.addAttribute("items", items);
    //     }
        
    //     return "inventory";  // This should correspond to the inventory.html file in the templates directory
    // }

    // @GetMapping("/api/inventory")
    // public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
    //     List<InventoryItem> items = service.getAllInventoryItems();
    //     if (items.isEmpty()) {
    //         return ResponseEntity.noContent().build();
    //     }
    //     return ResponseEntity.ok(items);
    // }

    // @GetMapping("/marketplace")
    // public String marketplace(Model model) {
    //     List<InventoryItem> items = service.getAllInventoryItems();
    //     model.addAttribute("items", items);
    //     return "marketplace";  // This should correspond to the marketplace.html file in the templates directory
    // }

    @PostMapping("/buy-item/{itemId}")
    @ResponseBody
    public String buyItem(@PathVariable Long itemId) {
        if (service.buyItem(itemId)) {
            return "Item purchased successfully";
        } else {
            return "Failed to buy item. Item may be out of stock.";
        }
    }
}
