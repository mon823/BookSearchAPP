package kr.ac.jbnu.se.mm2019Group1.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.models.Library;

public class LibraryAdapter extends ArrayAdapter<Library> {
//    private String tmpLat;
//    private String tmpLng;
//    private String tmpName;
    private Context context;

//    private Integer tmpPoition;

    private static class ViewHolder {
        public TextView tvPlaceName;
        public TextView tvtmp;
        public TextView tvAddress;
        public TextView tvDistance;
        public Button bntMap;
    }

    public LibraryAdapter(Context context, ArrayList<Library> arrLibrary) {
        super(context, 0, arrLibrary);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Library library = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_library, parent, false);
            viewHolder.tvPlaceName = (TextView) convertView.findViewById(R.id.tvPlaceName);
//            viewHolder.tvAddress = (TextView)convertView.findViewById(R.id.tvAddress);
            //viewHolder.tvDistance = (TextView)convertView.findViewById(R.id.tvDistance);
            viewHolder.bntMap = (Button) convertView.findViewById(R.id.btMap);
//            viewHolder.tvtmp = (TextView) convertView.findViewById(R.id.tvDistance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        tmpName = library.getPlaceName();
//        tmpLat = library.getLat();
//        tmpLng = library.getLng();
//        tmpPoition = position;
        viewHolder.tvPlaceName.setText(library.getPlaceName());
        viewHolder.bntMap.setTag(position);
        viewHolder.bntMap.setOnClickListener(onClickListener);
        return convertView;
    }

    Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            String tmpLng = getItem(position).getLng();
            String tmpLat = getItem(position).getLat();
            String tmpName = getItem(position).getPlaceName();
            String tmp;
            //<" + tmpLat + ">,<" + tmpLng + ">
            tmp = "geo:<" + tmpLat + ">,<" + tmpLng + ">?q=(" + tmpName + ")";
            Log.d("Map", tmp);
            Uri uri = Uri.parse(tmp);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);

        }
    };
}
