package com.hoomin.lhm7877.draganddroptest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDragEventListener myDragEventListener = new MyDragEventListener();
        MyTouchListener myTouchListener = new MyTouchListener();

        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        iv1.setTag("iv1");
        iv2.setTag("iv2");
        iv1.setOnDragListener(myDragEventListener);
        iv2.setOnDragListener(myDragEventListener);
        iv1.setOnTouchListener(myTouchListener);
        iv2.setOnTouchListener(myTouchListener);

        RelativeLayout parent = findViewById(R.id.parent);

        // 동적으로 이미지 추가
        ImageView iv3 = new ImageView(this);
        iv3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        RelativeLayout.LayoutParams iv3Lp = new RelativeLayout.LayoutParams(200, 200);
        iv3Lp.addRule(RelativeLayout.BELOW, iv1.getId());
        parent.addView(iv3, iv3Lp);

        ImageView iv4 = new ImageView(this);
        iv4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        RelativeLayout.LayoutParams iv4Lp = new RelativeLayout.LayoutParams(200, 200);
        iv4Lp.addRule(RelativeLayout.BELOW, iv2.getId());
        iv4Lp.addRule(RelativeLayout.ALIGN_START, iv2.getId());
        iv4Lp.addRule(RelativeLayout.ALIGN_LEFT, iv2.getId());

        iv4Lp.addRule(RelativeLayout.RIGHT_OF, iv4.getId());
        parent.addView(iv4, iv4Lp);

        iv3.setTag("iv3");
        iv4.setTag("iv4");
        iv3.setOnDragListener(myDragEventListener);
        iv4.setOnDragListener(myDragEventListener);
        iv3.setOnTouchListener(myTouchListener);
        iv4.setOnTouchListener(myTouchListener);

        textView = findViewById(R.id.textView);
    }
    TextView textView;

    protected class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {
                        ClipDescription.MIMETYPE_TEXT_PLAIN
                };

                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                v.startDrag(dragData, shadowBuilder, v, 0);
                return true;

            } else {
                return false;
            }
        }


    }



    protected class MyDragEventListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    textView.setText("ACTION_DRAG_STARTED");
                    Log.i("drag", "ACTION_DRAG_STARTED");
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    textView.setText("ACTION_DRAG_ENTERED");
                    Log.i("drag", "ACTION_DRAG_ENTERED");
                    Toast.makeText(MainActivity.this, "entered",Toast.LENGTH_SHORT).show();
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    textView.setText("ACTION_DRAG_LOCATION");
                    Log.i("drag", "ACTION_DRAG_LOCATION");
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    textView.setText("ACTION_DRAG_EXITED");
                    Log.i("drag", "ACTION_DRAG_EXITED");
                    return true;
                case DragEvent.ACTION_DROP:
                    textView.setText("ACTION_DROP");
                    Log.i("drag", "ACTION_DROP");
//                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    textView.setText("ACTION_DRAG_ENDED");
                    Log.i("drag", "ACTION_DRAG_ENDED");
                    return true;
                default:
                    Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                    break;
            }
            return false;
        }
    }
}
