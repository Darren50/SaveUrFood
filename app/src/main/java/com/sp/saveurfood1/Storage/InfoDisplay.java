package com.sp.saveurfood1.Storage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sp.saveurfood1.R;

public class InfoDisplay extends AppCompatActivity {

    private Cursor model = null;
    private Helper helper = null;
    private RestaurantAdapter adapter = null;
    private ListView list;
    private TextView empty = null;
    private ImageButton edit;
    private FloatingActionButton add;
    private FloatingActionButton TTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);
        add=findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InfoDisplay.this, DataInput.class);
                startActivity(intent);
            }

        });

        helper = new Helper(this);
        list = findViewById(R.id.contacts);
        model = helper.getAll();
        adapter = new RestaurantAdapter(this, model, 0);
        list.setOnItemClickListener(onListClick);
        list.setAdapter(adapter);



    }


    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=new Intent(InfoDisplay.this,guide1.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }








    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(InfoDisplay.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Delete item?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    model.moveToPosition(position);
                    String recordID = helper.getID(model);
                    helper.deleteItem(recordID);
                    adapter.notifyDataSetChanged();
                    Intent intent=new Intent(InfoDisplay.this,InfoDisplay.class);
                    startActivity(intent);

                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();

        }


    };

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(InfoDisplay.this, DashBoard.class);
        startActivity(intent);
    }





    static class RestaurantHolder {
        private TextView contName = null;
        private TextView tel=null;
        private ImageView icon = null;
        private TextView expiry=null;




        RestaurantHolder(View row) {
            contName = row.findViewById(R.id.contName);
            expiry=row.findViewById(R.id.expirydate);

            icon=row.findViewById(R.id.icon);
            tel=row.findViewById(R.id.tel);




        }



        void populateFrom(Cursor c, Helper helper) {
            contName.setText(helper.getContactName(c));
            tel.setText(helper.getContactTel(c));
            expiry.setText(helper.getbtExpiry(c));
            byte [] bytesImage=helper.getImage(c);
            Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytesImage,0,bytesImage.length);
            icon.setImageBitmap(bitmapImage);

        }





    }


    class RestaurantAdapter extends CursorAdapter {
        RestaurantAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            RestaurantHolder holder = (RestaurantHolder) view.getTag();
            holder.populateFrom(cursor,helper);


        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row4, parent, false);
            RestaurantHolder holder = new RestaurantHolder(row);
            row.setTag(holder);
            return (row);

        }









    }
}




