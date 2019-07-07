package Utitlities;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.androiddeft.loginandregistration.ExerciseActivity;
import com.androiddeft.loginandregistration.ExerciseCountdownActivity;
import com.androiddeft.loginandregistration.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    ImageLoader imageLoader1;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        final GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);
        Viewholder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               if(GetDataAdapter.flag==1){
                   Intent intent = new Intent(view.getContext(), ExerciseCountdownActivity.class);
                   intent.putExtra("image",getDataAdapter1.getImageServerUrl());
                   intent.putExtra("time",getDataAdapter1.getExerciseTime());
                   view.getContext().startActivity(intent);
               }
               else if(GetDataAdapter.flag==2)
               {
                   Toast.makeText(view.getContext(),getDataAdapter1.getImageTitleName(),Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(view.getContext(),"ERROR",Toast.LENGTH_LONG).show();
               }
                }
        });
        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();

        imageLoader1.get(getDataAdapter1.getImageServerUrl(),
                ImageLoader.getImageListener(
                        Viewholder.networkImageView,
                        R.mipmap.ic_launcher,
                        android.R.drawable.ic_dialog_alert
                )
        );

        Viewholder.networkImageView.setImageUrl(getDataAdapter1.getImageServerUrl(), imageLoader1);

        Viewholder.ImageTitleNameView.setText(getDataAdapter1.getImageTitleName());
    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleNameView;
        public NetworkImageView networkImageView ;

        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleNameView = (TextView) itemView.findViewById(R.id.textView_item) ;

            networkImageView = (NetworkImageView) itemView.findViewById(R.id.VollyNetworkImageView1) ;

        }
    }
}