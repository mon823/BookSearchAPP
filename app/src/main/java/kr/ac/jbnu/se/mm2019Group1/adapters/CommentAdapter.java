package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;

    private static class ViewHolder{
        public TextView tvWriter;
        public TextView tvComment;
    }

    public CommentAdapter(Context context, ArrayList<Comment> commentArrayList){
        super(context,0,commentArrayList);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Comment comment = getItem(position);

        CommentAdapter.ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new CommentAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comment,parent,false);
            viewHolder.tvWriter = (TextView) convertView.findViewById(R.id.tvCommentWriter);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CommentAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.tvWriter.setText(comment.writer);
        viewHolder.tvComment.setText(comment.comment);

        return convertView;
    }
}
