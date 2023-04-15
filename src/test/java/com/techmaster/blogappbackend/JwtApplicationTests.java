package com.techmaster.blogappbackend;



import com.github.slugify.Slugify;
import com.techmaster.blogappbackend.entity.*;
import com.techmaster.blogappbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class JwtApplicationTests {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;
    @Test
    void save_roles() {
        List<Role> roles = List.of(
                new Role(null, "ADMIN"),
                new Role(null, "USER"),
                new Role(null, "AUTHOR")
        );

        roleRepository.saveAll(roles);
    }

    @Test
    void save_users() {
        Role userRole = roleRepository.findByName("USER").orElse(null);
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        Role authorRole = roleRepository.findByName("AUTHOR").orElse(null);

        List<User> users = List.of(
                new User(null, "Bùi Hiên", "hien@gmail.com", passwordEncoder.encode("111"),null, List.of(adminRole, userRole)),
//                User.builder().name("Bùi Hiên").build(),
                new User(null, "Minh Duy", "duy@gmail.com", passwordEncoder.encode("111"),null, List.of(userRole)),
                new User(null, "Thu Hằng", "hang@gmail.com", passwordEncoder.encode("111"),null, List.of(authorRole, userRole))
        );

        userRepository.saveAll(users);
    }
    @Test
    void save_category(){
        List<Category> categories = List.of(
                new Category(null,"Backend"),
                new Category(null,"Frontend"),
                new Category(null,"DevOps"),
                new Category(null,"DataBase"),
                new Category(null,"Mobile")
        );
        categoryRepository.saveAll(categories);
    }
    @Test
    void save_blogs(){
//        List<Blog> blogs = List.of(
//          new Blog(null,"test blog")
//        );
        Slugify slugify = Slugify.builder().build();
        Random random = new Random();
        List<User> users = userRepository.findByRoles_NameIn(List.of("ADMIN","AUTHOR"));
        List<Category> categories = categoryRepository.findAll();
        for (int i = 0; i < 25; i++) {
            // Random 1 user
            User userRd = users.get(random.nextInt(users.size()));
            // Random 1 ds category tuong ung
            List<Category> categoriesRd = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Category category = categories.get(random.nextInt(categories.size()));
                if(!categoriesRd.contains(category)){
                    categoriesRd.add(category);                }
            }
            // tao blog
            blogRepository.save(Blog.builder()
                    .categories(categoriesRd)
                    .title("Blog "+(i+1))
                    .slug(slugify.slugify("Blog "+(i+1)))
                    .description("Description "+(i+1))
                    .content("Content "+(i+1))
                    .status(random.nextInt(2) == 0)
                    .user(userRd)
                    .build());
        }
    }
    @Test
    void save_comments() {
        Random rd = new Random();
        List<User> userList = userRepository.findAll();
        List<Blog> blogList = blogRepository.findAll();

        for (int i = 0; i < 100; i++) {
            // Random 1 user
            User rdUser = userList.get(rd.nextInt(userList.size()));

            // Random 1 blog
            Blog rdBlog = blogList.get(rd.nextInt(blogList.size()));

            // Tao comment
            Comment comment = Comment.builder()
                    .content("comment " + (i + 1))
                    .user(rdUser)
                    .blog(rdBlog)
                    .build();

            commentRepository.save(comment);
        }
    }
    @Test
    void testFindBlogContainingKey(){
        blogRepository.findByTitleContainingJPQL("Blog").forEach(System.out::println);
    }

}
