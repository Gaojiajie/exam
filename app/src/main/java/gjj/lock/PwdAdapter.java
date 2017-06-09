package gjj.lock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class PwdAdapter extends BaseAdapter {
    private List<Pwd> pwds;
    private Context context;
    public PwdAdapter(Context context, List<Pwd> students) {
        super();
        this.pwds=students;
        this.context=context;
    }

    @Override
    public int getCount() {
        return pwds.size();
    }

    @Override
    public Object getItem(int i) {
        return pwds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
        }
        ImageView imageView= (ImageView) view.findViewById(R.id.image);
        TextView tvName= (TextView) view.findViewById(R.id.name);
        TextView tvUserName= (TextView) view.findViewById(R.id.userName);
        TextView tvPassword= (TextView) view.findViewById(R.id.password);

        //随机为学生匹配头像
        if(pwds.get(i).getId()%2==0)
        {
            imageView.setImageResource(R.mipmap.girl1);
        }else{
            imageView.setImageResource(R.mipmap.boy2);
        }
        tvName.setText("站    点:"+pwds.get(i).getName());
        tvUserName.setText("用户名:"+pwds.get(i).getUserName());
        tvPassword.setText("密    码:" + pwds.get(i).getPassword());
        return view;
    }
}
