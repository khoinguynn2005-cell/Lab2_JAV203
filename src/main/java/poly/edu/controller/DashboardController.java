package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import poly.edu.entity.Product;
import poly.edu.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DashboardController {

    @ModelAttribute("sampleProducts")
    public List<Product> sampleProducts() {
        return List.of(
                product("SP001", "Laptop Pro 14", "25990000", 8, "Điện tử", true),
                product("SP002", "Chuột không dây", "450000", 25, "Phụ kiện", true),
                product("SP003", "Bàn phím cơ", "1290000", 12, "Phụ kiện", true),
                product("SP004", "Màn hình 27 inch", "5990000", 6, "Điện tử", true),
                product("SP005", "Bình giữ nhiệt", "320000", 18, "Gia dụng", false));
    }

    @ModelAttribute("sampleCategories")
    public List<ProductCategory> sampleCategories() {
        return List.of(new ProductCategory("CAT01", "Điện tử"),
                new ProductCategory("CAT02", "Phụ kiện"),
                new ProductCategory("CAT03", "Gia dụng"));
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(@ModelAttribute("sampleProducts") List<Product> products,
                            Model model) {
        BigDecimal inventoryValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("welcomeMessage", "Chao mung ban den voi trang quan ly san pham!");
        model.addAttribute("featuredProduct", products.get(0));
        model.addAttribute("productCount", products.size());
        model.addAttribute("inventoryValue", inventoryValue);
        return "products/product-dashboard";
    }

    private Product product(String id, String name, String price, int quantity, String category, boolean active) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setPrice(new BigDecimal(price));
        product.setQuantity(quantity);
        product.setCategoryId(category);
        product.setActive(active);
        return product;
    }
}
