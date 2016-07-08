package sos.based.sneakgeek.adaptor;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import sos.based.sneakgeek.R;
import sos.based.sneakgeek.model.Answer;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class answerAdaptor extends ArrayAdapter<Answer> {

	private final Activity context;
	private List<Answer> answerList;

	public answerAdaptor(Activity context,int resource,List<Answer> objects) {
		super(context, resource, objects);
		this.context=context;
		this.answerList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.answerlist,null,true);
		TextView answer = (TextView) view.findViewById(R.id.ansTxt);
		TextView details = (TextView) view.findViewById(R.id.info);

		Answer answerDetail = answerList.get(position);
		details.append("Name       :"+ answerDetail.getUser() +"\n");
		details.append("Answered on:"+ answerDetail.getAnswerTimestamp() + "\n"); 
		details.append("Upvotes    :" +answerDetail.getUpvotes() + "\n");
		answer.setText(answerDetail.getReply());

		AnswerAndView container = new AnswerAndView();
		container.answer = answerDetail;
		container.view = view;

		ImageLoader loader = new ImageLoader();
		loader.execute(container);

		return view;
	}

	class AnswerAndView {
		public Answer answer;
		public View view;
		public Bitmap bitmap;
	}

	private class ImageLoader extends AsyncTask<AnswerAndView, Void, AnswerAndView> {

		@Override
		protected AnswerAndView doInBackground(AnswerAndView... params) {
			// TODO Auto-generated method stub

			AnswerAndView container = params[0];
			Answer answer = container.answer;

			HttpClient client = new DefaultHttpClient();
			String iUrl=answer.getProfilePic().toString();
			Log.d("Usrl",iUrl);
			HttpGet request = new HttpGet(iUrl);
			HttpResponse response;
			try {
				response = (HttpResponse)client.execute(request);           
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
				InputStream inputStream = bufferedEntity.getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				answer.setProfilePicture(bitmap); 
				inputStream.close();
				container.bitmap = bitmap;
				return container;
			}catch(Exception e)
			{
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(AnswerAndView result) {
			ImageView imageView = (ImageView) result.view.findViewById(R.id.imgR);
			imageView.setImageBitmap(result.bitmap);
			result.answer.setProfilePicture(result.bitmap);
		}

	}

}
