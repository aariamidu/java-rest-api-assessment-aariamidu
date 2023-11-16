package com.cbfacademy.apiassessment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cbfacademy.apiassessment.emissions.JsonFileWriter;
import com.cbfacademy.apiassessment.trees.Tree;
import com.cbfacademy.apiassessment.trees.TreeController;
import com.cbfacademy.apiassessment.trees.TreeService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TreeControllerTest {

        private MockMvc mockMvc;

        @Mock
        private TreeService treeService;

        @Mock
        private JsonFileWriter jsonFileWriter;

        @InjectMocks
        private TreeController treeController;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(treeController).build();
        }

        @Test
        public void getAllTrees_ReturnsListOfTrees() throws Exception {
                // Arrange
                List<Tree> trees = Arrays.asList(
                                new Tree(1L, "Oak", 20.0, 1600.0),
                                new Tree(2L, "Maple", 18.5, 1480.0));
                when(treeService.getAllTrees()).thenReturn(trees);

                // Act & Assert
                mockMvc.perform(get("/api/trees"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].species", is("Oak")))
                                .andExpect(jsonPath("$[1].species", is("Maple")));
        }

        @Test
        public void getTreeById_ValidId_ReturnsTree() throws Exception {
                // Arrange
                Tree oakTree = new Tree(1L, "Oak", 20.0, 1600.0);
                when(treeService.getTreeById(1L)).thenReturn(oakTree);

                // Act & Assert
                mockMvc.perform(get("/api/trees/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.species", is("Oak")));
        }

    @Test
    public void getTreeById_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        when(treeService.getTreeById(20L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/trees/20"))
                .andExpect(status().isNotFound());
    }

        @Test
        public void addTree_ValidTree_ReturnsCreated() throws Exception {
                // Arrange
                Tree savedTree = new Tree(3L, "Pine", 15.0, 1200.0);
                when(treeService.addTree(any(Tree.class))).thenReturn(savedTree);
                when(jsonFileWriter.writeTreesJsonFile(anyList())).thenReturn(true);

                // Act & Assert
                mockMvc.perform(post("/api/trees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"species\":\"Pine\",\"co2StoragePerTreePerYear\":15.0,\"co2AbsorptionPerTreeIn80Years\":1200.0}"))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id", is(3)))
                                .andExpect(jsonPath("$.species", is("Pine")));
        }

        @Test
        public void addTree_InvalidTree_ReturnsBadRequest() throws Exception {
                // Arrange - sends an empty request body

                // Act & Assert
                mockMvc.perform(post("/api/trees")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest());
        }

    @Test
    public void deleteTree_ValidId_ReturnsOk() throws Exception {
        // Arrange
        when(treeService.deleteTreeById(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/trees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tree deleted successfully"));
    }

    @Test
    public void deleteTree_InvalidId_ReturnsNotFound() throws Exception {
        // Arrange
        when(treeService.deleteTreeById(100L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/trees/100"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Tree not found with the given ID"));
    }

        @Test
        public void getRandomTree_ReturnsRandomTree() throws Exception {
                // Arrange
                Tree randomTree = new Tree(1L, "Random Tree", 25.0, 2000.0);
                when(treeService.getRandomTree()).thenReturn(randomTree);

                // Act & Assert
                mockMvc.perform(get("/api/trees/random"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.species", is("Random Tree")));
        }
}
