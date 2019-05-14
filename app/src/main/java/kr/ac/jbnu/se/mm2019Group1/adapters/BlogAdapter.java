package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Blog;

public class BlogAdapter extends ArrayAdapter<Blog> {

    private  Context context;
    private static class ViewHolder {
        public TextView tvTitleBlog;
        public TextView tvBloggerName;
        public TextView tvDescription;
    }

    public BlogAdapter(Context context, ArrayList<Blog> arrBlogs) {
        super(context, 0, arrBlogs);
        this.context = context;
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
        final Blog blog = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        BlogAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new BlogAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_blog, parent, false);
            viewHolder.tvTitleBlog = (TextView) convertView.findViewById(R.id.tvTitleBlog);
            viewHolder.tvBloggerName = (TextView) convertView.findViewById(R.id.tvBloggerName);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BlogAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object

        viewHolder.tvTitleBlog.setText(blog.getTitle());
//        viewHolder.tvTitleBlog.setTag(position);
        viewHolder.tvBloggerName.setText(blog.getDescription());
        viewHolder.tvDescription.setText(blog.getBloggerName());
//        viewHolder.tvTitleBlog.setOnClickListener(onClickListener);
        // Return the completed view to render on screen
        return convertView;
    }

//    TextView.OnClickListener onClickListener = new TextView.OnClickListener(){
//
//        public void onClick(View v){
//            int position = Integer.parseInt(v.getTag().toString());
//            String tmpLink = getItem(position).getLink();
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(tmpLink));
//            context.startActivity(intent);
//        }
//    };
}
