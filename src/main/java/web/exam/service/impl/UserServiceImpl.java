package web.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.exam.model.binding.UserRegisterBindingModel;
import web.exam.model.entity.User;
import web.exam.model.service.UserServiceModel;
import web.exam.repository.UserRepository;
import web.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        return this.userRepository.saveAndFlush(this.modelMapper.map(userRegisterBindingModel, User.class)) != null;

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel logInUser(String username, String password) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }
}
