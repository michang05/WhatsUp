package com.whatsup.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsup.R;
import com.whatsup.asynctask.LocalWeatherAsyncTask;
import com.whatsup.db.CurrentWeatherDb;
import com.whatsup.db.ForecastDb;
import com.whatsup.db.RequestDb;
import com.whatsup.model.CurrentConditionElement;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WhatsUpMain extends Activity {


    List<CurrentConditionElement> listCe;
    private ListView listView;
    private List<CurrentConditionElement> listWeather;
    private CitiesAdapter cityAdapter;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_up_main);

        TextView tvPolicy = (TextView) findViewById(R.id.tvApiPolicy);
        tvPolicy.setText(
                Html.fromHtml(
                        "Powered by <a href=\"http://www.worldweatheronline.com/\" \n" +
                                "title=\"Free local weather content provider\" \n" +
                                "target=\"_blank\">World Weather Online</a> "));
        tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        listCe = new ArrayList<CurrentConditionElement>();
        cityAdapter = new CitiesAdapter(this, R.layout.city_row_item, listCe);

        listView = (ListView) findViewById(android.R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder ad = new AlertDialog.Builder(WhatsUpMain.this);

                final String query = listCe.get(position).getQuery();
                ad.setTitle(query);
                ad.setMessage("View Details or Delete?");
                ad.setNeutralButton("View Details", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent view = new Intent(WhatsUpMain.this, WeatherDetail.class);
                        view.putExtra("query", query);
                        view.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        startActivity(view);
                    }
                });

                ad.setPositiveButton("Delete", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listCe.remove(position);
                        CurrentWeatherDb currentWeatherDb = new CurrentWeatherDb(WhatsUpMain.this);
                        currentWeatherDb.delete(query);
                        cityAdapter.notifyDataSetChanged();
                    }
                });
                ad.show();
            }
        });

//        RequestDb rdb = new RequestDb(this);
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("Dublin");
//        list.add("London");
//        list.add("New York");
//        list.add("Barcelona");


        //Load the list
        loadTheList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateTheList();
    }

    private void updateTheList() {
        listCe.clear();
        CurrentWeatherDb cDb = new CurrentWeatherDb(this);
        listWeather = cDb.getAllCurrentWeather();
        listCe.addAll(listWeather);
        cityAdapter.notifyDataSetChanged();
    }

    private void loadTheList() {

        CurrentWeatherDb cDb = new CurrentWeatherDb(this);
        listWeather = cDb.getAllCurrentWeather();
        listCe.addAll(listWeather);
        cityAdapter.notifyDataSetChanged();
        listView.setAdapter(cityAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_whats_up_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
            showAddDialog();
            return true;
        } else if (id == R.id.delete) {
            deleteAll();
            return true;
        }
        return false;

    }

    private void deleteAll() {
        CurrentWeatherDb cdb = new CurrentWeatherDb(this);
        RequestDb db = new RequestDb(this);
        ForecastDb fdb = new ForecastDb(this);
        cdb.delete();
        db.delete();
        fdb.delete();

        updateTheList();

    }

//    private void refreshAll() {
//        RequestDb db = new RequestDb(this);
//
//        for (String city : db.getRequestedCities()){
//            new LocalWeatherAsyncTask(this, "refresh").execute(city);
//        }
//
//       updateTheList();
//
//    }

    private void showAddDialog() {
        // get prompt
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.add_city, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Search and Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
//                                new LocalWeatherAsyncTask(WhatsUpMain.this).execute(userInput.getText().toString());
                                Intent view = new Intent(WhatsUpMain.this, WeatherDetail.class);
                                view.putExtra("query", userInput.getText().toString());
                                view.putExtra("isAdd", true);
                                view.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                                startActivity(view);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}

