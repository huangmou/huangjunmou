package com.jason.feick.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jason.feick.R;

public class LoadingDataDialog extends Dialog
{
  private Context context;
  private TextView tv_msg;

  private AnimationDrawable mADAnimationDrawable;
  private ImageView mIVAnimationContent;

  public LoadingDataDialog(Context context)
  {
    super(context, R.style.dialog_fullscreen);
    this.context = context;
    init();
  }

  public LoadingDataDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener)
  {
    super(context, cancelable, cancelListener);
    this.context = context;
    init();
  }

  public LoadingDataDialog(Context context, int theme) {
    super(context, theme);
    this.context = context;
    init();
  }

  private void init()
  {
  }

  protected void onCreate(Bundle savedInstanceState)
  {
    requestWindowFeature(1);
//    getWindow().setBackgroundDrawableResource(
//      R.drawable.ic_launcher);
    super.onCreate(savedInstanceState);
    setContentView(creatLayout());
  }

  /*private LinearLayout creatLayout()
  {
    LinearLayout layout = new LinearLayout(this.context);
    layout.setPadding(10, 10, 10, 10);
    layout.setGravity(16);

    ProgressBar progressBar = new ProgressBar(this.context, null,
      16842873);
    layout.addView(progressBar);
    this.tv_msg = new TextView(this.context);
    this.tv_msg.setText(R.string.text_loadingdata);
    this.tv_msg.setTextColor(Color.parseColor("#cccccc"));
    layout.addView(this.tv_msg);
    return layout;
  }*/

  private LinearLayout creatLayout()
  {
    LinearLayout layout = new LinearLayout(this.context);
    layout.setPadding(10, 10, 10, 10);
    layout.setGravity(16);

    View view=LayoutInflater.from(this.context).inflate(R.layout.loading, null);
    view.getBackground().setAlpha(120);
    mIVAnimationContent= (ImageView) view.findViewById(R.id.loading_data_dialog);
    mADAnimationDrawable = (AnimationDrawable) mIVAnimationContent.getDrawable();
    mADAnimationDrawable.start();
   /* ProgressBar progressBar = new ProgressBar(this.context, null,
            16842873);
    layout.addView(progressBar);
    this.tv_msg = new TextView(this.context);
    this.tv_msg.setText(R.string.text_loadingdata);
    this.tv_msg.setTextColor(Color.parseColor("#cccccc"));
    layout.addView(this.tv_msg);*/
    layout.addView(view);
    return layout;
  }

  public void stopAnimation(){
    if (mADAnimationDrawable!=null){
    mADAnimationDrawable.stop();
    }
  }

  public void setMessage(String msg)
  {
    if (this.tv_msg != null)
      this.tv_msg.setText(msg);
  }

  public void setMessage(int resId)
  {
    setMessage(this.context.getString(resId));
  }
}