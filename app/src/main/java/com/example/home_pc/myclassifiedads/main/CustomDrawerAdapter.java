package com.example.home_pc.myclassifiedads.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.home_pc.myclassifiedads.R;

import java.util.List;

/**
 * Created by Home-PC on 6/30/2015.
 */
public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem>{

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {

        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if(view==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);

            drawerHolder.itemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
            drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);

            drawerHolder.headerLayout = (LinearLayout) view
                    .findViewById(R.id.headerLayout);
            drawerHolder.itemLayout = (LinearLayout) view
                    .findViewById(R.id.itemLayout);
            drawerHolder.items = (LinearLayout) view
                    .findViewById(R.id.items);
            drawerHolder.view=(View) view.findViewById(R.id.baselines);


            view.setTag(drawerHolder);

        } else
        {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if (dItem.getTitle() != null) {
            drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
            drawerHolder.title.setText(dItem.getTitle());

        } else {
            switch(dItem.getinfo()){

                case 1:
                    drawerHolder.items.setMinimumHeight(dptopx(20));
                    drawerHolder.itemName.setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dptopx(25),dptopx(25));
                    drawerHolder.icon.setLayoutParams(params);
                    drawerHolder.view.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    drawerHolder.items.setMinimumHeight(dptopx(20));
                    drawerHolder.itemName.setTextAppearance(getContext(), android.R.style.TextAppearance_Small);
                    params = new LinearLayout.LayoutParams(dptopx(25),dptopx(25));
                    params.setMargins(dptopx(10),0,0,0);
                    drawerHolder.icon.setLayoutParams(params);
                    break;
                default:break;

            }

            drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
            drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
            drawerHolder.icon.setImageResource(dItem.getImgResID());
            drawerHolder.itemName.setText(dItem.getItemName());
        }
        return view;
    }

    public int dptopx(float dp){
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    private static class DrawerItemHolder {

        TextView itemName, title;
        ImageView icon;
        LinearLayout headerLayout, itemLayout, items;
        View view;

    }

}
