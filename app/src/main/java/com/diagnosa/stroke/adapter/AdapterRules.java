package com.diagnosa.stroke.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.diagnosa.stroke.db.DBrules;
import com.diagnosa.stroke.R;

public class AdapterRules extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterRules(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_rules, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDrulesrow);
        holder.ListKodeRules = (TextView)v.findViewById(R.id.listkoderulesrow);
        holder.ListRules = (TextView)v.findViewById(R.id.listnamarulesrow);
        holder.ListNilaiRules = (TextView)v.findViewById(R.id.listnilairulesrow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBrules.row_id)));
        holder.ListKodeRules.setText(cursor.getString(cursor.getColumnIndex(DBrules.row_kdRules)));
        holder.ListRules.setText("["+cursor.getString(cursor.getColumnIndex(DBrules.row_alternatif))+"] <=> "+"["+cursor.getString(cursor.getColumnIndex(DBrules.row_kriteria))+"]");
        holder.ListNilaiRules.setText("Nilai: "+cursor.getString(cursor.getColumnIndex(DBrules.row_nilai)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKodeRules;
        TextView ListRules;
        TextView ListNilaiRules;
    }
}
