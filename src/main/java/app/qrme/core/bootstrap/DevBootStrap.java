package app.qrme.core.bootstrap;

import app.qrme.core.data.repository.*;
import app.qrme.core.entities.*;
import app.qrme.core.utils.constants.Constants;
import app.qrme.lib.data.entity.BaseUser;
import app.qrme.lib.data.entity.Role;
import app.qrme.lib.data.repo.BaseUserRepository;
import app.qrme.lib.data.repo.RoleRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ServerVariableRepository serverVariableRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostSectionRepository postSectionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private SliderCategoryRepository sliderCategoryRepository;

    @Autowired
    private SliderRepository sliderRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private QuickLinksRepository quickLinksRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ParamsRepository paramsRepository;

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BreakingNewsRepository breakingNewsRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    /**
     * Initialize Spring data
     */
    private void initData() {

        ServerVariable var = serverVariableRepository.findByServerKey("INIT_DATABASE");
        if (var == null) {
            ServerVariable key = ServerVariable.builder()
                    .serverKey("INIT_DATABASE")
                    .serverVal("true")
                    .build();
            serverVariableRepository.save(key);
            this.createUserAndRoles();
            this.createParams();
            this.createSection();
            this.createPostCategories();
            this.createPost();
            this.createPostSection();
        }

    }

    private void createPostSection() {
        List<Post> posts = postRepository.findAll();
        List<Section> sections = sectionRepository.findAll();
        sections.forEach(obj -> {
            posts.forEach(p-> {
                postSectionRepository.save(PostSection.builder().section(obj).post(p).build());
            });
        });
    }

    private void createPost() {
        BaseUser baseUser = baseUserRepository.findById(1L).get();
        PostCategory c1 = postCategoryRepository.findById(1L).get();
        PostCategory c2 = postCategoryRepository.findById(2L).get();
        PostCategory c3 = postCategoryRepository.findById(3L).get();

        List<Post> posts = new ArrayList<Post>(3);
        for (int i = 0; i < 50; i++) {
            posts.add(Post.builder()
                    .active(true)
                    .category(c1)
                    .showsDate(true)
                    .createdBy(baseUser)
                    .descr("<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                            "                                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                            "                                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                            "                                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                            "                                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                            "                                proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
                    .title("This is post title " + i)
                    .orderNum(i)
                    .numberOfViews(i * 255)
                    .imgUrls("[\n" +
                            "  \"https://jooinn.com/images/girl-162.jpg\",\n" +
                            "  \"https://getbg.net/upload/full/www.GetBg.net_Girls___Models_Red-haired_girl_in_the_street__photographer_George_Chernyad_ev_110577_.jpg\",\n" +
                            "  \"http://hdqwalls.com/wallpapers/lily-collins-celebrity-ad.jpg\",\n" +
                            "  \"http://sfwallpaper.com/images/1080p-girl-wallpaper-4.jpg\"\n" +
                            "]")
                    .build());

            posts.add(Post.builder()
                    .active(true)
                    .category(c2)
                    .showsDate(true)
                    .createdBy(baseUser)
                    .descr("<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                            "                                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                            "                                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                            "                                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                            "                                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                            "                                proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
                    .title("This is post title " + i + 1)
                    .orderNum(i + 1)
                    .numberOfViews(i * 255)
                    .imgUrls("[\n" +
                            "  \"https://images.pexels.com/photos/160673/girl-water-flowers-beauty-160673.jpeg?cs=srgb&dl=person-woman-water-160673.jpg&fm=jpg\",\n" +
                            "  \"https://timedotcom.files.wordpress.com/2016/06/gilmore-girls.jpg?quality=85\",\n" +
                            "  \"http://voorbladgesig.sarie.com/wp-content/uploads/2018/05/photo-1516795680264-ba1f8518c603.jpeg\",\n" +
                            "  \"http://ichef.bbci.co.uk/wwfeatures/wm/live/1280_640/images/live/p0/6d/xq/p06dxqmk.jpg\"\n" +
                            "]")
                    .build());
            posts.add(Post.builder()
                    .active(true)
                    .category(c3)
                    .showsDate(true)
                    .createdBy(baseUser)
                    .descr("<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n" +
                            "                                tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                            "                                quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                            "                                consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n" +
                            "                                cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n" +
                            "                                proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>")
                    .title("This is post title " + i + 2)
                    .orderNum(i + 2)
                    .numberOfViews(i * 255)
                    .imgUrls("[\n" +
                            "  \"http://www.stuja.pl/wp-content/uploads/2018/02/jak-poznac-dziewczyne.jpg\",\n" +
                            "  \"https://tamil.mirrorarts.lk/images/cute-pics-of-girls-018.jpg\",\n" +
                            "  \"https://www.girlscouts.org/content/dam/girlscouts-gsusa/images/content-hub/raising-girls/Catcall.png/_jcr_content/renditions/original\",\n" +
                            "  \"https://images7.alphacoders.com/423/thumb-1920-423936.jpg\"\n" +
                            "]")
                    .build());
        }

        postRepository.saveAll(posts);
    }

    private void createPostCategories() {
        BaseUser baseUser = baseUserRepository.findById(1L).get();
        List<PostCategory> categories = new ArrayList<>(7);
        {
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(0)
                    .title("სიახლეები")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(1)
                    .title("სკოლები")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(2)
                    .title("წიგნები")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(3)
                    .title("რჩევები")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(4)
                    .title("ინტერვიუ")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(5)
                    .title("ეტალონი")
                    .color("")
                    .build()
            );
            categories.add(PostCategory
                    .builder()
                    .active(true)
                    .createdBy(baseUser)
                    .imgUrl("")
                    .orderNum(6)
                    .title("კონკურსი")
                    .color("")
                    .build()
            );
        }
        postCategoryRepository.saveAll(categories);
    }

    private void createSection() {
        List<Section> sectionList = new ArrayList<>(12);
        {
            sectionList.add(Section.builder().title(Constants.Sections.Charitable_Foundation.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.news.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.promotions.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.cardSales.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.recipes.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.corporateSales.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.qualityMgt.name()).build());
            sectionList.add(Section.builder().title(Constants.Sections.adv.name()).build());
        }
        sectionRepository.saveAll(sectionList);
    }

    private void createParams() {
        paramsRepository.save(Params
                .builder()
                .gmailUrl("gmailUrlHere")
                .fbUrl("fbUrl")
                .youtubeUrl("youtubeUrl")
                .contactSkype("Skype")
                .contactPhone("Phone1")
                .contactPhone2("Phone2")
                .contactEmail("Email")
                .contactAddr("ContactAddr")
                .build()
        );
    }

    private void createUserAndRoles() {
        List<Role> roleList = new ArrayList<>(4);
        {
            Role role = Role.builder().name("SUPER_ADMIN").build();
            role.setId(1L);
            roleList.add(role);
        }
        roleRepository.saveAll(roleList);

        List<BaseUser> userList = new ArrayList<>(2);
        {
            Role adminRole = roleRepository.findByName("SUPER_ADMIN");
            Set<Role> userRole = new HashSet<>(1);
            {
                if (adminRole != null) {
                    userRole.add(adminRole);
                }
            }

            String hashPsw = passwordEncoder.encode("123456789");

            BaseUser baseUser = BaseUser.builder()
                    .username("mjaniko@gmail.com")
                    .firstName("Mikheil").lastName("Janiashvili")
                    .password(hashPsw)
                    .active(true)
                    .build();
            baseUser.setId(1L);
            userList.add(baseUser);
            baseUserRepository.saveAll(userList);
        }
    }

}
