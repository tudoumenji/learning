mongo配置

```java
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * mongo配置类
 */
@Configuration
public class MongoTemplateConfig extends AbstractMongoConfiguration {
    /**
     * mongo uri地址
     */
    @Value("${spring.data.mongodb.uri}")
    private String mongoURI;
    /**
     * 数据库abc
     */
    private static final String MONGO_V6DB_NAME = "abc";
    /**
     * 数据库def
     */
    private static final String MONGO_DB_NAME = "def";

    /**
     * 返回mongo abc-ghi处理器
     *
     * @return mongo abc-ghi处理器
     */
    @Primary
    @Bean(autowire = Autowire.BY_NAME, value = "abc-ghi")
    public MongoTemplate getMongoV6Template() {
        MongoClientURI mongoClientURI =
                new MongoClientURI(mongoURI);
        MongoClient client = new MongoClient(mongoClientURI);
        return new MongoTemplate(client, MONGO_V6DB_NAME);
    }

    /**
     * 返回mongo def-ghi处理器
     *
     * @return mongo def-ghi处理器
     */
    @Bean(autowire = Autowire.BY_NAME, value = "def-ghi")
    public MongoTemplate getMongoTemplate() {
        MongoClientURI mongoClientURI =
                new MongoClientURI(mongoURI);
        MongoClient client = new MongoClient(mongoClientURI);
        return new MongoTemplate(client, MONGO_DB_NAME);
    }

    /**
     * 返回mongo fs abc-ghi处理器
     *
     * @return 返回mongo fs abc-ghi处理器
     * @throws Exception Exception
     */
    @Bean(autowire = Autowire.BY_NAME, value = "abc-fs")
    public GridFsTemplate getGridFsV6Template() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI + "/" + MONGO_V6DB_NAME);
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
        return new GridFsTemplate(simpleMongoDbFactory, mappingMongoConverter());
    }

    /**
     * 返回mongo fs def-ghi处理器
     *
     * @return 返回mongo fs def-ghi处理器
     * @throws Exception Exception
     */
    @Bean(autowire = Autowire.BY_NAME, value = "def-fs")
    public GridFsTemplate getGridFsTemplate() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoURI + "/" + MONGO_DB_NAME);

        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClientURI);
        return new GridFsTemplate(simpleMongoDbFactory, mappingMongoConverter());
    }

    /**
     * 返回一个非空字符串
     *
     * @return 返回一个非空字符串
     */
    @Override
    protected String getDatabaseName() {
        return "database";
    }

    /**
     * 返回Mongo
     *
     * @return Mongo
     * @throws Exception Exception
     */
    @Override
    public Mongo mongo() throws Exception {
        return new Mongo();
    }
}

```



请求：



```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * mongo数据查询，供外部接口调用
 */
@Api(value = "testnow/v1/upoladStrategy")
@RestController
@RequestMapping("/testnow/v1/upoladStrategy")
public class UpoladStrategyController {
    /**
     * log记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpoladStrategyController.class);
    /**
     * mongo elephant-deploy-v6-configmodify处理器
     */
    @Resource(name = "elephant-deploy-v6-configmodify")
    private MongoTemplate mongoV6Template;
    /**
     * mongo elephant-deploy-configmodify处理器
     */
    @Resource(name = "elephant-deploy-configmodify")
    private MongoTemplate mongoTemplate;
    /**
     * mongo gridfs  elephant-deploy-v6-fs处理器
     */
    @Resource(name = "elephant-deploy-v6-fs")
    private GridFsTemplate gridFsV6Template;
    /**
     * mongo gridfs  elephant-deploy-fs处理器
     */
    @Resource(name = "elephant-deploy-fs")
    private GridFsTemplate gridFsTemplate;
    /**
     * json转换器
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通过modifiedId获取ConfigModifyInfo
     *
     * @param modifiedId modifiedId
     * @return 接口数据
     */
    @ApiOperation(value = "获取ConfigModify信息", notes = "获取ConfigModify信息")
    @GetMapping("/modifiedId/{modifiedId}")
    public ResponseEntity<HashMap<String, String>> getConfigModifyInfo(
            @ApiParam(value = "modifiedId", required = true) @PathVariable("modifiedId") String modifiedId,
            @ApiParam(value = "userName", required = false)
            @RequestHeader(value = "userName", required = false) String userName,
            @ApiParam(value = "projectId", required = false)
            @RequestHeader(value = "projectId", required = false) String projectId,
            @ApiParam(value = "requestId", required = false)
            @RequestHeader(value = "requestId", required = false) String requestId) {
        //返回数据
        HashMap<String, String> responseHashMap = new HashMap<>();
        try {
            String responseData = "";
            //处理查询
            Query query = new Query(Criteria.where("modifiedid").is(modifiedId));
            //先查询elephant-deploy-v6，若未查询到，再查询elephant-deploy
            Map mapBymodifiedId = Optional.ofNullable(mongoV6Template.findOne(query, Map.class, "configmodify"))
                    .orElse(mongoTemplate.findOne(query, Map.class, "configmodify"));
            //若数据非空，则返回；若数据空，则返回空字符串
            if (!Objects.isNull(mapBymodifiedId)) {
                responseData = objectMapper.writeValueAsString(mapBymodifiedId);
            }
            responseHashMap.put("status", "200");
            responseHashMap.put("message", "success");
            responseHashMap.put("data", responseData);
            return ResponseEntity.ok(responseHashMap);
        } catch (Exception e) {
            //错误处理
            LOGGER.error("sent configModifyInfo by modifiedId failed", e);
            responseHashMap.put("status", "500");
            responseHashMap.put("message", "failed");
            return ResponseEntity.ok(responseHashMap);
        }
    }

    /**
     * 通过fileId查找文件
     *
     * @param fileId              fileId
     * @param httpServletResponse httpServletResponse
     */
    @ApiOperation(value = "获取文件信息", notes = "获取文件信息")
    @GetMapping("/fileId/{fileId}")
    public void getFileInfoByFileId(
            @ApiParam(value = "fileId", required = true) @PathVariable("fileId") String fileId,
            @ApiParam(value = "userName", required = false)
            @RequestHeader(value = "userName", required = false) String userName,
            @ApiParam(value = "projectId", required = false)
            @RequestHeader(value = "projectId", required = false) String projectId,
            @ApiParam(value = "requestId", required = false)
            @RequestHeader(value = "requestId", required = false) String requestId,
            HttpServletResponse httpServletResponse) {
        try {
            //处理查询
            Query query = new Query(Criteria.where("_id").is(fileId));
            //先查询elephant-deploy-v6，若未查询到，再查询elephant-deploy
            GridFSDBFile gridFSDBFile =
                    Optional.ofNullable(gridFsV6Template.findOne(query)).orElse(gridFsTemplate.findOne(query));
            //若未查到数据，则返回空流
            if (Objects.isNull(gridFSDBFile)) {
                httpServletResponse.getOutputStream().write("".getBytes("UTF-8"));
                return;
            }
            gridFSDBFile.writeTo(httpServletResponse.getOutputStream());
        } catch (Exception e) {
            //错误处理
            try {
                httpServletResponse.getOutputStream().write("".getBytes("UTF-8"));
            } catch (Exception ex) {
                LOGGER.error("sent file outputStream By fileId failed", ex);
            }
            LOGGER.error("sent file outputStream By fileId failed", e);
        }
    }
}

```

2121

