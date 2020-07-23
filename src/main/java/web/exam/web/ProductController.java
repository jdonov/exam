package web.exam.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.exam.model.binding.ProductAddBindingModel;
import web.exam.service.ProductService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final HttpSession httpSession;

    @Autowired
    public ProductController(ProductService productService, HttpSession httpSession) {
        super(httpSession);
        this.productService = productService;
        this.httpSession = httpSession;
    }

    @GetMapping("/add")
    public String add(Model model) {
        if (isLogged()) {
            if (!model.containsAttribute("productAddBindingModel")) {
                model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
            }
            return "product-add";
        } else {
            return redirectIndex();
        }
    }

    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (this.productService.getByName(productAddBindingModel.getName()) != null) {
            FieldError err = new FieldError("productAddBindingModel", "name", "Name already exists!");
            bindingResult.addError(err);
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return redirect("add");
        } else {
            this.productService.addProduct(productAddBindingModel);
            return redirectIndex();
        }
    }

    @GetMapping("/buy/{id}")
    public String buy(@PathVariable("id") String id) {
        if (isLogged()) {
            this.productService.buyProduct(id);
        }
        return redirectIndex();
    }

    @GetMapping("/buyall")
    public String buyAll() {
        if (isLogged()) {
            this.productService.buyAllProducts();
        }
        return redirectIndex();
    }
}
