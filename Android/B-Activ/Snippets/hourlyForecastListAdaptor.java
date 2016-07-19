package org.app.Adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.app.anand.b_activ.R;
import org.app.model.forecastSlot;

import java.util.List;

/**
 * Created by Anand on 3/23/2016.
 */
public class hourlyForecastListAdaptor extends ArrayAdapter<forecastSlot> {

    private final Activity context;
    private List<forecastSlot> forecastSlotList;

    public hourlyForecastListAdaptor(Activity context,int resource,List<forecastSlot> objects) {
        super(context, resource, objects);
        this.context=context;
        this.forecastSlotList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item,null,true);
        TextView txtTitle = (TextView) view.findViewById(R.id.vListItem);

        forecastSlot fSlot = forecastSlotList.get(position);

        txtTitle.append("Date: " + fSlot.getForecastDate() + "\n");
        txtTitle.append("Time: " + fSlot.getSlotStart() + "-" + fSlot.getSlotEnd() + "\n");
        txtTitle.append("Condition: " + fSlot.getCondition() +"\n");
        txtTitle.append("Max. Temp.: " + fSlot.getMaxTemp()+" °C\n");
        txtTitle.append("Min. Temp.: " + fSlot.getMinTemp() + " °C");

        return view;
    }
}
/*extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listVenueHeader;
    private HashMap<String, List<forecastSlot>> _listChildVenues;

    public hourlyForecastListAdaptor(Context context, List<String> listDataHeader,HashMap<String, List<forecastSlot>> listChildData) {
        this._context = context;
        this._listVenueHeader = listDataHeader;
        this._listChildVenues = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listChildVenues.get(this._listVenueHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_item, null, true);
        TextView forecastChild = (TextView) convertView.findViewById(R.id.vListItem);

        forecastSlot fSlot = (forecastSlot) getChild(groupPosition, childPosition);

        forecastChild.append("Date         :" + fSlot.getForecastDate() + "\n");
        forecastChild.append("Time         :" + fSlot.getSlotStart() + "-" + fSlot.getSlotEnd() + "\n");
        forecastChild.append("Condition    :" + fSlot.getCondition() +"\n");
        forecastChild.append("Max. Temp.    :" + fSlot.getMaxTemp());
        forecastChild.append("Min. Temp.    :" + fSlot.getMinTemp());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listChildVenues.get(this._listVenueHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listVenueHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listVenueHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        String headerTitle = (String) this._listVenueHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null, true);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

*/
/*txtTitle.append(qstn.getTitle() + "\n");
        txtTitle.append("\n");
        txtTitle.append("Asked by:" +qstn.getAskedBy()+"\n");
*/