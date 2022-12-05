package xyz.lglg.maven.plugin;

/**
 * @author jay.li
 * @Title: DocumentMojo
 * @Package xyz.lglg.maven.plugin
 * @Description: 使用文档注释
 * @date 2022/12/2
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author jay
 * @goal document
 */
public class DocumentMojo extends CustomAbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("文档注释类型的插件");
    }
}
