package app.qrme.core.controller;

import app.qrme.core.data.repository.PostRepository;
import app.qrme.core.data.repository.PostSectionRepository;
import app.qrme.core.data.repository.SectionRepository;
import app.qrme.core.entities.Post;
import app.qrme.core.entities.PostSection;
import app.qrme.lib.controller.AbstractCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postSection")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
public class PostSectionController extends AbstractCRUDController<PostSection, Long> {

    @Autowired
    PostSectionRepository postSectionRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    SectionRepository sectionRepository;

    public PostSectionController(PostSectionRepository repository) {
        super(repository);
    }

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findAllByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postSectionRepository.findAllByPostId(postId));
    }

    @RequestMapping(value = "/savePS", method = RequestMethod.POST)
    @ResponseBody
    public void removeAllByPostId(@RequestBody List<Long> ids, @RequestParam(name = "postId") Long postId) {
        if (ids != null && ids.size() > 0) {
            Optional<Post> post = postRepository.findById(postId);
            if (post.isPresent()) {
                postSectionRepository.removeAllByPostId(postId);
                List<PostSection> postSections = new ArrayList<PostSection>();
                ids.forEach(id-> postSections.add(PostSection.builder()
                        .post(post.get())
                        .section(sectionRepository.getOne(id))
                        .build()));
                postSectionRepository.saveAll(postSections);
            }

        }
    }
}
