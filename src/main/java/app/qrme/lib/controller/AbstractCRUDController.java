package app.qrme.lib.controller;

import app.qrme.lib.data.config.QueryCfg;
import app.qrme.lib.data.dto.ResponseDTO;
import app.qrme.lib.data.repo.GenericRepository;
import app.qrme.lib.data.specification.GenericSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@MappedSuperclass
public abstract class AbstractCRUDController<T, ID extends Serializable> {

    private final List<QueryCfg> filterFields = new ArrayList<>();
    private final GenericRepository<T, ID> genericRepository;

    @Autowired
    private MessageSource messageSource;

    public AbstractCRUDController(GenericRepository<T, ID> repository) {
        this.genericRepository = repository;
    }

    @GetMapping("/all")
    public List<T> all() {
        return genericRepository.findAll();
    }

    @GetMapping("/byId")
    public ResponseEntity<T> byId(ID id) {
        Optional<T> obj = genericRepository.findById(id);
        if (obj.isPresent()) {
            return ResponseEntity.ok(obj.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public Page<T> list(@PageableDefault Pageable pageable) {
        return genericRepository.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/put")
    public ResponseEntity save(@RequestBody T obj) {
        T savedObject = genericRepository.save(obj);
        if (savedObject != null) {
            return ResponseEntity.ok(ResponseDTO.builder().content(savedObject).success(true).build());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody ID id) {
        Optional<T> deleteObject = genericRepository.findById(id);
        if (deleteObject.isPresent()) {
            genericRepository.delete(deleteObject.get());
            return ResponseEntity.ok(ResponseDTO.builder().success(true).build());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity countQuery() {
        return ResponseEntity.ok(ResponseDTO.builder().content(genericRepository.count()).build());
    }

    @GetMapping("/s")
    public Page searchQuery(@RequestParam String q, @PageableDefault Pageable pageable) {
        if (!this.getFilterFields().isEmpty()) {
            return this.getGenericRepository().findAll(GenericSpecification.query(filterFields, q), pageable);
        }
        return null;
    }


    public GenericRepository<T, ID> getGenericRepository() {
        return genericRepository;
    }

    public List<QueryCfg> getFilterFields() {
        return filterFields;
    }

    public String getLocalizedMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
