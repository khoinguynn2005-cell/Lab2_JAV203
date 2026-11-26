package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import poly.edu.entity.Product;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/search")
    public String searchForm() {
        return "products/search";
    }

    @GetMapping("/category/{categoryId}/search")
    public String search(@PathVariable String categoryId,
                         @RequestParam(defaultValue = "") String keyword,
                         @RequestParam Optional<Integer> page,
                         @RequestParam Optional<Integer> size,
                         Model model) {
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", currentPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("searchMessage", String.format(
                "Đang tìm sản phẩm thuộc loại %s, từ khóa %s, trang %d, kích thước trang %d",
                categoryId, keyword.isBlank() ? "(trống)" : keyword, currentPage, pageSize));
        return "products/search-result";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "products/product-form";
    }

    @PostMapping
    public String create(@ModelAttribute Product product, Model model) {
        boolean invalidName = product.getProductName() == null || product.getProductName().isBlank();
        boolean invalidPrice = product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0;
        if (invalidName || invalidPrice) {
            model.addAttribute("errorMessage", "Dữ liệu chưa hợp lệ: tên sản phẩm không được để trống và giá phải lớn hơn 0.");
            return "products/product-form";
        }
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(
                product.getQuantity() == null ? 0 : product.getQuantity()));
        model.addAttribute("product", product);
        model.addAttribute("subtotal", subtotal);
        return "products/product-result";
    }
}
