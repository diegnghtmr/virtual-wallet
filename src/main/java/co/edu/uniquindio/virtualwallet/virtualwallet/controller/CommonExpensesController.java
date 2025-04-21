package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.model.Category;

import java.util.List;
import java.util.Map;

public class CommonExpensesController extends CoreController{

    public CommonExpensesController() {
        super();
    }

    public List<Category> getcommonExpenses() {
        return modelFactory.getcommonExpenses();
    }


    public Map<String, Double> calculateTotalExpenses() {
        return modelFactory.calculateTotalExpenses();
    }
}
