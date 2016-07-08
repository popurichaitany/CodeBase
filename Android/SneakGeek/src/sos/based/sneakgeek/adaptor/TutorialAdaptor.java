package sos.based.sneakgeek.adaptor;

import java.io.InputStream;
import java.util.List;

import sos.based.sneakgeek.R;
import sos.based.sneakgeek.model.Tutorial;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialAdaptor extends ArrayAdapter<Tutorial> {

	private final Activity context;
	private List<Tutorial> ttrList;

	public TutorialAdaptor(Activity context,int resource,List<Tutorial> objects) {
		super(context, resource, objects);
		this.context=context;
		this.ttrList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		//View view = inflater.inflate(R.layout.listelement,null,true);
		View view = inflater.inflate(R.layout.listelement,null,true);
		TextView txtTitle = (TextView) view.findViewById(R.id.txt);

		Tutorial qstn = ttrList.get(position);
		String publish=qstn.getPublishedAt();
		publish.replace("T", " ");
		publish.replace(".000Z", ""); 
		txtTitle.append("Title	:"+qstn.getTitle() + "\n");
		txtTitle.append("\n");
		txtTitle.append("Posted at:" +qstn.getPublishedAt()+"\n");


		TutorialAndView container = new TutorialAndView();
		container.ttrl = qstn;
		container.view = view;

		ImageLoader loader = new ImageLoader();
		loader.execute(container);


		return view;
	}

	class TutorialAndView {
		public Tutorial ttrl;
		public View view;
		public Bitmap bitmap;
	}

	private class ImageLoader extends AsyncTask<TutorialAndView, Void, TutorialAndView> {

		@Override
		protected TutorialAndView doInBackground(TutorialAndView... params) {
			// TODO Auto-generated method stub

			TutorialAndView container = params[0];
			Tutorial ttrls = container.ttrl;

			try {

				InputStream in = (InputStream) ttrls.getUrl().getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				ttrls.setTile(bitmap); 
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
		protected void onPostExecute(TutorialAndView result) {
			ImageView imageView = (ImageView) result.view.findViewById(R.id.img);
			imageView.setImageBitmap(result.bitmap);
			result.ttrl.setTile(result.bitmap);
		}

	}
}

