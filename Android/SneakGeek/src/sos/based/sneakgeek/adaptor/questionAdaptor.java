package sos.based.sneakgeek.adaptor;

import java.io.InputStream;
import java.util.List;

import sos.based.sneakgeek.R;
import sos.based.sneakgeek.model.Question;
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

public class questionAdaptor extends ArrayAdapter<Question> {
	
	private final Activity context;
	private List<Question> questionList;
	
	public questionAdaptor(Activity context,int resource,List<Question> objects) {
		super(context, resource, objects);
		this.context=context;
		this.questionList = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = context.getLayoutInflater();
		//View view = inflater.inflate(R.layout.listelement,null,true);
		View view = inflater.inflate(R.layout.listelement,null,true);
		TextView txtTitle = (TextView) view.findViewById(R.id.txt);
				
		Question qstn = questionList.get(position);
		txtTitle.append(qstn.getTitle() + "\n");
		txtTitle.append("\n");
		txtTitle.append("Asked by:" +qstn.getAskedBy()+"\n");
		
	    if(qstn.getProfilePicture() != null){
	    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
		    imageView.setImageBitmap(qstn.getProfilePicture());
	    }
	    else
	    {
	    	QuestionAndView container = new QuestionAndView();
	    	container.question = qstn;
	    	container.view = view;
	    	
	    	ImageLoader loader = new ImageLoader();
	    	loader.execute(container);
	    }
	    	    
		return view;
	}

	class QuestionAndView {
		public Question question;
		public View view;
		public Bitmap bitmap;
	}
	
	private class ImageLoader extends AsyncTask<QuestionAndView, Void, QuestionAndView> {

		@Override
		protected QuestionAndView doInBackground(QuestionAndView... params) {
			// TODO Auto-generated method stub
			
			QuestionAndView container = params[0];
			Question question = container.question;
						
				try {

					InputStream in = (InputStream) question.getProfilePic().getContent();
					Bitmap bitmap = BitmapFactory.decodeStream(in);
					question.setProfilePicture(bitmap); 
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
		protected void onPostExecute(QuestionAndView result) {
			ImageView imageView = (ImageView) result.view.findViewById(R.id.img);
		    imageView.setImageBitmap(result.bitmap);
		    result.question.setProfilePicture(result.bitmap);
		}
		
	}
}
