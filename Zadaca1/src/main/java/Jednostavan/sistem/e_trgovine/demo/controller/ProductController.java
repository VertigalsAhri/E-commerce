package Jednostavan.sistem.e_trgovine.demo.controller;

import Jednostavan.sistem.e_trgovine.demo.model.Product;
import Jednostavan.sistem.e_trgovine.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Lista proizvoda
    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    // Forma za novi proizvod
    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    // Kreiranje proizvoda
    @PostMapping
    public String createProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.createProduct(product);
        redirectAttributes.addFlashAttribute("message", "Proizvod je uspješno dodan!");
        return "redirect:/products";
    }

    // Prikaz detalja
    @GetMapping("/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Proizvod nije pronađen");
        }
        model.addAttribute("product", product);
        return "products/detail";
    }

    // Forma za uređivanje
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new RuntimeException("Proizvod nije pronađen");
        }
        model.addAttribute("product", product);
        return "products/form";
    }

    // Ažuriranje proizvoda
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, 
                              RedirectAttributes redirectAttributes) {
        productService.updateProduct(id, product);
        redirectAttributes.addFlashAttribute("message", "Proizvod je uspješno ažuriran!");
        return "redirect:/products";
    }

    // Brisanje proizvoda
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("message", "Proizvod je uspješno obrisan!");
        return "redirect:/products";
    }
}


