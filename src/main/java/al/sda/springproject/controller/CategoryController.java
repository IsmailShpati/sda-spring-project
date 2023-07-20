package al.sda.springproject.controller;

import al.sda.springproject.dto.ServiceResponse;
import al.sda.springproject.entity.ProductCategory;
import al.sda.springproject.service.CategoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class CategoryController {

    private final CategoriesService categoriesService;

    public CategoryController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    // GET /categories
    @GetMapping("/categories")
    public String getCategoriesView(ModelMap modelMap) {
        modelMap.addAttribute("categories",
                this.categoriesService.getCategories());
        return "category/categories";
    }

    @PostMapping("/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable String categoryId) {
        ServiceResponse<ProductCategory> deletedCategoryResponse =
                this.categoriesService.deleteCategory(categoryId);

        deletedCategoryResponse
                .ifHasErrors(this::printErrors);
        
        return "redirect:/categories";
    }

    private void printErrors(List<String> errorList) {
        log.info("errorList: {}", errorList);
    }


}
