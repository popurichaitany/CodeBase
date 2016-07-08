package sos.based.sneakgeek.adaptor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import sos.based.sneakgeek.R;
import sos.based.sneakgeek.model.Answerer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<Answerer>> _listDataChild;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<Answerer>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		//final String childText = (String) getChild(groupPosition, childPosition);

					LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null,true);
		

		TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
		
		Answerer ansr = (Answerer) getChild(groupPosition, childPosition);
				
		txtListChild.append("Name           :"+ansr.getName() + "\n");
		txtListChild.append("User type     :"+ansr.getUserType() + "\n");
		txtListChild.append("Score          :"+ansr.getScore() + "\n");
		txtListChild.append("Reputation   :"+ansr.getReputation() + "\n");
		txtListChild.append("Post Count   :"+ansr.getPostCount() + "\n");
		txtListChild.append("Accept rate  :"+ansr.getAcceptRate() + "\n");
		
		AnswererAndView container = new AnswererAndView();
    	container.answerer = ansr;
    	container.view = convertView;
    	
    	ImageLoader loader = new ImageLoader();
    	loader.execute(container);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
		
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) this._listDataHeader.get(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null,true);
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
	
	class AnswererAndView {
		public Answerer answerer;
		public View view;
		public Bitmap bitmap;
	}
	
	private class ImageLoader extends AsyncTask<AnswererAndView, Void, AnswererAndView> {

		@Override
		protected AnswererAndView doInBackground(AnswererAndView... params) {
			// TODO Auto-generated method stub
			
			AnswererAndView container = params[0];
			Answerer answerer = container.answerer;
						
				try {

					InputStream in = (InputStream) answerer.getProfilePic().getContent();
					Bitmap bitmap = BitmapFactory.decodeStream(in);
					answerer.setProfilePicture(bitmap); 
					in.close();
					container.bitmap = bitmap;
					return container;
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(AnswererAndView result) {
			ImageView imageView = (ImageView) result.view.findViewById(R.id.ImgA);
		    imageView.setImageBitmap(result.bitmap);
		    result.answerer.setProfilePicture(result.bitmap);
		}
		
	}
}


