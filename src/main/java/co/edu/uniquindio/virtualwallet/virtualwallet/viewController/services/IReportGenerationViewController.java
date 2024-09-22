package co.edu.uniquindio.virtualwallet.virtualwallet.viewController.services;

import javafx.event.ActionEvent;

public interface IReportGenerationViewController {
    void onGenerateCSV(ActionEvent event);

    void onGeneratePDF(ActionEvent event);
}
