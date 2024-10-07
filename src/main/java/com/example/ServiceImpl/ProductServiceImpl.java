package com.example.ServiceImpl;

import com.example.Config.BsicUtil;
import com.example.DTO.PageableDTO;
import com.example.DTO.PageableResponseVO;
import com.example.DTO.Result;
import com.example.Model.Category;
import com.example.Model.Product;
import com.example.Repository.CategoryRepository;
import com.example.Repository.ProductRepository;
import com.example.Service.ImageUploader;
import com.example.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl extends BsicUtil implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional
    @Override
    public Result createProduct(String productName,
                                String productDescription,
                                String price,
                                String quantity,
                                MultipartFile file,
                                String categoryName
    ) {
        Result data = isValid(productName, productDescription, price, quantity, file, categoryName);
        if (!data.getMessage().equals("")) {
            return prepareResponseObject("014", data.getMessage(), null);
        }
        String url;
        if (!isNullOrEmpty(productName)) {
            url = imageUploader.uploadImage(file);
        } else {
            return prepareResponseObject("012", "No Image File Found", null);
        }

        Category categories = categoryRepository.findByCategoryTitle(categoryName);
        if(isNullOrEmpty(categories)){
            return prepareResponseObject("038","No CAtegory Found By the CategoryTitle",null);
        }
        Product product = new Product();
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setPrice(price);
        product.setImage(url);
        product.setQuantity(quantity);
        product.setCategory(categories);
        Product productSave = productRepository.save(product);
        log.warn("calling to the saveProduct {}", productSave);
        if (isNullOrEmpty(productSave)) {
            return prepareResponseObject("013", "No Products Are available", null);
        }
        return prepareResponseObject("014", "Product Crated Successfully", null);
    }

    @Override
    public Result getAllProduct(PageableDTO pageable ) {
        Pageable pageable1 = null;
        pageable1 = PageRequest.of(pageable.getPage(), pageable.getPageSize());
        Page<Product> all = productRepository.findAll(pageable1);
        PageableResponseVO pageableResponseVO = new PageableResponseVO(all.getContent(), all, pageable);
//        List<Product> all = productRepository.findAll();
        return prepareResponseObject("022", "ProductFetched SuccessFully", pageableResponseVO);
    }

    @Override
    public Result getSingleProduct(int id) {
        if (isNullOrEmpty(id)) {
            return prepareResponseObject("023", "The Given Id is Empty", null);
        }
        log.warn("the given id is {}", id);
        Product product = productRepository.findById(id).get();
        if (isNullOrEmpty(product)) {
            return prepareResponseObject("024", "The Product Is Empty", null);
        }
        log.warn("the SingleProduct is {}", product);
        return prepareResponseObject("025", "SingleProduct Fetched Successfully", product);
    }

    @Override
    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, MultipartFile file) {
        if (isNullOrEmpty(id)) {
            return prepareResponseObject("024", "In that given Id no Product are Avilable", null);
        }
        Product product = productRepository.findById(id).get();

//        Result valid = isValid(productName, productDescription, price, quantity, file);
//        if (!valid.getMessage().equals("")) {
//            return prepareResponseObject("027", valid.getMessage(), null);
//        }

        String url;
        if (!isNullOrEmpty(file)) {
            url = imageUploader.uploadImage(file);
        } else {
            return prepareResponseObject("025", "There was a Problrm With Image", null);
        }
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setImage(url);
        Product saveProduct = productRepository.save(product);
        log.warn("calling to EditProduct save Method {}", saveProduct);
        if (isNullOrEmpty(saveProduct)) {
            return prepareResponseObject("029", "Prouct is Empty", null);
        }
        return prepareResponseObject("030", "Product Updated SuccessFully", saveProduct);
    }


    @Override
    public Result deleteProduct(int id) {
        if (isNullOrEmpty(id)) {
            return prepareResponseObject("031", "The given id is null", null);
        }
        productRepository.deleteById(id);
        log.warn("calling to the deleetById {}");

        return prepareResponseObject("032", "Product Deleted Successfully", null);
    }

    private Result isValid(String productName,
                           String productDescription,
                           String price,
                           String quantity,
                           MultipartFile file,
                           String categoryName
    ) {
        if (isNullOrEmpty(productName)) {
            return prepareResponseObject("015", "ProductName Should Not Be Empty", null);
        }
        if (isNullOrEmpty(productDescription)) {
            return prepareResponseObject("016", "ProductDescription Should Not Be Empty", null);
        }
        if (isNullOrEmpty(price)) {
            return prepareResponseObject("017", "ProductPrice Should Not Be Empty", null);
        }
        if (isNullOrEmpty(quantity)) {
            return prepareResponseObject("018", "ProductQuantity Should Not Be Empty", null);
        }
        if (productRepository.existsByProductName(productName)) {
            return prepareResponseObject("019", "Product Exist By The Given Name", null);
        }
        if (isNullOrEmpty(categoryName)) {
            return prepareResponseObject("035", "CAtegory Name Should Not Be Empty", null);
        }
//        if (categoryRepository.existsByCategoryTitle(categoryName.get(0))) {
//            return prepareResponseObject("036", "Category Allready Exist With That Given Name", null);
//        }
        return prepareResponseObject("020", "", null);
    }


    @Override
    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, String file) {
        return null;
    }
}
