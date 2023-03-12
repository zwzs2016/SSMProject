package com.bamboo.util.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

import java.util.Arrays;
import java.util.List;

public class LongModTableNameHandler implements TableNameHandler {
    //用于记录哪些表可以使用该月份动态表名处理器（即哪些表按月分表）
    private List<String> tableNames;
    //构造函数，构造动态表名处理器的时候，传递tableNames参数
    public LongModTableNameHandler(String ...tableNames) {
        this.tableNames = Arrays.asList(tableNames);
    }

    //每个请求线程维护一个数据，避免多线程数据冲突。所以使用ThreadLocal
    private static final ThreadLocal<Long> LONG_DATA = new ThreadLocal<>();
    //设置请求线程的数据
    public static void setData(Long id) {
        LONG_DATA.set(id);
    }
    //删除当前请求线程的month数据
    public static void removeData() {
        LONG_DATA.remove();
    }


    @Override
    public String dynamicTableName(String sql, String tableName) {
        if (this.tableNames.contains(tableName)){
            String tail=String.valueOf(LONG_DATA.get()%2+1);
            return tableName  + tail;  //表名增加后缀 按照字段取模
        }else{
            return tableName;   //表名原样返回
        }
    }
}
