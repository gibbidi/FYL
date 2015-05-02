package com.example.gibbidi.FYL;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
	private Context mContext;
	private List<Task> mTasks;
	
	public TaskAdapter(Context context, List<Task> objects) {
		super(context, R.layout.task_row_item, objects);
		this.mContext = context;
		this.mTasks = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
			convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);
		}
		
		Task task = mTasks.get(position);

		TextView descriptionView = (TextView) convertView.findViewById(R.id.task_name);
		
		descriptionView.setText(task.getHotelName());

        TextView descriptionView1 = (TextView) convertView.findViewById(R.id.task_cost);

        descriptionView1.setText(task.getHotelPrice());

        TextView descriptionView2 = (TextView) convertView.findViewById(R.id.task_location);

        descriptionView2.setText(task.getHotelLocation());

		/*if(task.isCompleted()){
			descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}*/
		
		return convertView;
	}

}
