package com.mars.mysqldocfx.service;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FileSave {
    /**
     * 保存markdown 文档
     * @param db
     * @param text
     * @throws IOException
     */
    public static void  saveMarkdown(String path, String db,String text) throws IOException{
        if(StringUtils.isBlank(path)){
            path = "./";
        }
        path = path + System.getProperty("file.separator") + "MySqlDocFx";
        mkdirIfNotExist(path);
        FileWriter mdFile = new FileWriter(path + System.getProperty("file.separator") + db+".md");
        mdFile.write(text);
        mdFile.close();
        System.out.println("markdonw文档生成成功：" + path  + System.getProperty("file.separator") + db+".md");
    }
    /**
     * 判断目录是否存在，不存在则创建
     * @param path
     */
    public static void mkdirIfNotExist(String path) {
        File dirpath = new File(path);
        if (dirpath.exists()) {
        } else {
            dirpath.mkdir();
        }
    }
    /**
     * 保存Html文档
     * @param db
     * @param text
     */
    public  static  void  saveHtml(String path, String db,String text) throws IOException {
        if(StringUtils.isBlank(path)){
            path = "./";
        }
        path = path + System.getProperty("file.separator") + "MySqlDocFx";
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        // uncomment to set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.<Extension>asList(TablesExtension.create(), StrikethroughExtension.create()));


        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        // You can re-use parser and renderer instances
        Node document = parser.parse(text);
        String html = renderer.render(document);
        ClassLoader classLoader = options.getClass().getClassLoader();
        String css  = "";
        try {
            css = IOUtils.toString(classLoader.getResourceAsStream("markdown.css"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        css  = "<style type=\"text/css\">\n" + css + "\n</style>\n";
        html = css + html;
        mkdirIfNotExist(path);
        FileWriter mdFile = new FileWriter(path + System.getProperty("file.separator") + db+".html");
        mdFile.write(html);
        mdFile.close();
        System.out.println("html文档生成成功：" + path + System.getProperty("file.separator") + db +".html");
    }
}
