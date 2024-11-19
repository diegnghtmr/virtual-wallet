package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.AdministratorDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.Administrator;

public class AdministratorDataController extends CoreController {
    public AdministratorDataController(){
        super();
    }

    public boolean updateAdmin(Administrator person, AdministratorDto administratorDto) {
        return modelFactory.updateAdmin(person, administratorDto);
    }

    public Administrator getAdminById(String id) {
        return modelFactory.getAdminById(id);
    }
}
