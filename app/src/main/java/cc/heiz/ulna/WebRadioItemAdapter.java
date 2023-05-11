package cc.heiz.ulna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WebRadioItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<WebRadio> radios;

    public WebRadioItemAdapter(Context ctx, List<WebRadio> radios) {
        this.context = ctx;
        this.radios = radios;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return radios.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_web_radio_item, null);
        TextView title = convertView.findViewById(R.id.webradio_title);
        ImageView icon = convertView.findViewById(R.id.webradio_icon);
        title.setText(radios.get(position).title);
        icon.setImageResource(radios.get(position).image);
        return convertView;
    }
}
