package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Book;
import kr.ac.jbnu.se.mm2019Group1.models.Search;

public class SearchAdapter extends ArrayAdapter<Search> {

    private static class ViewHolder{
        public TextView tvSearch;
    }

    public SearchAdapter(Context context, ArrayList<Search> searchArrayList){
        super(context, 0, searchArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Search search = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new  ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_recent_searches, parent, false);
            viewHolder.tvSearch = (TextView) convertView.findViewById(R.id.tvSearch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.tvSearch.setText(search.getWord());

        // Return the completed view to render on screen
        return convertView;
    }


}
