package com.diagnosa.stroke.model;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diagnosa.stroke.R;
import com.diagnosa.stroke.kelas.cJarak;

import java.util.List;

public class aJarak extends RecyclerView.Adapter {

    private List<cJarak> dataList;

    public aJarak(List<cJarak> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tabel_jarak, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtDplus.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtDmin.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtHasil.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtKriteria.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtDplus.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtDmin.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtHasil.setTypeface(null, Typeface.BOLD);

            rowViewHolder.txtKriteria.setText("Alternatif");
            rowViewHolder.txtDplus.setText("D+");
            rowViewHolder.txtDmin.setText("D-");
            rowViewHolder.txtHasil.setText("Preferensi");
        } else {
            cJarak data = dataList.get(rowPos - 1);

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.border_left);
            rowViewHolder.txtDplus.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtDmin.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtHasil.setBackgroundResource(R.drawable.border_right_bottom);

            rowViewHolder.txtKriteria.setText(data.getKriteria() + "");
            rowViewHolder.txtDplus.setText(data.getDplus() + "");
            rowViewHolder.txtDmin.setText(data.getDmin() + "");
            rowViewHolder.txtHasil.setText(data.getHasil() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtKriteria;
        TextView txtDplus;
        TextView txtDmin;
        TextView txtHasil;

        RowViewHolder(View itemView) {
            super(itemView);
            txtKriteria = itemView.findViewById(R.id.tv_jarakkriteria);
            txtDplus = itemView.findViewById(R.id.tv_Dplus);
            txtDmin = itemView.findViewById(R.id.tv_Dmin);
            txtHasil = itemView.findViewById(R.id.tv_jarakHasil);
        }
    }
}
