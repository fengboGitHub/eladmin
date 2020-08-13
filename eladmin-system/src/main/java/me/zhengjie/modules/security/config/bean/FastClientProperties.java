package me.zhengjie.modules.security.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fastdfs")
public class FastClientProperties {
    /**
     * 文件服务查看文件路劲
     */
    private String url;
    /**
     * 外网ip
     */
    private String ip;
    /**
     * 外网端口号
     */
    private String port;
}
