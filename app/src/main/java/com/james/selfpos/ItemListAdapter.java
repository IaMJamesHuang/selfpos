package com.james.selfpos;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.james.possdk.imageloader.ImageLoaderManager;
import com.james.selfpos.model.BuyGoodModel;
import com.james.selfpos.model.CommonItemModel;

import java.util.List;

/**
 * Created by James on 2018/10/14.
 */
public class ItemListAdapter extends BaseAdapter {

    private List<BuyGoodModel> mList;
    private Context mContext;
    private OnPriceChangeListener listener;

    public ItemListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(List<BuyGoodModel> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setListener(OnPriceChangeListener listener) {
        this.listener = listener;
    }

    public int  getTotal() {
        int sum = 0;
        for (BuyGoodModel goodModel : mList) {
            int price = goodModel.getItem().getPrice();
            sum += price * goodModel.getNum();
        }
        return sum;
    }

    public void addItem(CommonItemModel model) {
        if (model == null) {
            return;
        }
        String itemCode = model.getItemCode();
        boolean isFind = false;
        for (BuyGoodModel goodModel : mList) {
            if (itemCode.equals(goodModel.getItem().getItemCode())) {
                goodModel.setNum(goodModel.getNum() + 1);
                isFind = true;
                break;
            }
        }
        if (!isFind) {
            BuyGoodModel buyGoodModel = new BuyGoodModel();
            buyGoodModel.setItem(model);
            buyGoodModel.setNum(1);
            mList.add(buyGoodModel);
        }
        notifyDataSetChanged();
        if (listener != null) {
            listener.onPriceChange(getTotal());
        }
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public BuyGoodModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivItemPic = convertView.findViewById(R.id.iv_item_pic);
            viewHolder.tvItemName = convertView.findViewById(R.id.tv_item_name);
            viewHolder.tvItemCode = convertView.findViewById(R.id.tv_item_code);
            viewHolder.tvSpec = convertView.findViewById(R.id.tv_spec);
            viewHolder.tvItemNum = convertView.findViewById(R.id.tv_item_num);
            viewHolder.tvPrice = convertView.findViewById(R.id.tv_price);
            viewHolder.tvSub = convertView.findViewById(R.id.tv_item_num_sub);
            viewHolder.tvAdd = convertView.findViewById(R.id.tv_item_num_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BuyGoodModel model = getItem(position);
        ImageLoaderManager.getInstance(mContext).displayImage(viewHolder.ivItemPic, model.getItem().getPicUrl());
        viewHolder.tvItemName.setText(model.getItem().getItemName());
        viewHolder.tvItemCode.setText(model.getItem().getItemCode());
        viewHolder.tvSpec.setText(model.getItem().getSpec() + model.getItem().getUnit() + "/" + model.getItem().getInventoryUnit());
        viewHolder.tvItemNum.setText(model.getNum() + model.getItem().getInventoryUnit());
        viewHolder.tvPrice.setText(model.getItem().getPrice() + "元/" + model.getItem().getInventoryUnit());
        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = model.getNum();
                model.setNum(++num);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onPriceChange(getTotal());
                }
            }
        });
        viewHolder.tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = model.getNum();
                if (num == 1) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("确认删除商品 " + model.getItem().getItemName())
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mList.remove(model);
                                    notifyDataSetChanged();
                                    if (listener != null) {
                                        listener.onPriceChange(getTotal());
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();

                } else {
                    model.setNum(--num);
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onPriceChange(getTotal());
                    }
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView ivItemPic;
        TextView tvItemName, tvItemCode, tvSpec, tvItemNum, tvPrice, tvSub, tvAdd;
    }

    public interface OnPriceChangeListener {

        void onPriceChange(int price);

    }

}
