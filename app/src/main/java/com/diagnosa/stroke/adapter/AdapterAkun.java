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

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.db.DBgejala;
import com.diagnosa.stroke.db.DBuser;

public class AdapterAkun extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdapterAkun(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_akun, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDakunrow);
        holder.ListNIK = (TextView)v.findViewById(R.id.listnikakunrow);
        holder.ListUsername = (TextView)v.findViewById(R.id.listusernamerow);
        holder.ListPassword = (TextView)v.findViewById(R.id.listpasswordrow);
        holder.ListLevel = (TextView)v.findViewById(R.id.listlevelrow);
        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBuser.row_id)));
        holder.ListNIK.setText(cursor.getString(cursor.getColumnIndex(DBuser.row_nik)));
        holder.ListUsername.setText(cursor.getString(cursor.getColumnIndex(DBuser.row_email)));
        holder.ListPassword.setText(cursor.getString(cursor.getColumnIndex(DBuser.row_password)));
        holder.ListLevel.setText(cursor.getString(cursor.getColumnIndex(DBuser.row_level)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListNIK;
        TextView ListUsername;
        TextView ListPassword;
        TextView ListLevel;
    }
}
