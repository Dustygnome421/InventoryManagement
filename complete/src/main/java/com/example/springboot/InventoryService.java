package com.example.springboot;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    // public void saveInventoryFromCsv(MultipartFile file) {
    //     try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
    //         String[] lines;
    //         List<InventoryItem> items = new ArrayList<>();

    //         // Skip the first line
    //         reader.readNext();

    //         while ((lines = reader.readNext()) != null) {
    //             // Skip empty lines
    //             if (lines.length == 0 || lines[0].isEmpty()) {
    //                 break;
    //             }

    //             InventoryItem item = new InventoryItem();
    //             item.setId(Long.parseLong(lines[0]));
    //             item.setCount(Integer.parseInt(lines[1]));
    //             items.add(item);
    //         }

    //         repository.saveAll(items);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    public void saveInventoryFromCsv(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] lines;
            List<InventoryItem> items = new ArrayList<>();

            // Skip the first line if it contains headers
            reader.readNext();

            while ((lines = reader.readNext()) != null) {
                // Skip empty lines
                if (lines.length == 0 || lines[0].isEmpty()) {
                    break;
                }

                Long itemId = Long.parseLong(lines[0]);
                int itemCount = Integer.parseInt(lines[1]);

                Optional<InventoryItem> existingItem = repository.findById(itemId);
                if (existingItem.isPresent()) {
                    InventoryItem item = existingItem.get();
                    item.setCount(item.getCount() + itemCount); // Increment count
                    items.add(item); // Add updated item to list
                } else {
                    InventoryItem newItem = new InventoryItem();
                    newItem.setId(itemId);
                    newItem.setCount(itemCount);
                    items.add(newItem); // Add new item to list
                }
            }

            repository.saveAll(items); // Save all items to repository
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<InventoryItem> getAllInventoryItems() {
        return repository.findAll();
    }
    
    public boolean buyItem(Long itemId) {
        Optional<InventoryItem> optionalItem = repository.findById(itemId);
        if (optionalItem.isPresent()) {
            InventoryItem item = optionalItem.get();
            if (item.getCount() > 0) {
                item.setCount(item.getCount() - 1);
                repository.save(item);
                return true;
            }
        }
        return false;
    }

    public InventoryItem getInventoryItemById(Long itemId) {
        Optional<InventoryItem> item = repository.findById(itemId);
        return item.orElse(null);
    }
}
