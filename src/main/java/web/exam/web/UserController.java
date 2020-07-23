package web.exam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.exam.model.binding.UserLoginBindingModel;
import web.exam.model.binding.UserRegisterBindingModel;
import web.exam.model.service.UserServiceModel;
import web.exam.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@Valid @ModelAttribute("userLoginBindingModel") UserLoginBindingModel userLoginBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                  ModelAndView modelAndView,
                                  HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            modelAndView.setViewName("redirect:login");
        } else {
            UserServiceModel userServiceModel = this.userService.logInUser(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());
            if (userServiceModel == null) {
                redirectAttributes.addFlashAttribute("notFound", true);
                redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                modelAndView.setViewName("redirect:login");
            } else {
                httpSession.setAttribute("user", userServiceModel);
                modelAndView.setViewName("redirect:/");
            }
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                     final BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                     ModelAndView modelAndView) {

        confirmPasswords(userRegisterBindingModel, bindingResult);
        userExists(userRegisterBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            modelAndView.setViewName("redirect:register");
        } else {
            this.userService.registerUser(userRegisterBindingModel);
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    private void confirmPasswords(UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            FieldError err = new FieldError("userRegisterBindingModel", "confirmPassword", "Passwords do not match!");
            bindingResult.addError(err);
        }
    }

    private void userExists(UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult) {
        if (userService.findByUsername(userRegisterBindingModel.getUsername()) != null) {
            FieldError err = new FieldError("userRegisterBindingModel", "username", "Username already exists!");
            bindingResult.addError(err);
        }
    }
}
