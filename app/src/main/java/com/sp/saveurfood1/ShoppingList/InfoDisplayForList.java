package com.sp.saveurfood1.ShoppingList;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sp.saveurfood1.R;
import com.sp.saveurfood1.Storage.DashBoard;
import com.sp.saveurfood1.Storage.InfoDisplay;
import com.sp.saveurfood1.Storage.guide1;

public class InfoDisplayForList extends AppCompatActivity {

    private Cursor model = null;
    private Helper2 helper2 = null;
    private RestaurantAdapter adapter = null;
    private ListView list;
    private FloatingActionButton add;
    private FloatingActionButton scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display_for_list);
        add=findViewById(R.id.add_button);
        scan=findViewById(R.id.scan_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(InfoDisplayForList.this);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Do you have a barcode?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(InfoDisplayForList.this, ScanForList.class);
                        startActivity(intent);

                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(InfoDisplayForList.this, DataInputForList.class);
                        startActivity(intent);

                    }
                });
                alertDialog.show();

            }

        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(InfoDisplayForList.this, ScanForRemoveList.class);
                startActivity(intent1);

            }
        });

        helper2 = new Helper2(this);
        list = findViewById(R.id.contacts);
        model = helper2.getAll();
        adapter = new RestaurantAdapter(this, model, 0);
        list.setOnItemClickListener(onListClick);
        list.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=new Intent(InfoDisplayForList.this, guide2.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        helper2.close();
        super.onDestroy();
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(InfoDisplayForList.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Delete item?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    model.moveToPosition(position);
                    String recordID = helper2.getID(model);
                    String name=helper2.getListName(model);
                    helper2.deleteItem(recordID);
                    adapter.notifyDataSetChanged();
                    Intent intent=new Intent(InfoDisplayForList.this,InfoDisplayForList.class);
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
        Intent intent=new Intent(InfoDisplayForList.this, DashBoard.class);
        startActivity(intent);
    }

    static class RestaurantHolder {
        private TextView contName = null;
        private TextView tel=null;
        private ImageView icon = null;
        private TextView expiry=null;

        RestaurantHolder(View row) {
            contName = row.findViewById(R.id.contName);
            expiry=row.findViewById(R.id.addr);
            icon=row.findViewById(R.id.icon);
            tel=row.findViewById(R.id.tel);
        }

        void populateFrom(Cursor c, Helper2 helper2) {

            contName.setText(helper2.getListName(c));
            tel.setText(helper2.getListDes(c));
            expiry.setText(helper2.getListBC(c));
            byte [] bytesImage=helper2.getImage(c);
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
            holder.populateFrom(cursor,helper2);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row5, parent, false);
            RestaurantHolder holder = new RestaurantHolder(row);
            row.setTag(holder);
            return (row);
        }
    }
}