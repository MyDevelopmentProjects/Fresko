package app.qrme.core.controller;

import app.qrme.core.data.repository.PostRepository;
import app.qrme.core.data.repository.PostSectionRepository;
import app.qrme.core.data.repository.PostTagRepository;
import app.qrme.core.entities.Post;
import app.qrme.core.entities.PostTag;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/post")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class PostController extends AbstractCRUDController<Post, Long> {

    public PostController(PostRepository repository) {
        super(repository);
    }

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    PostSectionRepository postSectionRepository;


    @GetMapping("/find")
    public Page<Post> find(@PageableDefault Pageable pageable,
                           @RequestParam(name = "q", defaultValue = "", required = false) String q,
                           @RequestParam(name = "categoryId", defaultValue = "", required = false) Long categoryId,
                           @RequestParam(required = false, defaultValue = "false") boolean showEnabled,
                           @RequestParam(required = false, defaultValue = "false") boolean showDisabled) {
        if (q != null && !q.equals("")) {
            return postRepository.findByTitleContainingOrDescrContaining(pageable, q, q);
        } else if (categoryId != null) {
            return postRepository.findAllByCategoryId(pageable, categoryId);
        }
        return super.list(pageable);
    }

    @RequestMapping(value = {"/removeMutl"}, method = RequestMethod.POST)
    public void deletePost(@RequestBody @NotNull @NotEmpty List<Long> ids) {
        ids.forEach(id-> {
            postSectionRepository.removeAllByPostId(id);
            postTagRepository.removeAllByPostId(id);
            postRepository.deleteById(id);
        });
    }

    @RequestMapping(value = {"/removePost"}, method = RequestMethod.POST)
    public void deletePost(@RequestBody Long id) {
        postSectionRepository.removeAllByPostId(id);
        postTagRepository.removeAllByPostId(id);
        postRepository.deleteById(id);
    }
}
