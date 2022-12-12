package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// http:localhost:8080/auth/login
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;

    private final PersonService personService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, ProductService productService, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/auth/login")
    public String addProduct(Model model){

//        System.out.println(categoryRepository.findAll().size());
        return "product/addProduct";
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("products", productService.getAllProduct());
            return "login";
        }

    @PostMapping("/search_main")
    public String mainSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model) {
    System.out.println("Поиск у гостя");
    if (!ot.isEmpty() & !Do.isEmpty()) {
        // Если сортировка по цене выбрана
        if (!price.isEmpty()) {
            // Если в качестве сортировки выбрана сортировка по возрастанию
            if (price.equals("  ")) {
                // Если категория товара не пустая
                if (!category.isEmpty()) {
                    // Если категория равная мебели
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        // Если категория равная бытовой техники
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        // Если категория равная одежде
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                    }
                    // Если категория не выбрана
                } else {
                    model.addAttribute("search_product", productRepository.findByTitleOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                }

                // Если в качестве сортировки выбрана сортировкам по убыванию
            } else if (price.equals("sorted_by_descending_price")) {

                // Если категория не пустая
                if (!category.isEmpty()) {
                    // Если категория равная мебели
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        // Если категория равная бытовой техники
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        // Если категория равная одежде
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                    }
                    // Если категория не выбрана
                } else {
                    model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                }
            }
        } else {
            model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
        }
    } else {
        model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
    }
    model.addAttribute("value_search", search);
    model.addAttribute("value_price_ot", ot);
    model.addAttribute("value_price_do", Do);
    model.addAttribute("products", productService.getAllProduct());
    //return "main";
    return "login";
}


    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("person", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/index";
    }


}