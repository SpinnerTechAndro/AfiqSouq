package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.PastOrderListAdapter;
import dev.spinner_tech.afiqsouq.Adapter.PastTransactionListAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.oldOrderModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.Utils.Utilities;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OldTransactions extends AppCompatActivity implements PastTransactionListAdapter.ItemClickListener {

    List<oldOrderModel> orderModelList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    List<oldOrderModel.LineItem> ProductList = new ArrayList<>();
    SpinKitView spinKitView;
    RecyclerView recyclerView;
    LineChart chart;
    ImageView cartIcon;
    TextView cartCountTv;
    CartDatabase cartDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_transactions);
        cartDatabase = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        spinKitView = findViewById(R.id.spin_kit22);
        spinKitView.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recent_spendings_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chart = findViewById(R.id.cube_lines);
        chart.setBackgroundColor(Color.WHITE);
        cartIcon = findViewById(R.id.imageView_discover_cart);
        cartCountTv = findViewById(R.id.textview_discover_cartNumber);

        setUpChart();
//        chart.animateXY(2000, 2000);
//
//        // don't forget to refresh the drawing
//        chart.invalidate();

        loadOrderList();
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SharedPrefManager.getInstance(getApplicationContext())
                        .isUserLoggedIn()) {
                    Intent p = new Intent(getApplicationContext(), CartListPage.class);
                    startActivity(p);
                }


            }
        });
    }

    private void setUpChart() {
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.getExtraRightOffset();
        chart.getExtraLeftOffset();
        chart.getExtraLeftOffset();

        chart.setVisibleXRangeMaximum(12);
        XAxis x = chart.getXAxis();
        x.setTextColor(Color.parseColor("#0E153D"));
        x.setTextSize(14);
        x.setAxisMaximum(12);
        x.enableGridDashedLine(10f, 10f, 0f);
        x.setAxisMinimum(1);
        x.setDrawLabels(true);
        x.setLabelRotationAngle(90);
        x.setLabelCount(12, true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(true);
        x.setAxisLineColor(Color.BLACK);
        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {


                return   "  " + getMonthName(value );
            }
        });
        YAxis y = chart.getAxisLeft();

        y.setTextColor(Color.parseColor("#0E153D"));
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.enableGridDashedLine(10f, 10f, 0f);
        y.setTextSize(14);

        y.setAxisLineColor(Color.GRAY);
        y.setLabelCount(6 , true);
        y.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis)
            {
                if(value == 0 ){
                    return "";
                }
                else {
                    return Constants.BDT_SIGN + " " + Math.round(value);
                }

            }
        });
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.animateXY(1000, 1000);}


    private void loadOrderList() {
        spinKitView.setVisibility(View.VISIBLE);
        String id = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId().toString();
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<oldOrderModel>> call = RetrofitClient.getInstance().getApi()
                .oldOrderList(authHeader, id);

        call.enqueue(new Callback<List<oldOrderModel>>() {
            @Override
            public void onResponse(Call<List<oldOrderModel>> call, Response<List<oldOrderModel>> response) {
                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    orderModelList = response.body();
                    List<oldOrderModel.LineItem> list = new ArrayList<>();

                    // now load the product from  oder list ;
                    for (oldOrderModel item : orderModelList) {

                        List<oldOrderModel.LineItem> productList = item.getLineItems();

                        for (oldOrderModel.LineItem singleItem : productList) {
                            list.add(singleItem);
                            dateList.add(item.getDateCreated() + "");
                        }


                    }
                    PastTransactionListAdapter adapter = new PastTransactionListAdapter(list, dateList, getApplicationContext(), OldTransactions.this);
                    recyclerView.setAdapter(adapter);

                    //    setData(5, 5, list, dateList);


                    filterDataForMonth(list, dateList);


                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), response.code() + " ", 1).show();
                }
            }

            @Override
            public void onFailure(Call<List<oldOrderModel>> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), t.getMessage() + " ", 1).show();
            }
        });
    }

    @Override
    public void onItemClick(oldOrderModel model) {

    }


    private void setData(List<Float> dataSet) {
//int count, float range, List<oldOrderModel.LineItem> list, List<String> dateList
        float maxValue = Collections.max(dataSet);
        float minValue = Collections.min(dataSet);

        int a = (int) maxValue % 100;


        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(maxValue);
        chart.animateXY(1000, 1000);
        chart.invalidate();

        ArrayList<Entry> values = new ArrayList<>();

        //  Utilities utilities = new Utilities();
        for (int i = 1; i < dataSet.size(); i++) {
//            values.add(new Entry(Float.valueOf(utilities.transFromDate(dateList.get(i)))
//                    , Float.valueOf(list.get(i).getTotal())));
            // Log.d("TAG", "MONTH: " + dataSet.size());

            values.add(new Entry(i, dataSet.get(i)));


        }
//        values.add(new Entry(1, 300));
//        values.add(new Entry(2, 200));
//        values.add(new Entry(4, 100));
//        values.add(new Entry(5, 450));
//        values.add(new Entry(6, 300));
//        values.add(new Entry(8, 800));
//        values.add(new Entry(10, 1300));
//        values.add(new Entry(11, 200));
//        values.add(new Entry(12, 3000));


        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.BLACK);
            set1.setHighLightColor(Color.BLUE);
            set1.setColor(Color.parseColor("#1aa397"));
            // set1.setFillColor(Color.parseColor("#20d3c2"));
            set1.setFillAlpha(100);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });


            // create a data object with the data sets
            LineData data = new LineData(set1);
            //  data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);


            // set data
            chart.setData(data);
        }
    }

    /*
    here  dataset is the list of a float which has 12 index of 12 month from index 1 ;
    float value represents the total spend in that month

     */

    private void filterDataForMonth(List<oldOrderModel.LineItem> list, List<String> dateList) {

        Utilities utilities = new Utilities();
        // creating data set
        List<Float> dataSet = new ArrayList<>();
        for (int i = 0; i <= 13; i++) {
            dataSet.add((float) 0);
        }
        // here we  filter
        for (int i = 0; i < list.size(); i++) {
            // get the month number
            String numberOfMonthStr = utilities.transFromDate(dateList.get(i));
            int numberOfMonth = Integer.parseInt(numberOfMonthStr);
            float newData = dataSet.get(numberOfMonth) + list.get(i).getPrice();
            dataSet.set(numberOfMonth, newData);
        }


        setData(dataSet);

    }

    public String getMonthName(float monNumber) {
        String name = "null";

        if (monNumber == 1) {
            name = "Jan";
        } else if (monNumber == 2) {
            name = "Feb";
        } else if (monNumber == 3) {
            name = "Mar";
        } else if (monNumber == 4) {
            name = "Apr";
        } else if (monNumber == 5) {
            name = "May";
        } else if (monNumber == 6) {
            name = "Jun";
        } else if (monNumber == 7) {
            name = "Jul";
        } else if (monNumber == 8) {
            name = "Aug";
        } else if (monNumber == 9) {
            name = "Sept";
        } else if (monNumber == 10) {
            name = "Oct";
        } else if (monNumber == 11) {
            name = "Nov";
        } else if (monNumber == 12) {
            name = "Dec";
        }

        return name;

    }

    public void countCartItemNumber() {

        try {
            List<CartDbModel> models = cartDatabase.dao().fetchAllTodos();
            cartCountTv.setText(models.size() + "");
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        countCartItemNumber();
        super.onResume();
    }
}