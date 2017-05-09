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

public abstract class   GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<Map<String,String>> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public abstract void onClickItem(int index);
    public GridAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.grid_item, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.listview_footer, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).initItem(list.get(position),position);
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size()+1;
    }

    class ItemViewHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.grid_img)
        ImageView img;
        @BindView(R.id.grid_name)
        TextView name;
        @BindView(R.id.grid_price)
        TextView price;
        @OnClick(R.id.grid_layout)
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
            name.setText(map.get("name"));
            price.setText(context.getString(R.string.found_price).replace("*",map.get("price")));

        }
    }
    class FootViewHolder extends  RecyclerView.ViewHolder{
        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
