package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/request-demo")
public class RequestDemoController {

    @GetMapping
    public String showRequestDemo() {
        return "RequestDemo/Request-demo";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        return showResult(model, "GET", "Danh sách sản phẩm", "listProducts");
    }

    @PostMapping("/products")
    public String addProduct(Model model,
                             @RequestParam(defaultValue = "Sản phẩm mới") String productName) {
        model.addAttribute("productName", productName);
        return showResult(model, "POST", "Yêu cầu thêm sản phẩm", "addProduct");
    }

    @GetMapping("/search")
    public String searchProducts(Model model,
                                 @RequestParam(defaultValue = "") String keyword) {
        model.addAttribute("keyword", keyword);
        return showResult(model, "GET", "Tìm kiếm sản phẩm", "searchProducts");
    }

    @GetMapping("/category/{categoryId}")
    public String productsByCategory(Model model, @org.springframework.web.bind.annotation.PathVariable String categoryId) {
        model.addAttribute("categoryId", categoryId);
        return showResult(model, "GET", "Sản phẩm theo mã loại", "productsByCategory");
    }

    private String showResult(Model model, String method, String action, String handler) {
        model.addAttribute("requestMethod", method);
        model.addAttribute("requestUrl", ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        model.addAttribute("action", action);
        model.addAttribute("handler", handler);
        return "RequestDemo/Request-demo";
    }
}
