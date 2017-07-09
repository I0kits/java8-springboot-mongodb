# microservice project template for java
This's a template for java microservice project.

#### Requirements
* [Docker](https://www.docker.com/)
* [docker-compose](https://github.com/docker/compose/releases)

### Instructions
##### Start service in dev mode
```
    docker-compose up -d
```
#### Stop service
```
    docker-compose stop
    # or
    docker-compose down
```
#### Check service status
```
  docker-compose ps
```
#### Run test
```
    #TODO....
```

# 后台框架说明

## 基本功能

测试框架-spock, mockmvc

缓存框架-ehcache，guava, redis

ORM-JPA

REST架构-hateaos

## spock
spock是用于java和groovy项目的单元测试框架，为什么使用此框架：

* spock框架使用标签分隔单元测试中不同的代码，更加规范，也符合实际写单元测试的思路

* 代码写起来更简洁、优雅、易于理解

* 由于使用groovy语言，所以也可以享受到脚本语言带来的便利

* 底层基于jUnit，不需要额外的运行框架


## mockmvc

mockmvc可以模拟客户端对被测试的controller调用http请求。为了对controller进行单元测试，一般还需要引入mockito来对repository层进行mock，从而屏蔽数据库层的影响。

```
MockMvc mockMvc

def setup() {
        expectedList = new ArrayList<User>()
        expectedList.add(new User(
                "World",
                18
        ))
        def mockRepo = mock(UserRepository.class)
        when(mockRepo.findByName("World")).thenReturn(expectedList)
        def controller = new DemoRestController(mockRepo)
        mockMvc = standaloneSetup(controller).build()
    }
    
def "should put list returned from repository"() {
        when:
        def response = mockMvc.perform(get("/users/by_name"))

        then:
        response.andExpect(status().isOk())

```

##ehcache

引入相关的jar包，在build.gradle文件中加入

```
compile('net.sf.ehcache:ehcache')

```

在resources文件夹下加入相应的配置文件ehcache.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<ehcache>
    <!--切换为ehcache 缓存时使用-->
    <cache name="people" maxElementsInMemory="1000" />
</ehcache>

```

在启动类上加入annotations

```
@EnableCaching

```

在需要进行cache的service中加入annotations

```
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    //@CachePut缓存新增的或更新的数据到缓存,其中缓存名字是 people 。数据的key是person的id
    @CachePut(value = "people", key = "#person.id")
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、key为:"+p.getId()+"数据做了缓存");
        return p;
    }

    @Override
    //@CacheEvict 从缓存people中删除key为id 的数据
    @CacheEvict(value = "people")
    public void remove(Long id) {
        System.out.println("删除了id、key为"+id+"的数据缓存");

    }

    @Override
    //@Cacheable缓存key为person 的id 数据到缓存people 中,如果没有指定key则方法参数作为key保存到缓存中。
    @Cacheable(value = "people", key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.findOne(person.getId());
        System.out.println("为id、key为:"+p.getId()+"数据做了缓存");
        return p;
    }
}

```

## guava

切换成guava cache只需要在build.gradle中引入guava cache的包即可

```
compile('com.google.guava:guava:18.0')

```
此时需要屏蔽其他的cache功能

## redis

在build.gradle中加入启动包

```
compile('org.springframework.boot:spring-boot-starter-redis:1.4.5.RELEASE')

```
redis cache 需要单独启动一个redis服务器才能正常工作。连接的参数需要在配置文件application.yml中加入

```
redis:
  hostname: localhost
  port: 6379

```

## JPA

工程中的数据模型映射层使用的是spring-data-jpa.它只需要添加一个继承MongoRepository的接口类，即能够完成基本的CRUD操作。对于其他特殊的复杂查询，也可以单独在借口中定义。

```
public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findByName(String username);
}

```

## hateaos
> HATEOAS（Hypermedia as the engine of application state）是 REST 架构风格中最复杂的约束，也是构建成熟 REST 服务的核心。它的重要性在于打破了客户端和服务器之间严格的契约，使得客户端可以更加智能和自适应，而 REST 服务本身的演化和更新也变得更加容易。


>对于不使用 HATEOAS 的 REST 服务，客户端和服务器的实现之间是紧密耦合的。客户端需要根据服务器提供的相关文档来了解所暴露的资源和对应的操作。当服务器发生了变化时，如修改了资源的 URI，客户端也需要进行相应的修改。而使用 HATEOAS 的 REST 服务中，客户端可以通过服务器提供的资源的表达来智能地发现可以执行的操作。当服务器发生了变化时，客户端并不需要做出修改，因为资源的 URI 和其他信息都是动态发现的

要实现此功能，需要使DTO中的实体类继承ResourceSupport。

```
public class Greeting extends ResourceSupport {
    private final String content;

    @JsonCreator
    public Greeting(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

```

controller中加入HATEOAS hyperlinks

```

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
    
```

### TODO
* [] 添加spring-boot's devtools 支持，for auto relaod..
* [] 添加TDD框架支持，(e.g. spock)
* [] 添加trace support (e.g. Zipkin)
* [] .....
