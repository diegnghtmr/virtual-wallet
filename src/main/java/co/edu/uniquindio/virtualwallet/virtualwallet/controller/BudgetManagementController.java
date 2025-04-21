package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.BudgetDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.CategoryDto;

import java.util.List;

public class BudgetManagementController extends CoreController {
    public BudgetManagementController() {
        super();
    }


    public List<BudgetDto> getBudgetsByUser(String userId) {
        return modelFactory.getBudgetsByUser(userId);
    }

    public List<CategoryDto> getCategoriesByUserId(String id) {
        return modelFactory.getCategoriesByUserId(id);
    }

    public boolean addBudget(BudgetDto budgetDto) {
        return modelFactory.addBudget(budgetDto);
    }

    public boolean removeBudget(BudgetDto budgetSelected) {
        return modelFactory.removeBudget(budgetSelected);
    }

    public boolean updateBudget(BudgetDto budgetSelected, BudgetDto budgetDto) {
        return modelFactory.updateBudget(budgetSelected, budgetDto);
    }


    public boolean isBudgetIdExists(String id) {
        return modelFactory.isBudgetIdExists(id);
    }
}
