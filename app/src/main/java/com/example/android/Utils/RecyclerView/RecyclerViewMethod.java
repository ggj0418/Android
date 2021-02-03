package com.example.android.Utils.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Activites.Barcode.QRCodeActivity;
import com.example.android.DTOS.CartItemDTO;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Preference.PreferenceManager;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class RecyclerViewMethod {
    private final Context mContext;
    private final List<CartItemDTO> cartItemList;
    private final MyCartAdapter myCartAdapter;
    private final Services retrofitAPI2;

    public RecyclerViewMethod(Context mContext, List<CartItemDTO> cartItemList, MyCartAdapter myCartAdapter) {
        this.mContext = mContext;
        this.cartItemList = cartItemList;
        this.myCartAdapter = myCartAdapter;
        retrofitAPI2 = RetrofitClient.getRetrofit(PreferenceManager.getString(mContext, "accessToken")).create(Services.class);
    }

    @SuppressLint("SetTextI18n")
    public void setTextView(TextView textView, int wholeCount) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String totalPrice = decimalFormat.format(wholeCount);
        textView.setText(totalPrice + " 원");
    }

    public int getWholeCount() {
        int wholeCount = 0;
        for(CartItemDTO cartItemDTO : cartItemList) {
            wholeCount += (cartItemDTO.getCount() * cartItemDTO.getProductPrice());
        }

        return wholeCount;
    }

    public void showCartList() {
        Call<List<CartItemDTO>> getCartListCall = retrofitAPI2.getCartList();
        getCartListCall.enqueue(new Callback<List<CartItemDTO>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<CartItemDTO>> call, Response<List<CartItemDTO>> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(mContext, "장바구니 리스트업 성공", Toast.LENGTH_SHORT).show();
                        cartItemList.clear();
                        cartItemList.addAll(Objects.requireNonNull(response.body()));
                        setTextView(((QRCodeActivity) mContext).wholeCountTextView, getWholeCount());
                        myCartAdapter.notifyDataSetChanged();
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(mContext, "해당 유저의 장바구니가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<CartItemDTO>> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setCartItem(int productCode) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("productCode", productCode);

        Call<ResponseBody> setCartItemCall = retrofitAPI2.setCartItem(map);
        setCartItemCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        Toast.makeText(mContext, "정상적으로 장바구니에 상품이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        showCartList();
                        break;
                    case 400:
                        Toast.makeText(mContext, "유효한 입력이 아닙니다. 혹은 재고 부족으로 인해 상품을 담을 수 없습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(mContext, "해당 제품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteCartItem(int productNo) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("productNo", productNo);

        Call<ResponseBody> setCartItemCall = retrofitAPI2.deleteCartItem(map);
        setCartItemCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                        break;
                    case 204:
                        Toast.makeText(mContext, "정상적으로 장바구니의 상품이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        showCartList();
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(mContext, "해당 제품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addCartItemCount(int productNo) {
        Call<ResponseBody> addCartItemCountCall = retrofitAPI2.addCartItemCount(productNo);
        addCartItemCountCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        Toast.makeText(mContext, "정상적으로 장바구니의 상품 개수가 증가되었습니다.", Toast.LENGTH_SHORT).show();
                        showCartList();
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(mContext, "해당 제품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void subCartItemCount(int productNo) {
        Call<ResponseBody> subCartItemCountCall = retrofitAPI2.subCartItemCount(productNo);
        subCartItemCountCall.enqueue(new Callback<ResponseBody>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
                        break;
                    case 201:
                        Toast.makeText(mContext, "정상적으로 장바구니의 상품 개수가 감소되었습니다.", Toast.LENGTH_SHORT).show();
                        showCartList();
                        break;
                    case 401:
                        Toast.makeText(mContext, "토큰 만료", Toast.LENGTH_SHORT).show();
                        break;
                    case 403:
                        Toast.makeText(mContext, "유저만 접근 가능", Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(mContext, "해당 제품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(mContext, "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "통신 에러입니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
