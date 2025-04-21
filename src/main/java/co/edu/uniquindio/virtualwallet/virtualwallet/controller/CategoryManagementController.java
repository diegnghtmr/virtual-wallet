package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;

import java.util.List;

public class CategoryManagementController extends CoreController {
    public CategoryManagementController() {
        super();
    }

    public List<CategoryDto> getCategoriesByUser(String userId) {
        return modelFactory.getCategoriesByUser(userId);
    }

    public boolean addCategory(String idUser, CategoryDto categoryDto) {
        return modelFactory.addCategory(idUser, categoryDto);
    }

    public boolean removeCategory(String idUser, String idCategory) {
        return modelFactory.removeCategory(idUser, idCategory);
    }

    public boolean updateCategory(String idUser, CategoryDto categorySelected, CategoryDto categoryDto) {
        return modelFactory.updateCategory(idUser, categorySelected, categoryDto);
    }

    public boolean isCategoryExists(String id) {
        return modelFactory.isCategoryExists(id);
    }
}
