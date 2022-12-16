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

    @PostMapping("/login/search_main")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model) {
        if(ot.isEmpty()){
            ot = "0";
        }
        if(Do.isEmpty()){
            Do = "99999999";
        }
        // Если диапазон цен от и до не пустой
        if (!ot.isEmpty() & !Do.isEmpty()) {
            System.out.println("от:"+ot);
            System.out.println("do:"+Do);
            // Если сортировка по цене выбрана
            if (!price.isEmpty()) {
                // Если в качестве сортировки выбрана сортировка по возрастанию
                if (price.equals("sorted_by_ascending_price")) {
                // Если категория товара не пустая
                if (!category.isEmpty()) {
                    // Если категория равная мебели
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        // Если категория равна бытовой технике
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));

                    }else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));

                    }else if (category.equals("food")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                    }else if (category.equals("cosmetics")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                    }else if (category.equals("package")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 6));
                    }else if (category.equals("decor")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 7));
                    }else if (category.equals("electronics")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 8));
                    }else if (category.equals("dishes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 9));
                        // Если категория не выбрана
                    }
                } else {
                    model.addAttribute("search_product", productRepository.findByTitleOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                }
                // Если в качестве сортировки выбрана сортировка по убыванию
            } else if (price.equals("sorted_by_descending_price")) {
                if (!category.isEmpty()) {
                    if (category.equals("furniture")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));

                    }else if (category.equals("clothes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                    }else if (category.equals("food")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                    }else if (category.equals("cosmetics")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                    }else if (category.equals("package")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 6));
                    }else if (category.equals("decor")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 7));
                    }else if (category.equals("electronics")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 8));
                    }else if (category.equals("dishes")) {
                        model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 9));

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
