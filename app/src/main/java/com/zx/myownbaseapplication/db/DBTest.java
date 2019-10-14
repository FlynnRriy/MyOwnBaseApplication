package com.zx.myownbaseapplication.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.List;

public class DBTest {

    public void add(){
        UserData userData = new UserData();

        ModelAdapter<UserData> adapter = FlowManager.getModelAdapter(UserData.class);

//        userData.id = 1;//这一行必须加，必须指定id，后面才能根据id修改某一条的数据
        userData.name = "张三";
        userData.age = 99;
        userData.sex = true;


        boolean success = userData.save();
        // 执行到这里之后 id 已经被赋值

        adapter.insert(userData);//插入
//        adapter.delete(userData);//删除
//        adapter.update(userData);//修改
    }
    public void del(){
        UserData userData = new UserData();
        ModelAdapter<UserData> adapter = FlowManager.getModelAdapter(UserData.class);
        userData.id = 1;//这一行必须加，必须指定id，后面才能根据id修改某一条的数据
        userData.name = "张三";
        userData.age = 99;
        userData.sex = true;
        adapter.delete(userData);//删除

//=================================================================================
        //方法一  先查后删除
        UserData product = SQLite.select()
                .from(UserData.class)
                .where(UserData_Table.name.eq("yy"))
                .querySingle();
        if (product!=null){
            product.delete();
        }
        //方法二 直接删除
        SQLite.delete(UserData.class)
                .where(UserData_Table.name.eq("yy"))
                .execute();
    }
    public void update(){
        //修改的时候的代码
        UserData userData = new UserData();
        userData.id = 1;
        userData.name = "9999";
        userData.update();//只有继承了BaseModel才能用这个方法，否则调用这个方法adapter.update(userData);

        //再来点福利，update高级用法，增删改查都是同理，就不一一列举了
//        SQLite.update(UserData.class).set(UserData_Table.name.eq("888")).where(UserData_Table.id.eq(1)).execute();
        //UserData_Table就是DBFlow自动生成的表明，在(5)的备注中已经提到了


//=================================================================================
        //方法一 先查后改
        UserData product = SQLite.select()
                .from(UserData.class)
                .where(UserData_Table.name.eq("yy"))
                .querySingle();//区别于queryList(),返回的是实体
        if (product != null) {
            product.name = "yy1";
            product.update();
        }

        //方法二 直接修改
        SQLite.update(UserData.class)
                .set(UserData_Table.name.eq("yy1"))
                .where(UserData_Table.name.eq("yy"))
                .execute();

    }
    public void search(){
        //查询
        List<UserData> list = SQLite.select().from(UserData.class).queryList();



//=================================================================================
        //方法一
        List<UserData> products = SQLite.select()
                .from(UserData.class)
                .where()
                // .orderBy(Product_Table.id,true)//按照升序
                // .limit(5)//限制条数
                .queryList();//返回的list不为空，但是可能为empty
//        return products;

//        if (products != null) {
//            product.name = "P000X";
//            boolean success = product.update();
//            App.showToast(this, "修改结果：" + success);
//        } else {
//            App.showToast(this, "name=P0000的条件数据不存在：");
//        }

    }

    //查询单个
    public static UserData selectOne() {
        UserData product = SQLite.select()
                .from(UserData.class)
                .where(UserData_Table.name.eq("yy"))//条件
                .querySingle();//返回单个实体
        return product;
    }

}
