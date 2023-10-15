package com.cbfacademy.apiassessment;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Arrays;


@Service
public class TreeService {
    private final List<Tree> trees;

    private static final String JSON_FILE_PATH = "trees.json";

    public TreeService() {
        trees = loadTreesFromJsonFile();
    }

    private List<Tree> loadTreesFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(JSON_FILE_PATH);

        if (file.exists()) {
            try {
                Tree[] treesArray = objectMapper.readValue(file, Tree[].class);
                return new ArrayList<>(Arrays.asList(treesArray));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Return default trees if the file doesn't exist or there's an error reading it
        return getDefaultTrees();
    }

    private void saveTreesToJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), trees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tree> getAllTrees() {
        return trees;
    }
    private List<Tree> getDefaultTrees() {
        return new ArrayList<>(Arrays.asList(
            new Tree(1L, "Oak", 18.87, 1509.59),
            new Tree(2L, "Beech", 15.89, 1270.87),
            new Tree(3L, "Spruce", 20.13, 1610.10),
            new Tree(4L, "Fir", 20.72, 1657.24),
            new Tree(5L, "Douglas Fir", 46.46, 3717.04),
            new Tree(6L, "Pine", 14.39, 1150.96),
            new Tree(7L, "Larch", 35.91, 2872.63)
        ));
    }
    public Tree getTreeById(long id) {
        // Search for the tree by its ID
        for (Tree tree : trees) {
            if (tree.getId() == id) {
                return tree; // Return the tree if found
            }
        }
        return null; // Return null if a tree with the given ID is not found
    }

    public Tree addTree(Tree tree) {
        tree.setId(System.currentTimeMillis());
        trees.add(tree);
        saveTreesToJsonFile();
        return tree;
    }
    public void deleteTreeById(long id) {
        Iterator<Tree> iterator = trees.iterator();
        while (iterator.hasNext()) {
            Tree tree = iterator.next();
            if (tree.getId() == id) {
                iterator.remove();
                saveTreesToJsonFile();
                return;
            }
        }
    }
    public Tree getRandomTree() {
        Random random = new Random();
        int randomIndex = random.nextInt(trees.size());
        return trees.get(randomIndex);
    }
    

}
