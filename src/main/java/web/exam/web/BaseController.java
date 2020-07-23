package web.exam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public abstract class BaseController {
    private final HttpSession httpSession;

    @Autowired
    public BaseController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public boolean isLogged() {
        return this.httpSession.getAttribute("user") != null ;
    }

    public String checkLogin(String view) {
        return this.httpSession.getAttribute("user") == null ? "index" : view;
    }

    public String redirect(String view) {
        return "redirect:" + view;
    }

    public String redirectIndex() {
        return "redirect:/";
    }

    public ModelAndView redirectIndex(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
    public void returnIndex(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
    }
}
