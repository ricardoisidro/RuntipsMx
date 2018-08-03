package com.runtips.ricardo.runtipsmx.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.runtips.ricardo.runtipsmx.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> letterDays;
    private List<String> numDays;
    private List<String> mainText;
    private List<String> auxText;

    public MyAdapter(Context context, int layout, List<String> letterDays, List<String> numDays, List<String> text1,
                     List<String> text2) {

        this.context = context;
        this.layout = layout;
        this.letterDays = letterDays;
        this.numDays = numDays;
        this.mainText = text1;
        this.auxText = text2;

    }

    @Override
    public int getCount() {
        return this.letterDays.size();
    }

    @Override
    public Object getItem(int position) {
        return this.letterDays.get(position);
    }

    public Object getItem2(int position) {
        return this.numDays.get(position);
    }

    public Object getItem3(int position) {
        return this.mainText.get(position);
    }

    public Object getItem4(int position) {
        return this.auxText.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        // View Holder Pattern
        ViewHolder holder;

        if (convertView == null) {
            // Inflamos la vista que nos ha llegado con nuestro layout personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);

            holder = new ViewHolder();
            // Referenciamos el elemento a modificar y lo rellenamos
            holder.letterDay = (TextView) convertView.findViewById(R.id.txtPaidListDayOfWeek);
            holder.numberDay = (TextView) convertView.findViewById(R.id.txtPaidListDayOfWeekNumber);
            holder.mainTraining = (TextView) convertView.findViewById(R.id.txtPaidListTrainingTitle);
            holder.auxTraining = (TextView) convertView.findViewById(R.id.txtPaidListTrainingDetail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Nos traemos el valor actual dependiente de la posición
        String currentLetterDay = letterDays.get(position);
        currentLetterDay = (String) getItem(position);

        String currentNumberDay = numDays.get(position);
        currentNumberDay = (String) getItem2(position);

        String currentMainText = mainText.get(position);
        currentMainText = (String) getItem3(position);

        String currentAuxText = auxText.get(position);
        currentAuxText = (String) getItem4(position);

        // Referenciamos el elemento a modificar y lo rellenamos
        holder.letterDay.setText(currentLetterDay);
        holder.numberDay.setText(currentNumberDay);
        holder.mainTraining.setText(currentMainText);
        holder.auxTraining.setText(currentAuxText);

        // devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }

    static class ViewHolder {
        private TextView letterDay;
        private TextView numberDay;
        private TextView mainTraining;
        private TextView auxTraining;
    }
}
