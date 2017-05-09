package com.app.tychodemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tychodemo.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xingjikang on 2017/3/27.
 */

public abstract class HomeGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String,String>> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public abstract void onClickItem(int index);
    public HomeGridAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.home_grid_item, parent,
                    false);
            return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).initItem(list.get(position),position);
        }

    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ItemViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.home_grid_img)
        ImageView img;
        @BindView(R.id.home_grid_name)
        TextView home_grid_name;
        @OnClick(R.id.home_grid_layout)
       public void onClick(){
            onClickItem(index);
        }
        private  int index=0;
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void initItem(Map<String,String> map,int position){
            index=position;
            img.setImageResource(Integer.parseInt(map.get("img")));
            home_grid_name.setText(map.get("name"));


        }
    }
}
