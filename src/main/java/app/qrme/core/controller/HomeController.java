package app.qrme.core.controller;

import app.qrme.core.data.dto.CorporateSalesDTO;
import app.qrme.core.data.repository.*;
import app.qrme.core.data.specification.PostSectionSpecification;
import app.qrme.core.data.specification.SliderSpecification;
import app.qrme.core.entities.AboutUs;
import app.qrme.core.entities.Post;
import app.qrme.core.entities.PostSection;
import app.qrme.core.entities.QuickLinks;
import app.qrme.core.utils.MGLStringUtils;
import app.qrme.core.utils.constants.Constants;
import app.qrme.lib.thread.ThreadPoolManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private PostSectionRepository postSectionRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private QuickLinksRepository quickLinksRepository;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/detail/{text}-{id}"}, method = RequestMethod.GET)
    public String detail(Model model,
                         @PathVariable("text") String text,
                         @PathVariable(value = "id") Long id) {
        Optional<Post> p = postRepository.findById(id);
        if (p.isPresent()) {
            Post post = p.get();
            model.addAttribute("post", post);
            /*model.addAttribute("related", postRepository.findAll(
                    PostSectionSpecification.findRelated(post.getCategory().getId()),
                    PageRequest.of(0, 9, new Sort(Sort.Direction.DESC, "orderNum")
                            .and(new Sort(Sort.Direction.DESC, "timestamp.created"))
                    )
            ));
            model.addAttribute("recents", recentPosts());
            model.addAttribute("quicklinks", quickLinks());
            model.addAttribute("tags", postTagRepository.findAllByPostId(post.getId()));*/
            model.addAttribute("titles", menu());
            model.addAttribute("title", getLocalizedMessage("page.home.title"));
            return "detail";
        }
        return "404";
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("slider", sliderRepository.findAll());
        model.addAttribute("recents", recentPosts());
        model.addAttribute("quicklinks", quickLinks());
        model.addAttribute("titles", menu());
        model.addAttribute("slider", sliderRepository.findAll(SliderSpecification.enabled()));
        model.addAttribute("title", getLocalizedMessage("page.home.title"));
        return "index";
    }


    @RequestMapping(value = {"/news"}, method = RequestMethod.GET)
    public String news(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("news", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.news"));
        model.addAttribute("key", "news");
        return "generic";
    }

    @RequestMapping(value = {"/mediaAboutUs"}, method = RequestMethod.GET)
    public String mediaaboutus(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("mediaAboutUs", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.aboutus.media"));
        model.addAttribute("key", "mediaAboutUs");
        return "generic";
    }

    @RequestMapping(value = {"/promotions"}, method = RequestMethod.GET)
    public String promotions(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("promotions", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.promotions"));
        model.addAttribute("key", "promotions");
        return "generic";
    }

    @RequestMapping(value = {"/gallery"}, method = RequestMethod.GET)
    public String gallery(Model model) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.gallery"));
        return "gallery";
    }

    @RequestMapping(value = {"/gallery/{id}"}, method = RequestMethod.GET)
    public String galleryDetails(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.gallery"));
        model.addAttribute("obj", galleryRepository.findById(id).get());
        return "gallery";
    }

    @RequestMapping(value = {"/charitableFoundation"}, method = RequestMethod.GET)
    public String charitableFoundation(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("charitableFoundation", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.charitablefoundation"));


        Optional<AboutUs> p = aboutUsRepository.findById(11L);
        if (p.isPresent()) {
            AboutUs post = p.get();
            model.addAttribute("post", post);
        }

        model.addAttribute("key", "charitableFoundation");
        return "generic";
    }

    @RequestMapping(value = {"/career"}, method = RequestMethod.GET)
    public String career(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("career", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.career"));
        model.addAttribute("key", "career");
        return "generic";
    }

    @RequestMapping(value = {"/sales"}, method = RequestMethod.GET)
    public String sales(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("cardSales", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.sales"));
        model.addAttribute("key", "cardSales");
        return "generic";
    }

    @RequestMapping(value = {"/products"}, method = RequestMethod.GET)
    public String products(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("products", productsRepository.findAll(pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.products"));
        model.addAttribute("key", "products");
        return "products";
    }

    @RequestMapping(value = {"/advatfresco"}, method = RequestMethod.GET)
    public String advatfresco(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("advatfresco", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("advatfresco"));
        model.addAttribute("key", "advatfresco");
        return "generic";
    }

    @RequestMapping(value = {"/tenders"}, method = RequestMethod.GET)
    public String tenders(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("tenders", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.tenders"));
        model.addAttribute("key", "tenders");
        return "generic";
    }

    @RequestMapping(value = {"/recipes"}, method = RequestMethod.GET)
    public String recipes(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("recipes", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("receips"));
        model.addAttribute("key", "recipes");
        return "generic";
    }

    @RequestMapping(value = {"/bestEmpl"}, method = RequestMethod.GET)
    public String bestEmpl(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("news", getSectionPage("bestEmpl", pageable));
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("bestEmpl"));
        model.addAttribute("key", "bestEmpl");
        return "generic";
    }

    @RequestMapping(value = {"/corporateSales"}, method = RequestMethod.GET)
    public String corporateSales(Model model) {
        model.addAttribute("titles", menu());

        Optional<AboutUs> p = aboutUsRepository.findById(10L);
        if (p.isPresent()) {
            AboutUs post = p.get();
            model.addAttribute("post", post);
        }

        model.addAttribute("title", getLocalizedMessage("coprsales"));
        return "corpSales";
    }

    @RequestMapping(value = {"/qualityMgt"}, method = RequestMethod.GET)
    public String qualityMgt(Model model) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("qualityMgt"));
        model.addAttribute("key", "qualityMgt");
        return "qualityMgt";
    }

    @RequestMapping(value = {"/about-us/{text}-{id}"}, method = RequestMethod.GET)
    public String aboutUs(Model model,
                          @PathVariable("text") String text,
                          @PathVariable("id") Long id) {
        Optional<AboutUs> p = aboutUsRepository.findById(id);
        if (p.isPresent()) {
            AboutUs post = p.get();
            model.addAttribute("post", post);

            model.addAttribute("titles", menu());
            model.addAttribute("title", post.localizedTitle(LocaleContextHolder.getLocale().toString()));
            return "about-us";
        }
        return "404";
    }

    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String contact(Model model) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.contact"));
        return "contact";
    }

    @RequestMapping(value = {"/poll"}, method = RequestMethod.GET)
    public String poll(Model model) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", getLocalizedMessage("page.home.poll"));
        return "poll";
    }

    @RequestMapping(value = {"/hr"}, method = RequestMethod.GET)
    public String hr(Model model) {
        model.addAttribute("titles", menu());
        model.addAttribute("title", "HR");
        return "hr";
    }

    private Page<PostSection> getSectionPage(String section, Pageable pageable) {
        return postSectionRepository.findAll(
                PostSectionSpecification.findBySection(section),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        new Sort(Sort.Direction.DESC, "post.timestamp.created")
                )
        );
    }

    private Page<PostSection> getSectionPage(String section, int size) {
        return postSectionRepository.findAll(
                PostSectionSpecification.findBySection(section),
                PageRequest.of(0, size, new Sort(Sort.Direction.DESC, "post.orderNum")
                        .and(new Sort(Sort.Direction.DESC, "post.timestamp.created"))
                )
        );
    }

    private Map<String, PostSection> getSectionData(String section, int size) {
        Map<String, PostSection> map = new HashMap<>();
        Page<PostSection> postSection = getSectionPage(section, size);
        if (postSection.getSize() == size) {
            for (int i = 0; i < size; i++) {
                map.put("obj" + i, postSection.getContent().get(i));
            }
        }
        return map;
    }

    private Page<Post> recentPosts() {
        return postRepository.findAll(PageRequest.of(0, 4, new Sort(Sort.Direction.DESC, "id")));
    }

    private Page<QuickLinks> quickLinks() {
        return quickLinksRepository.findAll(PageRequest.of(0, 5, new Sort(Sort.Direction.DESC, "id")));
    }

    public String getLocalizedMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public Map<String, String> menu() {
        Map<String, String> menu = new HashMap<>();
        menu.put("page.home.title", getLocalizedMessage("page.home.title"));
        menu.put("page.home.career", getLocalizedMessage("page.home.career"));
        menu.put("page.home.charitablefoundation", getLocalizedMessage("page.home.charitablefoundation"));
        menu.put("page.home.aboutus", getLocalizedMessage("page.home.aboutus"));
        menu.put("page.home.gallery", getLocalizedMessage("page.home.gallery"));
        menu.put("page.home.news", getLocalizedMessage("page.home.news"));
        menu.put("page.home.poll", getLocalizedMessage("page.home.poll"));
        menu.put("page.home.contact", getLocalizedMessage("page.home.contact"));
        menu.put("page.home.tenders", getLocalizedMessage("page.home.tenders"));
        menu.put("footer.copyright", getLocalizedMessage("footer.copyright"));
        menu.put("footer.madeby", getLocalizedMessage("footer.madeby"));
        return menu;
    }

    @RequestMapping(
            value = {"/sendFrm", "/sendPoll", "/sendApp"},
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    @ResponseBody
    public ResponseEntity sendForm(HttpServletRequest request, @RequestBody CorporateSalesDTO body) {
        if (body.getBody() != null && !body.getBody().equals("")) {

            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

            if (pattern.contains("sendPoll")) {
                sendEmail("Corporate Sales", "mariam.apridonidze@fresco.ge", body.getBody());
            }

            if (pattern.contains("sendApp")) {
                sendEmail("Corporate Sales", "hr@fresco.ge", body.getBody());
            }

            if (pattern.contains("sendFrm")) {
                sendEmail("Corporate Sales", "corporate@fresco.ge", body.getBody());
            }

            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("err");
    }

    private void sendEmail(String subject, String to, String comment) {
        try {
            ThreadPoolManager.execute(new Runnable() {
                @Override
                public void run() {
                    mailSender.send(mimeMessage -> {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                        message.setFrom("no-reply@fresko.ge");
                        message.setTo(to); // send to user email
                        message.setSubject(subject);
                        message.setText(comment, true);
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
