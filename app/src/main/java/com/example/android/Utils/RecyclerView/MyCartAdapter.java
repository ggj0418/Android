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

import java.text.DecimalFormat;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.BasketViewHolder> {
    private final Context mContext;
    private final List<CartItemDTO> cartItemList;
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public MyCartAdapter(Context mContext, List<CartItemDTO> cartItemList) {
        this.mContext = mContext;
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.basket_items, parent, false);
        return new BasketViewHolder(view);
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
    public static class BasketViewHolder extends RecyclerView.ViewHolder {
//        protected ImageView imgUrl;
        protected TextView name;
        protected TextView categoryName;
        protected TextView count;
//        protected TextView categoryAndLocation;
        protected TextView delete;
        protected TextView price;

        protected Button plus;
        protected Button minus;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);

//            this.imgUrl = (ImageView) itemView.findViewById(R.id.basket_item_imageview);
            this.name = (TextView) itemView.findViewById(R.id.basket_item_name_textview);
            this.categoryName = (TextView) itemView.findViewById(R.id.basket_item_subname_textview);
            this.count = (TextView) itemView.findViewById(R.id.basket_item_count_textview);
//            this.categoryAndLocation = (TextView) itemView.findViewById(R.id.basket_item_subname_textview);
            this.price = (TextView) itemView.findViewById(R.id.basket_item_price_textview);

            this.delete = (TextView) itemView.findViewById(R.id.basket_item_delete_textview);
            this.plus = (Button) itemView.findViewById(R.id.basket_item_plus_button);
            this.minus = (Button) itemView.findViewById(R.id.basket_item_minus_button);

            this.plus.setOnClickListener(view -> Log.d("MyBasketAdapter", "상품 개수가 1개 추가되었습니다"));
            this.minus.setOnClickListener(view -> Log.d("MyBasketAdapter", "상품 개수가 1개 감소하였습니다"));
            this.delete.setOnClickListener(view -> Log.d("MyBasketAdapter", "해당 상품이 제외되었습니다"));
        }
    }
}