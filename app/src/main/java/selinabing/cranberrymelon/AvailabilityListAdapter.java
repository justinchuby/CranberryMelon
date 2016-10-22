package selinabing.cranberrymelon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by selinabing on 10/22/16.
 */
public class AvailabilityListAdapter extends RecyclerView.Adapter<AvailabilityListAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //set item's onclicklisteners
        }

    }


    public AvailabilityListAdapter() {
    }

    @Override
    public AvailabilityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View articleView = inflater.inflate(R.layout.component_contact_list_element, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AvailabilityListAdapter.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}