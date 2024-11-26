# How&gt;

好了在这里要说如何写的了。
是和AI一起写的，不过鉴于之前一起写过一个Android Studio 项目的经验来说，还是先去了解了一些。Java Web里面Spring Boot也算是了解一些，但是比较多还是Maven，一听Spring Boot不用配置Tomcat人都好了。

# Overview

说一下目录结构：
<code-block lang="plain text">
- firstclass/
    |__ controller/
        |__ LoginController.java
        |__ Off_LineController.java
        |__ PostController.java
        |__ UserController.java
    |__ dao/
        |__ PostDao.java
        |__ UserDao.java
    |__ entity/
        |__basicEntity/
            |__ Off_Line.java
            |__ Post_ci.java
            |__ User_x.java
        |__ DTO/
            |__ PostDTO.java
            |__ UserRegisterDTO.java
    |__ repository/
        |__ PostRepository.java
        |__ UserRepository.java
    |__ security/
        |__ PasswordEncoderConfig.java
        |__ SecurityConfig.java
    |__ service/
        |__ dataService/
            |__ PostService.java
            |__ Sto.java
            |__ User_Service.java
        |__ errorHandler/
            |__ GlobalException.java
            |__ ResoueceNotFoundException.java
    |__ util/
        |__ PasswordEncoderUtil.java
</code-block>


看起来很多但是其实没有那么多，所以实现的功能也不是很多。

准备从头说起，算是一种复习？其中还会插入一些关于spring boot或者java什么的回顾。


（OMG上一个Android Studio的项目的文章还没写好就又在这里写这个了……）

# 分层

其实对于MVC还算不上多了解，所以还是在结构上有一些混乱。

引用一张图片：

![MVC](https://pdai.tech/images/spring/springframework/spring-framework-helloworld-2.png)


- Entity： 数据实体层，可以理解为里面存放关于一个具体的数据的模型
- DAO：数据访问层 1.0版本，用于访问数据库，封装具体的数据库操作方法。
- DTO： 数据访问层 2.0版本，可以理解为对数据实体层进行操作的类，从Entity中提取模型，然后将具体数据注入形成可以访问的数据。
- Service： 业务逻辑层，可以理解为对Dao层进行操作的类，从Dao中提取数据，然后进行业务逻辑的处理。
- Controller： 控制层，可以理解为对Service层进行操作的类，从Service中提取数据，然后进行控制。

可以看到就是一层层从下往上封装的，和计算机网络的组成有着一样的构造思路，上层不必知道下层或者下下层如何实现，只需要想要的数据。

如果对DAO和DTO还是有疑惑，后面的具体code就可以很好的解答。

不过这个项目也就到这里了（顶多还有点RESTful API ），再往后的一些更多的涉及后端的数据封装什么的，还没有写。（因为不会）

具体开发知识可以参考：[Spring Boot 2.5x系列](https://pdai.tech/md/spring/springboot/springboot-x-interface-response.html)


## 数据层

简单粗暴的分层一下：

### Entity

这个项目里面有三个需要进行建立模型的
1. user
2. post
3. administer

原本想过administer和user共用一个。但没有。原因是，administer需要的数据比较少，并且我不爱按什么标准格式来进行命名，所以对比一下user和administer的：

>Administer Entity

```java
@Entity
@Data
@Table(name = "off_line")
public class Off_line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dean;

    @Column(nullable = false)
    private String system_off;

}
```


> User Entity

```java

@Entity
@Data
@Table(name = "user_x")
public class User_x {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatarData;

    private String avatarType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post_ci> posts;

    @Column(nullable = false)
    private String role;

    public String getRole() {
        if (this.username.equals("dean") || this.email.contains("dean")) {
            return "DEAN";
        }
        return "USER";
    }

}


```


其实差不多但是因为我对administer的具体变量的命名有其他想法。

后面如果想要进行调整，应该是将这两个合并一下。


最后是`post`的Entity

>Post Entity

```java

@Entity
@Data
@Table(name = "post_ci")
public class Post_ci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] attachmentData;

    private String attachmentName;
    private String attachmentType;

    @Column(nullable = false)
    private int attachmentCount = 0;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isPublic = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User_x user;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }


    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}



```


### DTO

对于这个用来处理数据与Entity与其他层的层，其实我也是这次写的时候才认识的。

>UserRegistrationDTO

用来处理注册的时候需要的数据的。

```java
@Data
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private MultipartFile avatar;
}

```

>PostDTO

```java
@Data
public class PostDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private Long authorId;
    private LocalDateTime createTime;
    private String attachmentName;
    private boolean isPublic;

    public boolean getIsPublic() {
        return isPublic;
    }
    
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
}
```
关于帖子的操作所需的一些数据以及一些封装方法。不止创建帖子的时候需要，后面涉及到帖子的展示、查找都有。


### DAO

和数据库的交互或者处理有关。

并且是`interface`类。

```java
// PostDao.java
public interface PostDao extends JpaRepository<Post_ci, Long> {

    List<Post_ci> findByUserOrderByCreateTimeDesc(User_x user);

    List<Post_ci> findAllByOrderByCreateTimeDesc();  

}

// UserDao.java
public interface UserDao extends JpaRepository<User_x, Long> {
    Optional<User_x> findByUsername(String username);
    boolean existsByUsername(String username);
}
```

都比较短，且对于方法都只是定义而不是具体实现。

和DAO相似的另外一种方式就是，**repository**

### reposiroty

>与DAO相比，区别在于，Repository更加简化和拥有内置的CRUD功能、自动生成查询的能力。DAO则需要手动实现。

我的respository中也用Spring Data JPA的`@Query`来实现对数据库进行的查询或者删除。

```java
@Repository
public interface UserRepository extends JpaRepository<User_x, Long> {
    Optional<User_x> findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}


@Repository
public interface PostRepository extends JpaRepository<Post_ci, Long> {
    List<Post_ci> findAllByOrderByCreateTimeDesc();

    List<Post_ci> findByUserId(Long userId);


    List<Post_ci> findByIsPublicTrueOrderByCreateTimeDesc();

    @Modifying
    @Transactional
    @Query("DELETE FROM Post_ci p WHERE p.user_id = :user_id")
    void deleteAllByUserId(@Param("user_id") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post_ci p WHERE p.id = :postId")
    void deletePostById(@Param("postId") Long postId);

    List<Post_ci> findByContentContainingIgnoreCase(String keyword);

    @org.springframework.data.jpa.repository.Query("SELECT p FROM Post_ci p WHERE LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post_ci> searchByKeyword(@Param("keyword") String keyword);
}
```

#### CRUD {collapsible="true"}
CRUD指的是基本的Create、Read、Update和Delete操作，通常CRUD方法是由JpaRepository或CrudRepository接口自动提供的，如save()、findById()、findAll()、deleteById()等。


## 服务层


### PostService

在这里实现的是，与`postRepository`的交互：
- 创建帖子：`createPost`
- 获取所有帖子: `getAllPosts`
- 获取帖子下的附件: `getAttachment`
- 获取用户的所有帖子: `getUserPosts`
- 转换为DTO数据: `convertToDTO`
- 获取所有公开贴子: `getAllPublicPosts`
- 通过关键词查找帖子: `searchPostsByKeyword`


```java

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post_ci createPost(String content, MultipartFile attachment, User_x user, Boolean isPublic) throws IOException {
        //对content的长度进行限制
        if (content.length() > 1000) {
            throw new RuntimeException("Content exceeds maximum length of 1000 characters");
        }
        //对帖子进行创建，方式就是创建一个Post_ci对象，然后设置其内容、用户、是否公开等属性，然后保存到数据库中。
        Post_ci post = new Post_ci();
        post.setContent(content);
        post.setUser(user);
        post.setPublic(isPublic);
        
        //对附件的一些处理
        if (attachment != null && !attachment.isEmpty()) {
            post.setAttachmentData(attachment.getBytes());
            post.setAttachmentName(attachment.getOriginalFilename());
            post.setAttachmentType(attachment.getContentType());
        }
        //最终保存到数据库中
        return postRepository.save(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreateTimeDesc()//通过时间倒叙排列
                .stream()//将返回得到的List<Post>转换为Stream<<Post>
                .map(this::convertToDTO) //结合上一步的stream()，接着用map()，引用convertToDTO方法，将Stream<Post>里面的每一个对象都进行转换（映射）
                .collect(Collectors.toList());//stream()的终结操作，将上一步得到的Stream<PostDTO>收集成List<PostDTO>返回
    }

    public byte[] getAttachment(Long postId) {
        Post_ci post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getAttachmentData();
    }

    public List<PostDTO> getUserPosts(Long userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post_ci post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setContent(post.getContent());
        dto.setAuthorUsername(post.getUser().getUsername());
        dto.setAuthorId(post.getUser().getId());
        dto.setCreateTime(post.getCreateTime());
        dto.setAttachmentName(post.getAttachmentName());
        dto.setIsPublic(post.isPublic());
        return dto;
    }



    public List<PostDTO> getPublicPosts() {
        List<Post_ci> publicPosts = postRepository.findByIsPublicTrueOrderByCreateTimeDesc();
        return publicPosts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<PostDTO> searchPostsByKeyword(String keyword) {
        List<Post_ci> posts = postRepository.searchByKeyword(keyword);
        return posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}


```


另外两个Service类也是一个道理。

值得单独记录的应该是

`User__Service`中在实现注册用户的功能的时候，对密码的加密处理、对文件类型的判断。


```java
@Slf4j
@Service
@Transactional
public class User_Service {
    
    public User_x registerUser(UserRegistrationDTO dto) throws IOException {
        MultipartFile avatarFile = dto.getAvatar();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            String contentType = avatarFile.getContentType();
            if (avatarFile.getSize() > MAX_AVATAR_SIZE) {
                throw new RuntimeException("Avatar file size exceeds 5MB limit");
            }

            assert contentType != null;
            if (isAllowedType(contentType)) {
                user.setAvatarData(avatarFile.getBytes());
                user.setAvatarType(contentType);
            } else {
                throw new RuntimeException("Invalid file type");
            }
        } else {
            // 默认avatar
            InputStream inputStream = defaultAvatar.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            user.setAvatarData(outputStream.toByteArray());
            user.setAvatarType("image/jpg");
            log.info("Default avatar used for user: {}", user.getUsername());
        }
        log.info("User registered successfully: {}", user.getUsername());
        return userRepository.save(user);

    }
    
    public boolean isAllowedType(String contentType) {
        return contentType.equals("image/jpeg") ||
                contentType.equals("image/png") ||
                contentType.equals("image/gif") ||
                contentType.equals("application/pdf");
    }
    

}

```


另一个就是对dean用户的判断，怎么说呢，感觉很不靠谱。

```java
    public boolean isDean(String name) {
        User_x user = findByUsername(name);
        return name.toLowerCase().equals("dean")
                || user.getEmail().toLowerCase().contains("dean")//???
                || user.getRole().equals("DEAN");
    }
```

前面通过姓名或者邮箱的判断都有点不靠谱，最后增加了一个role判断，但由于使用的是`||`，所有还是存在当满足前两者任何一个就会被当作管理员dean。

应该进行修改，要求必须同时满足三者。


## Controller

整个项目中，最难的居然不是怎么实现功能，而是怎么处理mapping……
哈哈哈哈……忽然想起来之前学servlet的时候，也是被这个mapping搞得头疼。