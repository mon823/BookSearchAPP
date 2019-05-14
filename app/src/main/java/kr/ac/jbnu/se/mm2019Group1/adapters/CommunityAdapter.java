package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Community;

public class CommunityAdapter extends ArrayAdapter<Community> {
    private Context context;
    private  static class ViewHolder{
        public TextView tvTitle;
        public TextView tvWriter;
        public TextView tvDate;

    }
    public CommunityAdapter(Context context, ArrayList<Community> arrLibraries) {
        super(context,0,arrLibraries);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Community community = getItem(position);

        CommunityAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new CommunityAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_community, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitleCommuntiy);
            viewHolder.tvWriter = (TextView) convertView.findViewById(R.id.tvWriter);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommunityAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(community.title);
        viewHolder.tvWriter.setText(community.writer);
        viewHolder.tvDate.setText(community.date);
        return convertView;
    }

}
