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

import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.R;

public class AdapterGejala extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterGejala(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_gejala, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDgejalarow);
        holder.ListKodeGejala = (TextView)v.findViewById(R.id.listkodegejalarow);
        holder.ListNamaGejala = (TextView)v.findViewById(R.id.listnamagejalarow);
        holder.ListBobotGejala = (TextView)v.findViewById(R.id.listbobotgejalarow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBgejala.row_id)));
        holder.ListKodeGejala.setText(cursor.getString(cursor.getColumnIndex(DBgejala.row_kdGejala)));
        holder.ListNamaGejala.setText(cursor.getString(cursor.getColumnIndex(DBgejala.row_nmGejala)));
        holder.ListBobotGejala.setText("Bobot: "+cursor.getString(cursor.getColumnIndex(DBgejala.row_bobot)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKodeGejala;
        TextView ListNamaGejala;
        TextView ListBobotGejala;
    }
}
