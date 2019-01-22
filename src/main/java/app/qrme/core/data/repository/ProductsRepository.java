package app.qrme.core.data.repository;

import app.qrme.core.entities.Products;
import app.qrme.lib.data.repo.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends GenericRepository<Products, Long> {

}
