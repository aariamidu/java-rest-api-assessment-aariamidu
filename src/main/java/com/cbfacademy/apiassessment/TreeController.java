package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public List<Tree> getAllTrees() {
        return treeService.getAllTrees();
    }

    @GetMapping("/{id}")
    public Tree getTreeById(@PathVariable Long id) {
        return treeService.getTreeById(id);
    }

    @PostMapping
    public Tree addTree(@RequestBody Tree tree) {
        return treeService.addTree(tree);
    }
}
