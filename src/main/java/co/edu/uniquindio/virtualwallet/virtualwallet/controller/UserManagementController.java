package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.UserDto;

import java.util.List;

public class UserManagementController extends CoreController{
    public UserManagementController() {
        super();
    }

    public List<UserDto> getUsers() {
        return modelFactory.getUserListDto();
    }

    public boolean addUser(UserDto userDto) {
        return modelFactory.addUser(userDto);
    }

    public boolean removeUser(UserDto userSelected) {
        return modelFactory.removeUser(userSelected);
    }

    public boolean updateUser(UserDto userSelected, UserDto userDto) {
        return modelFactory.adminUpdateUser(userSelected, userDto);
    }
}
