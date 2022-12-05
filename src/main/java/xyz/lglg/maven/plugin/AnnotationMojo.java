package xyz.lglg.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author jay.li
 * @Title: AnnotationMojo
 * @Package xyz.lglg.maven.plugin
 * @Description: 使用注解
 * @date 2022/12/2
 */
@Mojo(name = "annotation")
public class AnnotationMojo extends CustomAbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("注解类型的插件");
    }
}
