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
import com.diagnosa.stroke.kelas.cSolusiIdeal;

import java.util.List;

public class aSolusiIdeal extends RecyclerView.Adapter {

    private List<cSolusiIdeal> dataList;

    public aSolusiIdeal(List<cSolusiIdeal> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tabel_solusiideal, parent, false);

        return new RowViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtYplus.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtYmin.setBackgroundResource(R.drawable.table_cell);
            rowViewHolder.txtKriteria.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtYplus.setTypeface(null, Typeface.BOLD);
            rowViewHolder.txtYmin.setTypeface(null, Typeface.BOLD);

            rowViewHolder.txtKriteria.setText("Kriteria");
            rowViewHolder.txtYplus.setText("Y+");
            rowViewHolder.txtYmin.setText("Y-");
        } else {
            cSolusiIdeal data = dataList.get(rowPos - 1);

            rowViewHolder.txtKriteria.setBackgroundResource(R.drawable.border_left);
            rowViewHolder.txtYplus.setBackgroundResource(R.drawable.border_right_bottom);
            rowViewHolder.txtYmin.setBackgroundResource(R.drawable.border_right_bottom);

            rowViewHolder.txtKriteria.setText(data.getKriteria() + "");
            rowViewHolder.txtYplus.setText(data.getYplus() + "");
            rowViewHolder.txtYmin.setText(data.getYmin() + "");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtKriteria;
        TextView txtYplus;
        TextView txtYmin;

        RowViewHolder(View itemView) {
            super(itemView);
            txtKriteria = itemView.findViewById(R.id.tv_idealPluskriteria);
            txtYplus = itemView.findViewById(R.id.tv_Yplus);
            txtYmin = itemView.findViewById(R.id.tv_Ymin);
        }
    }
}
