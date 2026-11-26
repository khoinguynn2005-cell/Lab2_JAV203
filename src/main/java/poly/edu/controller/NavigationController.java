package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/navigation")
public class NavigationController {

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("message", "Thao tác sản phẩm đã hoàn tất thành công.");
        return "navigation/message";
    }

    @GetMapping("/intermediate")
    public String intermediate() {
        return "forward:/navigation/forward-target";
    }

    @GetMapping("/forward-target")
    public String forwardTarget(Model model) {
        model.addAttribute("message", "Request được forward đến URL xử lý trong cùng request.");
        return "navigation/message";
    }

    @GetMapping("/redirect")
    public String redirectToProducts() {
        return "redirect:/dashboard";
    }

    @PostMapping("/products")
    public String addProductAndRedirect(@RequestParam(defaultValue = "Sản phẩm mới") String productName,
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("navigationNotice",
                "Đã thêm " + productName + " thành công. Đây là request mới sau redirect.");
        return "redirect:/dashboard";
    }

    @GetMapping("/status")
    @ResponseBody
    public String status() {
        return "OK - Product management system is running.";
    }

    @GetMapping("/guide")
    public String guide() {
        return "navigation/guide";
    }
}
