package com.example.app_note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class JobAdapter extends BaseAdapter {
    private MainActivity2 context;
    private int layout;
    private List<Job> listJob;

    public JobAdapter(MainActivity2 context, int layout, List<Job> listJob) {
        this.context = context;
        this.layout = layout;
        this.listJob = listJob;
    }

    public MainActivity2 getContext() {
        return context;
    }

    public void setContext(MainActivity2 context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Job> getListJob() {
        return listJob;
    }

    public void setListJob(List<Job> listJob) {
        this.listJob = listJob;
    }

    @Override
    public int getCount() {
        return listJob.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView adapter_txt;
        ImageView adapter_imgEdit,adapter_imgDelete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(MainActivity2.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            //ánh xạ
            holder.adapter_txt=(TextView) convertView.findViewById(R.id.adapter_txt);
            holder.adapter_imgEdit=(ImageView) convertView.findViewById(R.id.adapter_imgEdit);
            holder.adapter_imgDelete=(ImageView) convertView.findViewById(R.id.adapter_imgDelete);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //gán giá trị
        Job job = listJob.get(position);
        holder.adapter_txt.setText(job.getJobName());
        // bắt sự kiện edit
        holder.adapter_imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogEdit(job.getJobName(),job.getJobID());
            }
        });
        holder.adapter_imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogDelete(job.getJobName(),job.getJobID());
            }
        });
        return convertView;
    }
}
