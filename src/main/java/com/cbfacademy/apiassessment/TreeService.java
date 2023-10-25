package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;



@Service
public class TreeService {
    private final List<Tree> trees;
    private static final String JSON_FILE_PATH = "src/main/resources/trees.json";

  
    public TreeService() {
        trees = Collections.synchronizedList(loadTreesFromJsonFile());
    }



    private List<Tree> loadDefaultTreesFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(JSON_FILE_PATH);

        try {
            if (file.exists()) {
                return new ArrayList<>( Arrays.asList(objectMapper.readValue(file, Tree[].class)));
            } else {
                file.createNewFile(); // Create an empty file if it doesn't exist
            }
        } catch (IOException e) {
    
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private synchronized List<Tree> loadTreesFromJsonFile() {
        System.out.println("Loading trees from JSON file..."); // Add debug output
        List<Tree> defaultTrees = loadDefaultTreesFromJsonFile();
        if (!defaultTrees.isEmpty()) {
            return defaultTrees;
        }

        // Provides an empty list if the JSON file is empty or not found
        return new ArrayList<>();
    }

    
    public List<Tree> getAllTrees() {
        System.out.println("Getting all trees..."); // Add debug output
        return trees;
    }
    
    public Tree getTreeById(long id) {
        // Search for the tree by its ID
        for (Tree tree : trees) {
            if (tree.getId() == id) {
                System.out.println("Getting specific tree..."); 
                return tree; // Returns the tree if found
            }
        }
        return null; // Returns null if a tree with the given ID is not found
    }

    public synchronized Tree addTree(Tree tree) {
        // adds a tree to Json
        tree.setId(getNewId());
        System.out.println("Entering addTree method...");
        trees.add(tree);
        saveTreesToJsonFile();
        System.out.println("Exiting addTree method...");
        return tree;
    }
    private  Long getNewId(){
        Long highestId = 1L;
        for( Tree tree : trees){
            if (tree.getId() > highestId){
                highestId = tree.getId() ;
            }
        }
            return highestId + 1;
    }
    
    public boolean deleteTreeById(long id) {
        synchronized (trees) {
            for (Tree tree : trees) {
             
                if (tree.getId() == id) {
                trees.remove(tree) ;
                    saveTreesToJsonFile();  
                    return true;  
                }
            }
        }
        return false;  // Tree with given ID not found
    }
    
    

    
    public Tree getRandomTree() {
        //Calls random tree from Json
        Random random = new Random();
        int randomIndex = random.nextInt(trees.size());
        return trees.get(randomIndex);
    }

    private void saveTreesToJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), trees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
