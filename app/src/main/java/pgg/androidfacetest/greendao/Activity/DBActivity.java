package pgg.androidfacetest.greendao.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.greendao.gen.UserDao;

import butterknife.Bind;
import butterknife.ButterKnife;
import pgg.androidfacetest.R;
import pgg.androidfacetest.greendao.Entity.User;
import pgg.androidfacetest.greendao.utils.EntityManager;
import pgg.androidfacetest.greendao.utils.MyApplication;

/**
 * Created by PDD on 2018/3/5.
 */

public class DBActivity extends AppCompatActivity {

    @Bind(R.id.tv_id)
    TextView tv_id;
    @Bind(R.id.tv_name)
    TextView tv_name;
    private UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
        userDao = EntityManager.getManager().getUserDao();

        insertData();
        queryData();
        deleteData();
        updateData();
    }
    //增加
    private void insertData() {
        userDao.insert(new User("001","pgg"));
    }
    //删除
    private void deleteData() {
        User pgg = userDao.queryBuilder().where(UserDao.Properties.Name.eq("pgg")).build().unique();
        if (pgg!=null){
            userDao.deleteByKey(pgg.getId());
        }
    }

    //更新
    private void updateData() {
        User pgg = userDao.queryBuilder().where(UserDao.Properties.Name.eq("pgg")).build().unique();
        if (pgg!=null){
            pgg.setName("pggpg");
            userDao.update(pgg);
            Toast.makeText(MyApplication.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MyApplication.getContext(), "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    //查找
    private void queryData() {
        User load = userDao.load("001");
        tv_id.setText("id="+load.getId());
        tv_name.setText("name="+load.getName());
    }


}
