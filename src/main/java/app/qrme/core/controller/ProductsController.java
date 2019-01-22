package app.qrme.core.controller;

import app.qrme.core.data.repository.ProductsRepository;
import app.qrme.core.entities.Products;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class ProductsController extends AbstractCRUDController<Products, Long> {

    public ProductsController(ProductsRepository repository) {
        super(repository);
    }

}
