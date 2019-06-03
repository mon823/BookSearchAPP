package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.UsedBook;

public class UsedBookAdapter extends ArrayAdapter<UsedBook> {

    private static class ViewHolder{
        public ImageView ivCover;
        public TextView tvTitle;
        public  TextView tvAuthor;

    }

    public UsedBookAdapter(Context context, ArrayList<UsedBook> usedBookArrayList){
        super(context,0,usedBookArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final UsedBook usedBook = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_used_book, parent, false);
            viewHolder.ivCover = (ImageView)convertView.findViewById(R.id.ivUsedBookCover);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvUsedTitle);
            viewHolder.tvAuthor = (TextView)convertView.findViewById(R.id.tvUsedAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(usedBook.getTitle());
        viewHolder.tvAuthor.setText(usedBook.getAuthor());
        Picasso.with(getContext()).load(Uri.parse(usedBook.getCover())).error(R.drawable.ic_nocover).into(viewHolder.ivCover);
        // Return the completed view to render on screen
        return convertView;
    }
}
