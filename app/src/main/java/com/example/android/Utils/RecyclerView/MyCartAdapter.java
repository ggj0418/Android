package com.example.android.Utils.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.DTOS.CartItemDTO;
import com.example.android.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.BasketViewHolder> {
    private final Context mContext;
    private final List<CartItemDTO> cartItemList;
    private final CartClickListener cartClickListener;

    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public MyCartAdapter(Context mContext, List<CartItemDTO> cartItemList, CartClickListener cartClickListener) {
        this.mContext = mContext;
        this.cartItemList = cartItemList;
        this.cartClickListener = cartClickListener;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.basket_items, parent, false);
        return new BasketViewHolder(view, cartClickListener);
    }

    // 여기가 실제로 값을 뷰에 적용시키는 단계
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        /*Glide
                .with(mContext)
                .load(basketItemList.get(position).getImgUrl())
                .placeholder(R.drawable.ic_sand_timer)  // 이미지가 로드되기 전에 보여줄 이미지
                .error(R.drawable.ic_error)        // 이미지 로드에 실패한 경우 보여줄 이미지
                .fallback(R.drawable.ic_error)     // 로드할 url이 null 인 경우 보여줄 이미지
                .into(holder.imgUrl);*/

        holder.name.setText(cartItemList.get(position).getProductName());
        holder.count.setText(String.valueOf(cartItemList.get(position).getCount()));
        holder.categoryName.setText(cartItemList.get(position).getCategoryName());

        String totalPrice = decimalFormat.format(cartItemList.get(position).getProductPrice() * cartItemList.get(position).getCount());
        holder.price.setText(totalPrice + "원");
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    // findViewById 쓰는 곳
    public static class BasketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //        protected ImageView imgUrl;
        protected TextView name;
        protected TextView categoryName;
        protected TextView count;
        //        protected TextView categoryAndLocation;
        protected TextView delete;
        protected TextView price;

        protected Button plus;
        protected Button minus;

        protected WeakReference<CartClickListener> listenerWeakReference;

        public BasketViewHolder(@NonNull View itemView, CartClickListener cartClickListener) {
            super(itemView);

            this.listenerWeakReference = new WeakReference<>(cartClickListener);

//            this.imgUrl = (ImageView) itemView.findViewById(R.id.basket_item_imageview);
            this.name = (TextView) itemView.findViewById(R.id.basket_item_name_textview);
            this.categoryName = (TextView) itemView.findViewById(R.id.basket_item_subname_textview);
            this.count = (TextView) itemView.findViewById(R.id.basket_item_count_textview);
//            this.categoryAndLocation = (TextView) itemView.findViewById(R.id.basket_item_subname_textview);
            this.price = (TextView) itemView.findViewById(R.id.basket_item_price_textview);

            this.delete = (TextView) itemView.findViewById(R.id.basket_item_delete_textview);
            this.plus = (Button) itemView.findViewById(R.id.basket_item_plus_button);
            this.minus = (Button) itemView.findViewById(R.id.basket_item_minus_button);

            this.itemView.setOnClickListener(this);
            this.delete.setOnClickListener(this);
            this.plus.setOnClickListener(this);
            this.minus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String value;

            if(view.getId() == this.delete.getId()) {
                value = "delete";
            } else if(view.getId() == this.plus.getId()) {
                value = "plus";
            } else if(view.getId() == this.minus.getId()) {
                value = "minus";
            } else {
                value = "item";
            }

            listenerWeakReference.get().onPositionClicked(getAdapterPosition(), value);
        }
    }
}