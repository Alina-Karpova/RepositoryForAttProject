package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.repositories.OrderRepository;
import com.example.springsecurityapplication.repositories.PersonRepository;
import com.example.springsecurityapplication.repositories.RoleRepository;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.util.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ProductValidator productValidator;
    private final ProductService productService;

    private final CategoryRepository categoryRepository;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PersonService personService;
    private final OrderRepository orderRepository;

    @Autowired
    public AdminController(ProductValidator productValidator, ProductService productService, CategoryRepository categoryRepository, PersonService personService, PersonRepository personRepository, RoleRepository roleRepository, OrderRepository orderRepository) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.personRepository = personRepository;
        this.personService = personService;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('')")
//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('')")

    // Метод по отображению главной страницы администратора с выводом товаров
    @GetMapping()
    public String admin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();

        if(role.equals("ROLE_USER")){
            return "redirect:/index";
        }


        return "admin/admin";
    }

    // Метод по отображению формы добавление
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("products", productService.getAllProduct());
//        System.out.println(categoryRepository.findAll().size());
        return "product/addProduct";
    }
    //метод по отображению списка пользователей
    @GetMapping("/person")
    public String editPerson(Model model){
        model.addAttribute("persons", personService.getAllPerson());
        return "user/editPerson";
    }
    // Метод по добавлению объекта с формы в таблицу product
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") MultipartFile file_one, @RequestParam("file_two") MultipartFile file_two, @RequestParam("file_three") MultipartFile file_three, @RequestParam("file_four") MultipartFile file_four, @RequestParam("file_five") MultipartFile file_five) throws IOException {

        productValidator.validate(product, bindingResult);
        if(bindingResult.hasErrors()){
            return "product/addProduct";
        }
        // Проверка на пустоту файла
        if(file_one != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" + uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_one.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_two != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_two.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_three != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_three.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_four != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_four.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        // Проверка на пустоту файла
        if(file_five != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_five.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageProduct(image);
        }

        productService.saveProduct(product);
        return "redirect:/admin/product/edit/"+product.getId();
    }




    // Метод по удалению товара по id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    // Метод по получению товара по id и отображение шаблона редактирования
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("editProduct", productService.getProductId(id));
        //model.addAttribute("editPicture", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }
//    @GetMapping("product/edit/pictures/{id}")
//    public String editPicture(@PathVariable("id") int id, Model model){
//        model.addAttribute("editPicture", productService.getProductId(id));
//
//        return "product/editPicture";
//    }


    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("editProduct") Product product, @PathVariable("id") int id){

        productService.updateProduct(id, product);
        return "redirect:/admin/product/edit/"+product.getId();
    }

    // Метод по удалению пользователя по id
    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }
    // Метод по получению пользователя по id и отображение шаблона редактирования
    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("editPerson", personService.getPersonId(id));
        model.addAttribute("role", roleRepository.findAll());
        return "user/personEditorPage";
    }

    @PostMapping("/person/edit/{id}")
    public String editPerson(@ModelAttribute("editPerson") Person person, @PathVariable("id") int id) {

        personService.updatePerson(id, person);
        return "redirect:/admin/person";
    }

    @PostMapping("/orders/search")
    public String productSearch(@RequestParam("search") String search, Model model){
        model.addAttribute("search_order", orderRepository.findByNumberContainingIgnoreCase(search));
        model.addAttribute("value_search", search);
        return "admin/orders";
    }
    @GetMapping("/orders")
    public String ordersUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orders", orderList);
        return "admin/orders";
    }
    @GetMapping("product/edit/pictures/{id}")
    public String editPicture(@PathVariable("id") int id, Model model){
        model.addAttribute("editPicture", productService.getProductId(id));

        return "product/editPicture";
    }
    //BindingResult bindingResult,
    @PostMapping("product/edit/pictures/{id}")
    public String editProductPictures(@ModelAttribute("editPicture") Product product, @PathVariable("id") int id,  @RequestParam("file0") MultipartFile file_1, @RequestParam("file1") MultipartFile file_two, @RequestParam("file2") MultipartFile file_three, @RequestParam("file3") MultipartFile file_four, @RequestParam("file4") MultipartFile file_five) throws IOException {

//        productValidator.validate(product, bindingResult);
//        if(bindingResult.hasErrors()){
//            return "product/editPicture";
//        }

        // Проверка на пустоту файла
        if(file_1 != null){

            // Дирректория по сохранению файла
            File uploadDir = new File("D:" + uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_1.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_1.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
           // Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//           product.changeImageProduct(0, image);
            Product productPic = productService.getProductId(id);
           Image image = productPic.getImageList().get(0);
             int pictureId = image.getId();
            productService.updatePictures(pictureId, resultFileName);
        }

        // Проверка на пустоту файла
        if(file_two != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" + uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_two.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//            product.changeImageProduct(1, image);
//            productService.updatePictures(image.getId(), image.getFileName());

            Product productPic = productService.getProductId(id);
            Image image = productPic.getImageList().get(1);
            int pictureId = image.getId();
            productService.updatePictures(pictureId, resultFileName);
        }

        // Проверка на пустоту файла
        if(file_three != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_three.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//            product.changeImageProduct(2, image);
//            productService.updatePictures(image.getId(), image.getFileName());
            Product productPic = productService.getProductId(id);
            Image image = productPic.getImageList().get(2);
            int pictureId = image.getId();
            productService.updatePictures(pictureId, resultFileName);
        }

        // Проверка на пустоту файла
        if(file_four != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" +uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_four.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//            product.changeImageProduct(3, image);
//            productService.updatePictures(image.getId(), image.getFileName());
            Product productPic = productService.getProductId(id);
            Image image = productPic.getImageList().get(3);
            int pictureId = image.getId();
            productService.updatePictures(pictureId, resultFileName);
        }

        // Проверка на пустоту файла
        if(file_five != null){
            // Дирректория по сохранению файла
            File uploadDir = new File("D:" + uploadPath);
            // Если данной дирректории по пути не сущетсвует
            if(!uploadDir.exists()){
                // Создаем данную дирректорию
                uploadDir.mkdir();
            }
            // Создаем уникальное имя файла
            // UUID представляет неищменный универсальный уникальный идентификатор
            String uuidFile = UUID.randomUUID().toString();
            // file_one.getOriginalFilename() - наименование файла с формы
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            // Загружаем файл по указаннопу пути
            file_five.transferTo(new File("D:" +uploadPath + "/" + resultFileName));
//            Image image = new Image();
//            image.setProduct(product);
//            image.setFileName(resultFileName);
//            product.changeImageProduct(4, image);
//            productService.updatePictures(image.getId(), image.getFileName());
            Product productPic = productService.getProductId(id);
            Image image = productPic.getImageList().get(4);
            int pictureId = image.getId();
            productService.updatePictures(pictureId, resultFileName);
        }
        return "redirect:/admin/product/edit/"+product.getId();
        //return "product/editPicture";
    }

}
