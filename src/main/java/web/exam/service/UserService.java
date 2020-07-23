package web.exam.service;


import web.exam.model.binding.UserRegisterBindingModel;
import web.exam.model.service.UserServiceModel;

public interface UserService {
    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);
    UserServiceModel findByUsername(String username);
    UserServiceModel logInUser(String username, String password);
}
