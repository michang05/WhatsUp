package com.whatsup.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsup.R;
import com.whatsup.asynctask.DownloadImageTask;
import com.whatsup.model.CurrentConditionElement;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by MichaelAngelo on 10/18/2014.
 */
public class CitiesAdapter extends ArrayAdapter<CurrentConditionElement> {
    private int resourceId;

    public CitiesAdapter(Context context, int textViewResourceId, List<CurrentConditionElement> objects) {
        super(context, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relLayout;
        CurrentConditionElement item = getItem(position);
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            relLayout = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            rowView = vi.inflate(resourceId, relLayout, true);

            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.city = (TextView) relLayout.findViewById(R.id.tvCity);
            viewHolder.image = (ImageView) relLayout
                    .findViewById(R.id.imgIcon);
            viewHolder.temp = (TextView) relLayout
                    .findViewById(R.id.tvTemp);
            viewHolder.desc = (TextView) relLayout
                    .findViewById(R.id.tvDescription);
            rowView.setTag(viewHolder);
        }


        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.city.setText(item.getQuery());
        Character deg = '\u00B0';
        holder.temp.setText(item.getTempC() + deg + "c");
        holder.desc.setText(item.getWeatherDescription());

        new DownloadImageTask(holder.image).execute(item.getWeatherIconUrl());

        return rowView;
    }

    static class ViewHolder {
        public TextView city;
        public TextView desc;
        public TextView temp;
        public ImageView image;
    }
}
