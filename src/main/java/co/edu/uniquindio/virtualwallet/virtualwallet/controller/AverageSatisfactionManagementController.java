package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

public class AverageSatisfactionManagementController extends CoreController {
    public AverageSatisfactionManagementController() {
        super();
    }

    public double getAverageRating() {
        return modelFactory.getAverageRating();
    }

}
