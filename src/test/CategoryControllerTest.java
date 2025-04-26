package test;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCategoryById() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO("Categoria 1");
        when(categoryService.getById(1L)).thenReturn(categoryDTO);

        mockMvc.perform(get("/categories/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Categoria 1"));
    }
}

