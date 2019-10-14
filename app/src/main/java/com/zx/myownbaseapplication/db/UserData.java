package com.zx.myownbaseapplication.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.structure.BaseModel;

import static com.zx.myownbaseapplication.db.AppDatabase.VERSION;

/**
 * 要继承BaseModel，这个类已经实现了增删改查的方法
 */

@Table(database = AppDatabase.class)
public class UserData extends BaseModel {
        //备注：DBFlow会根据你的类名自动生成一个表明，以此为例：
        //这个类对应的表名为：UserData_Table，这是作者在实践中得出来的

    @PrimaryKey(autoincrement = true)//ID自增
    public long id;

    /**
     * 姓名
     */
    @Column
    public String name;

    /**
     * 年龄
     */
    @Column
    public int age;

    /**
     * 性别
     */
    @Column
    public boolean sex;


    /**
     * 数据库的修改：
     * 1、PatientSession 表结构的变化
     * 2、增加表字段，考虑到版本兼容性，老版本不建议删除字段

    第一步
    public static final int VERSION = 2;
    第二部
    @Column
    public String content;//增加的字段
     */
    @Migration(version = VERSION, database = AppDatabase.class)
    public static class Migration2UserData extends AlterTableMigration<UserData> {

        public Migration2UserData(Class<UserData> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "content");
        }
    }

}
