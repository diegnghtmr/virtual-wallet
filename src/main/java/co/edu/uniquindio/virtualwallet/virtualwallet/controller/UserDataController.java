package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.model.User;

public class UserDataController extends CoreController {
    public UserDataController() {
        super();
    }

    // User Data Methods
    // -----------------
    public boolean updateUser(User person, UserDto userDto) {
        return modelFactory.updateUser(person, userDto);
    }

    public User getUserById(String id) {
        return modelFactory.getUserById(id);
    }
}
