package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.bean.Product;
import com.example.myapplication.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
     public List <Product> productList;
    private OnItemClickListener listener;
    //点击product的回调
    public interface OnItemClickListener{
        void onItemClick(Product product);
    }

    //设置点击事件的监听器
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    public ProductAdapter(Context context,List<Product> productList){
        this.context=context;
        this.productList=productList;
    }
    //提供商品模板 创建VIewHolder
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rv_product,parent,false);
        return new ViewHolder(view);
    }
    //绑定商品数据
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product=productList.get(position);
        holder.tvTitle.setText(product.getTitle());
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(product.getPrice());
        if (product.getImage() != null) {
            Glide.with(context)
                    .load(product.getImage())
                    .into(holder.imageView);
        }
    }
    //需要生成的item数量
    @Override
    public int getItemCount() {
        return productList.size();
    }
//ViewHolder类
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvTitle,tvDescription,tvPrice;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_product_image);
            tvTitle=itemView.findViewById(R.id.tv_product_title);
            tvPrice=itemView.findViewById(R.id.tv_product_price);
            tvDescription=itemView.findViewById(R.id.tv_product_description);
            //设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Product product=productList.get(position);
                    listener.onItemClick(product);
                }
            });
        }
    }
}
