package org.example;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.example.module.JsonModule;
import org.example.utils.CommonUtils;
import org.example.utils.PathUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterators;

@Mojo(name = "hanBLPackage")
public class MyMojo extends AbstractMojo {
    @Parameter( defaultValue = "${project.compileClasspathElements}", readonly = true, required = true )
    private List<String> compilePath;

    @Parameter( defaultValue = "${project.compileSourceRoots}", readonly = true, required = true )
    private List<String> compileSourceRoots;
    @Parameter
    private String applicationJsonName;




    @Override
    public void execute() throws MojoExecutionException {
        long startTime = System.currentTimeMillis();
        System.out.println(MessageFormat.format("自定义分模块打包插件开始执行：[{0}]", startTime));
        analysisJson(StringUtils.split(applicationJsonName,","));
    }

    private List<JsonModule> analysisJson(String[] applicationJsonNames){
        if (CommonUtils.isArrayEmpty(applicationJsonNames) || CommonUtils.isCollectionEmpty(compilePath)){
            return null;
        }
        List<JsonModule> jsonModuleList=new ArrayList<>();
        Arrays.stream(applicationJsonNames).forEach(p->{
            String jsonPath = PathUtils.buildJsonPath(compilePath, p);
            File jsonFile=new File(jsonPath);
            if (!jsonFile.exists()){
                System.out.println(MessageFormat.format("应用模块json文件未找到：[{0}]", jsonPath));
            }
            try {
                List<JsonModule> jsonModules = JSON.parseArray(new String(Files.readAllBytes(jsonFile.toPath()), "UTF-8"), JsonModule.class);
                jsonModuleList.addAll(jsonModules);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return jsonModuleList;
    }
}
