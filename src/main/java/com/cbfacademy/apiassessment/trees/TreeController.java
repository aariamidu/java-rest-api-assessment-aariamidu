package com.cbfacademy.apiassessment.trees;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cbfacademy.apiassessment.emissions.JsonFileWriter;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;
    private final JsonFileWriter jsonFileWriter;

    public TreeController(TreeService treeService, JsonFileWriter jsonFileWriter) {
        this.treeService = treeService;
        this.jsonFileWriter = jsonFileWriter;

    }

    @GetMapping
    public ResponseEntity<List<Tree>> getAllTrees() {
        List<Tree> trees = treeService.getAllTrees();
        return new ResponseEntity<>(trees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tree> getTreeById(@PathVariable Long id) {
        Tree tree = treeService.getTreeById(id);
        if (tree != null) {
            return new ResponseEntity<>(tree, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Tree> addTree(@RequestBody Tree tree) {
        if (tree != null && isValidTree(tree)) {
            Tree addedTree = treeService.addTree(tree);
            boolean writeSuccess = jsonFileWriter.writeTreesJsonFile(treeService.getAllTrees());

            if (writeSuccess) {
                return new ResponseEntity<>(addedTree, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable Long id) {
        boolean deletionResult = treeService.deleteTreeById(id);
        if (deletionResult) {
            return new ResponseEntity<>("Tree deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Tree not found with the given ID", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/random")
    public ResponseEntity<Tree> getRandomTree() {
        Tree randomTree = treeService.getRandomTree();
        if (randomTree != null) {
            return new ResponseEntity<>(randomTree, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isValidTree(Tree tree) {
        // Checks if the tree object is not null
        if (tree == null) {
            return false;
        }

        // Checks if species, co2StoragePerTreePerYear, and
        // co2AbsorptionPerTreeIn80Years are not null or empty
        if (tree.getSpecies() == null || tree.getSpecies().isEmpty()) {
            return false;
        }

        if (tree.getCo2StoragePerTreePerYear() <= 0) {
            return false;
        }

        if (tree.getCo2AbsorptionPerTreeIn80Years() <= 0) {
            return false;
        }

        return true;
    }

}
