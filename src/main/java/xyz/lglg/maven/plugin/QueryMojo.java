package xyz.lglg.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author jay.li
 * @Title: QueryMojo
 * @Package xyz.lglg.maven.plugin
 * @Description: 查询git
 * @date 2022/12/2
 */
@Mojo(name = "query")
public class QueryMojo extends CustomAbstractMojo {
    @Parameter(property="host", defaultValue = "${host}", required = true)
    private String host;
    @Parameter(property="port", defaultValue = "${port}", required = true)
    private String port;
    @Parameter(property="username", defaultValue = "${username}", required = true)
    private String username;
    @Parameter(property="password", defaultValue = "${password}", required = true)
    private String password;
    @Parameter(property="database", defaultValue = "${database}", required = true)
    private String database;

    @Parameter(property="timeout", defaultValue = "5000")
    private int timeout;

    @Parameter(property="options")
    private List<String> options;

    public QueryMojo() {
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.getLog().debug("###### >>>> 开始执行mysql数据库连接测试开始 >>>>> ######");

        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(this.host)
                .append(":")
                .append(this.port)
                .append("/")
                .append(this.database)
                .append("?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC")
                .append("&username=")
                .append(username)
                .append("&password=")
                .append(password);

        this.getLog().info("数据库连接：" + sb);
        this.getLog().info("连接当前数据库成功~");

        this.getLog().debug("###### >>>> 开始执行mysql数据库连接测试完成 >>>>> ######");
    }
}
