package com.ui.timebarchart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;
import com.jjoe64.graphview.ValueDependentColor;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Build;

public class MainActivity extends Activity {
    
    final String tag = "testing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, "add graph");
        addWeeklyGraphView();
        addMonthlyGraphView();
        
    }

    private void addWeeklyGraphView() {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE", Locale.getDefault());
        ArrayDeque<GraphViewData> data = new ArrayDeque<GraphViewData>();
        for (int i = 1; i < 8; i++) {
            double reading = Math.random() * 500;
            Time time = new Time();
            time.setToNow();
            time.monthDay += i;
            double mills = (double) time.normalize(false);
            Log.d(tag, i + "th reading is " + reading);
            data.add(new GraphViewData(mills, reading));
        }
        GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
        seriesStyle.setValueDependentColor(new ValueDependentColor() {
            @Override
            public int get(GraphViewDataInterface data) {
                return Color.rgb((int) (data.getY()/500*255), 0, (int) (255-(data.getY()/500*255)));
            }
        });
        GraphViewSeries series = new GraphViewSeries("weekly graph", seriesStyle, data.toArray(new GraphViewData[0]));
        GraphView graphView = new BarGraphView(this, "Weekly Graph");
        graphView.addSeries(series);
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Date date = new Date((long) value);
                    return dateFormatter.format(date);
                }
                return null;
            }
        });
        graphView.setManualYAxisBounds(500, 0);
        graphView.getGraphViewStyle().setNumVerticalLabels(11);
        graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
        Log.d(tag, "The Grid Style is " + graphView.getGraphViewStyle().getGridStyle());
        Log.d(tag, "The Grid Color is " + graphView.getGraphViewStyle().getGridColor());
        
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.weekly);
        layout.addView(graphView);
    }
    
    private void addMonthlyGraphView() {
      final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd", Locale.getDefault());
      ArrayDeque<GraphViewData> data = new ArrayDeque<GraphViewData>();
      for (int i = 1; i < 31; i++) {
          double reading = Math.random() * 500;
          Time time = new Time();
          time.setToNow();
          time.monthDay += i;
          double mills = (double) time.normalize(false);
          Log.d(tag, i + "th reading is " + reading);
          data.add(new GraphViewData(mills, reading));
      }
      GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
      seriesStyle.setValueDependentColor(new ValueDependentColor() {
          @Override
          public int get(GraphViewDataInterface data) {
              return Color.rgb((int) (data.getY()/500*255), 0, (int) (255-(data.getY()/500*255)));
          }
      });
      GraphViewSeries series = new GraphViewSeries("monthly graph", seriesStyle, data.toArray(new GraphViewData[0]));
      GraphView graphView = new BarGraphView(this, "Monthly Graph");
      graphView.addSeries(series);
      graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
          @Override
          public String formatLabel(double value, boolean isValueX) {
              if (isValueX) {
                  Date date = new Date((long) value);
                  return dateFormatter.format(date);
              }
              return null;
          }
      });
      graphView.setManualYAxisBounds(500, 0);
      graphView.getGraphViewStyle().setNumVerticalLabels(11);
      graphView.getGraphViewStyle().setGridStyle(GridStyle.BOTH);
      Log.d(tag, "The Grid Style is " + graphView.getGraphViewStyle().getGridStyle());
      Log.d(tag, "The Grid Color is " + graphView.getGraphViewStyle().getGridColor());
      
      double latest = data.getLast().getX();
      double window = 20*24*60*60*1000;
      graphView.setViewPort(latest - window, window);
      graphView.setScrollable(true);
      
      LinearLayout layout = (LinearLayout) findViewById(R.id.monthly);
      layout.addView(graphView);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

}
