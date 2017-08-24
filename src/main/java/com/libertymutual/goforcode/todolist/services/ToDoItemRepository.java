// ToDoItemRepository.java
package com.libertymutual.goforcode.todolist.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {
	

	private ArrayList<ToDoItem> items;
    private int nextId = 1;

	public ToDoItemRepository() {
		items = new ArrayList<ToDoItem>();
	}
    /**
     * Get all the items from the file. 
     * @return A list of the items. If no items exist, returns an empty list.
     */
    public List<ToDoItem> getAll() {
    	
    	if (items.size() == 0) {
    		  	
    		try  
        	(FileReader reader = new FileReader("ToDo.csv")) {
        	
        		
        		for (CSVRecord record : CSVFormat.DEFAULT.parse(reader).getRecords()) {
        			
        			int id = Integer.parseInt(record.get(0));
        			String text = record.get(1);
        			boolean isComplete = Boolean.parseBoolean(record.get(2));
        			ToDoItem item = new ToDoItem();
        			item.setId(id);
        			item.setText(text);
        			item.setComplete(isComplete);
        			items.add(item);
        			nextId = nextId + 1;
        		}
        	
        		} catch (FileNotFoundException e) {
            		System.out.println("Could not read file");
        		} catch (IOException e) {
        			System.out.println("Could not read file1");
        		}
  		
    	}
		return items;
    			
    	
    }

    /**
     * Assigns a new id to the ToDoItem and saves it to the file.
     * @param item The to-do item to save to the file.
     */
    public void create(ToDoItem item) {
    	
   
    	try  
    	(FileWriter writer = new FileWriter("ToDo.csv",true)) {
		
			System.out.println("Everything is A-ok!");
			
			
			CSVPrinter printer = CSVFormat.DEFAULT.print(writer);
			String[] record = new String[3];
			record = new String [] {String.valueOf(nextId), item.getText(), String.valueOf(item.isComplete())};
				
				printer.printRecord(record);
				item.setId(nextId);
				items.add(item);
				nextId = nextId + 1;
				
				
							
	  	} catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
		} catch (IOException e) {
			System.out.println("Could not read file2");
		
		}
		
    }

    /**
     * Gets a specific ToDoItem by its id.
     * @param id The id of the ToDoItem.
     * @return The ToDoItem with the specified id or null if none is found.
     */
    public ToDoItem getById(int id) {
   			for (ToDoItem item :items) {
   				if (id ==item.getId()) {
   					return item;
   				}
   			}
        	
    	return null;
    }
    
    

   
	/**
     * Updates the given to-do item in the file.
     * @param item The item to update.
     */
    public void update(ToDoItem updatedItem) {
    	
    	for (ToDoItem item : items) {
    		if (updatedItem.getId() == item.getId()) {
    			
    			item.setComplete(item.isComplete());
    			
    	}
    	}
    	
    	try  
    	(FileWriter writer = new FileWriter("ToDo.csv");
    	
    	CSVPrinter printer = CSVFormat.DEFAULT.print(writer)){
    		
    		for (ToDoItem item : items) {
    			
    		
			String[] record = new String[3];
			record = new String [] {String.valueOf(item.getId()), item.getText(), String.valueOf(item.isComplete())};
				
				printer.printRecord(record);
				
				
    		}
							
	  	} catch (FileNotFoundException e) {
			System.out.println("Could not find file.");
		} catch (IOException e) {
			System.out.println("Could not read file3");
		
		}
    	}
    }


