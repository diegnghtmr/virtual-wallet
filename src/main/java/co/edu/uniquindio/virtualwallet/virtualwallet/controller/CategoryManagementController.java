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

    public boolean addCategory(String idUser, CategoryDto newCategory) {
        return modelFactory.addCategory(idUser, newCategory);
    }

    public boolean removeCategory(String idUser, String idCategory) {
        return modelFactory.removeCategory(idUser, idCategory);
    }
}
