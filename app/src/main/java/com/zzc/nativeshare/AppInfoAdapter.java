package com.zzc.nativeshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * auth zzc
 * date 2018/7/2
 * desc ${desc}
 */

public class AppInfoAdapter extends BaseAdapter {
    private List<AppInfoVo> mList;
    private Context mContext;

    public AppInfoAdapter(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    public void addAll(List<AppInfoVo> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app_info, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        AppInfoVo appInfoVo = mList.get(position);
        holder.mApp.setText(appInfoVo.getAppName());
        holder.mPkg.setText(appInfoVo.getPackageName());
        holder.mClz.setText(appInfoVo.getLauncherName());
        holder.mIcon.setImageDrawable(appInfoVo.getIcon());
        return convertView;
    }

    public static class Holder {
        View root;
        TextView mPkg;
        TextView mClz;
        TextView mApp;
        ImageView mIcon;

        public Holder(View root) {
            this.root = root;
            mApp = root.findViewById(R.id.tv_item_app);
            mPkg = root.findViewById(R.id.tv_item_pkg);
            mClz = root.findViewById(R.id.tv_item_clz);
            mIcon = root.findViewById(R.id.iv_item_icon);
        }
    }
}
