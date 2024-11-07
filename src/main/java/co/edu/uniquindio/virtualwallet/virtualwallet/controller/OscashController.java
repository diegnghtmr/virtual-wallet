package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import java.util.List;

public class OscashController extends CoreController {
    public OscashController() {
        super();
    }

    public List<String> getPercentageQuality() {
        return modelFactory.getCalifications();
    }

    public void addVotedUser(String selectedRating) {
        modelFactory.addVotedUser(selectedRating);
    }
}
