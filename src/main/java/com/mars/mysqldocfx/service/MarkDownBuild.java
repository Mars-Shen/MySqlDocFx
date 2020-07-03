package com.mars.mysqldocfx.service;

import java.util.HashMap;
import java.util.List;

public class MarkDownBuild {
    private String table_header="|字段名称|字段类型|默认值|是否为空|字段含义|\n|:---|:---:|:---:|:---:|:---|\n";
    public  String buildMarkdown(HashMap databaseAllInfo){
        String text="# 数据库文档\n\n";
        text+="<a name=\"返回顶部\"></a>\n\n## 数据表列表\n\n";
        List<HashMap> tablelists= (List<HashMap>) databaseAllInfo.get("tablelists");
        HashMap tableColumnlists = (HashMap) databaseAllInfo.get("tableColumnlists");
//        数据表列表
        for (HashMap table :tablelists) {
            text += "* [" + table.get("TABLE_NAME")+ "](#" + table.get("TABLE_NAME") +"_pointer)\n";
            text += table.get("TABLE_COMMENT") + "\n\n";
        }
        text += "\n\n## 数据表说明\n\n";
//        数据表说明
        int index_no = 1;
        for (HashMap table :tablelists) {
            text = text + "#### " + index_no++ +". [" + table.get("TABLE_NAME")+ "](#" + table.get("TABLE_NAME") +"_pointer) - ([↑返回顶部](#返回顶部))\n\n";
            String tableName= (String) table.get("TABLE_NAME");
            String tableCommnet= (String) table.get("TABLE_COMMENT");
            text = text + "<a name=\""+tableName+"_pointer\" ></a>\n\n";
            text = text + "* 说明: " +tableCommnet + "\n\n";
            text += table_header;
            List<HashMap> columns= (List<HashMap>) tableColumnlists.get(tableName);
            for (HashMap column:columns){
                String columnComment= (String) column.get("COLUMN_COMMENT");
                columnComment=columnComment.replaceAll("\r|\n","");

                text = text + "|" + column.get("COLUMN_NAME") + '|' +column.get("DATA_TYPE") + '|' +column.get("COLUMN_DEFAULT")+ '|' +column.get("IS_NULLABLE") + '|' +columnComment + "|";
                text += "\n";
            }

        }
//        System.out.println(text);
        return  text;


    }
}
