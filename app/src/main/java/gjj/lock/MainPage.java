package gjj.lock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends Activity implements View.OnClickListener{

    private ListView pwds;
    private PwdAdapter adapter;
    private Button btnAdd,btnSearch;

    private DatabaseHandler dbHandler;
    private List<Pwd> pwdList;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        pwds= (ListView) findViewById(R.id.Pwd_list);
        btnAdd= (Button) findViewById(R.id.btn_add);
        btnSearch= (Button) findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(this);
        btnAdd.setOnClickListener(this);


        dbHandler=new DatabaseHandler(this);

        //获取全部学生信息
        pwdList=dbHandler.getALllPwd();
        adapter=new PwdAdapter(this,pwdList);
        pwds.setAdapter(adapter);

        //点击ListView item跳转到详细界面
        pwds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainPage.this,PwdActivity.class);

                //注意这里的request是为了区分是通过什么跳转到详细界面的
                intent.putExtra("request","Look");
                intent.putExtra("id",pwdList.get(i).getId());
                intent.putExtra("name",pwdList.get(i).getName());
                intent.putExtra("userName",pwdList.get(i).getUserName());
                intent.putExtra("password",pwdList.get(i).getPassword());
                //
                startActivityForResult(intent, 0);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                Intent i=new Intent(MainPage.this,PwdActivity.class);

                i.putExtra("request","Add");
                startActivityForResult(i, 1);
                break;
            case R.id.btn_search:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);

                //自定义View的Dialog
                final LinearLayout searchView= (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_search,null);
                builder.setView(searchView);
                final AlertDialog dialog=builder.create();
                dialog.show();

                //为自定义View的Dialog的控件添加事件监听。
                final EditText searchName= (EditText) searchView.findViewById(R.id.search_name);
                Button btnDialogSearch= (Button) searchView.findViewById(R.id.btn_search_dialog);
                Button btnClose = (Button) searchView.findViewById(R.id.btn_close);
                btnDialogSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchName.setVisibility(View.GONE);
                        ListView list = (ListView) searchView.findViewById(R.id.search_result);
                        List<Pwd> resultList = new ArrayList<Pwd>();
                        final Pwd searchPwd = dbHandler.getPwd(searchName.getText().toString());
                        if (searchPwd != null) {
                            resultList.add(searchPwd);
                            PwdAdapter resultAdapter = new PwdAdapter(getApplicationContext(), resultList);
                            list.setAdapter(resultAdapter);
                            list.setVisibility(View.VISIBLE);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(MainPage.this, PwdActivity.class);
                                    intent.putExtra("request", "Look");
                                    intent.putExtra("id", searchPwd.getId());
                                    intent.putExtra("name", searchPwd.getName());
                                    intent.putExtra("userName", searchPwd.getUserName());
                                    intent.putExtra("password", searchPwd.getPassword());
                                    startActivityForResult(intent, 0);
                                }
                            });
                        } else {
                            //关闭Dialog
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "无此密码项", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //根据返回的resultCode判断是通过哪种操作返回的，并提示相关信息；
        switch (requestCode){
            case 0:
                if (resultCode==2)
                    Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                if (resultCode==3)
                    Toast.makeText(this,"已删除",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                if (resultCode==RESULT_OK)
                    Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
        }
        /**
         * 如果这里仅仅使用adapter.notifyDataSetChanged()是不会刷新界面ListView的，
         * 因为此时adapter中传入的studentList并没有给刷新，即adapter也没有被刷新，所以你可以
         * 重新获取studentList后再改变adapter，我这里通过调用onCreate()重新刷新了整个界面
         */

//        studentList=dbHandler.getALllStudent();
//        adapter=new PwdAdapter(this,studentList);
//        students.setAdapter(adapter);
        onCreate(null);
    }
}
