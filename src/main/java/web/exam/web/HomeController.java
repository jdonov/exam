package web.exam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import web.exam.model.entity.CategoryNames;
import web.exam.model.service.ProductServiceModel;
import web.exam.service.ProductService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController{
    private final ProductService productService;
    private final HttpSession httpSession;

    @Autowired
    public HomeController(ProductService productService, HttpSession httpSession) {
        super(httpSession);
        this.productService = productService;
        this.httpSession = httpSession;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        if (isLogged()) {

            modelAndView.addObject("foods", this.productService.getProductByCategory(CategoryNames.FOOD));
            modelAndView.addObject("drinks", this.productService.getProductByCategory(CategoryNames.DRINK));
            modelAndView.addObject("households", this.productService.getProductByCategory(CategoryNames.HOUSEHOLD));
            modelAndView.addObject("other", this.productService.getProductByCategory(CategoryNames.OTHER));
            modelAndView.addObject("totalPrice", this.productService.getTotalPrice());

            modelAndView.setViewName("home");
        } else {
            returnIndex(modelAndView);
        }
        return modelAndView;
    }
}
