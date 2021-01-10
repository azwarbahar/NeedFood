package com.technest.needfood.example;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ruesga.timelinechart.TimelineChartView;
import com.technest.needfood.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ContohChartActivity extends AppCompatActivity {

    private InMemoryCursor mCursor;

    private Toolbar mToolbar;
    private TimelineChartView mGraph;
    private TextView mTimestamp;
    private TextView[] mSeries;
    private View[] mSeriesColors;

    private Calendar mStart;

    private final SimpleDateFormat DATETIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final NumberFormat NUMBER_FORMATTER = new DecimalFormat("#0.00");
    private final String[] COLUMN_NAMES = {"timestamp", "Serie 1", "Serie 2", "Serie 3"};

    private final int[] MODES = {
            TimelineChartView.GRAPH_MODE_BARS,
            TimelineChartView.GRAPH_MODE_BARS_STACK,
            TimelineChartView.GRAPH_MODE_BARS_SIDE_BY_SIDE};
    private final String[] MODES_TEXT = {
            "GRAPH_MODE_BARS",
            "GRAPH_MODE_BARS_STACK",
            "GRAPH_MODE_BARS_SIDE_BY_SIDE"};
    private int mMode;
    private int mSound;
    private boolean mInLiveUpdate;

    private static final int LIVE_UPDATE_INTERVAL = 2;
    private Handler mHandler;
    private final Runnable mLiveUpdateTask = new Runnable() {
        @Override
        public void run() {
            mStart.setTimeInMillis(System.currentTimeMillis());
            mStart.set(Calendar.MILLISECOND, 0);
            mCursor.add(createItem(mStart.getTimeInMillis()));
            mHandler.postDelayed(this, LIVE_UPDATE_INTERVAL * 1000);
        }
    };

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mode:
                    mMode++;
                    if (mMode >= MODES.length) {
                        mMode = 0;
                    }
                    mGraph.setGraphMode(MODES[mMode]);
                    Toast.makeText(ContohChartActivity.this, MODES_TEXT[mMode], Toast.LENGTH_SHORT).show();
                    break;
                case R.id.footer:
                    mGraph.setShowFooter(!mGraph.isShowFooter());
                    break;
                case R.id.color:
                    int color = Color.rgb(random(255), random(255), random(255));
                    mToolbar.setBackgroundColor(color);
                    mGraph.setBackgroundColor(color);
                    mGraph.setGraphAreaBackground(color);
                    break;
                case R.id.sound:
                    mSound++;
                    if (mSound > 2) {
                        mSound = 0;
                    }
                    if (mSound == 0) {
                        mGraph.setPlaySelectionSoundEffect(false);
                    } else if (mSound == 1) {
                        mGraph.setSelectionSoundEffectSource(0);
                        mGraph.setPlaySelectionSoundEffect(true);
                    } else {
                        mGraph.setSelectionSoundEffectSource(R.raw.selection_effect);
                    }
                    break;
                case R.id.reload:
                    mCursor = createInMemoryCursor();
                    mGraph.observeData(mCursor);
                    break;
                case R.id.live_update:
                    mHandler.removeCallbacks(mLiveUpdateTask);
                    mCursor.removeAll();
                    if (!mInLiveUpdate) {
                        mGraph.setFollowCursorPosition(true);
                        mHandler.post(mLiveUpdateTask);
                    } else {
                        mGraph.setFollowCursorPosition(false);
                        createRandomData(mCursor);
                    }
                    mInLiveUpdate = !mInLiveUpdate;
                    findViewById(R.id.reload).setEnabled(!mInLiveUpdate);
                    findViewById(R.id.add).setEnabled(!mInLiveUpdate);
                    findViewById(R.id.update).setEnabled(!mInLiveUpdate);
                    findViewById(R.id.delete).setEnabled(!mInLiveUpdate);
                    break;
                case R.id.add:
                    mStart.add(Calendar.DAY_OF_WEEK, 1);
                    mCursor.add(createItem(mStart.getTimeInMillis()));
                    break;
                case R.id.delete:
                    int position = mCursor.getCount() - 1;
                    mCursor.remove(position);
                    mStart.add(Calendar.DAY_OF_WEEK, -1);
                    break;
                case R.id.update:
                    position = mCursor.getCount() - 1;
                    mCursor.update(position, createItem(mStart.getTimeInMillis()));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contoh_chart);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name);
        }

        // Buttons
        Button button = findViewById(R.id.add);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.delete);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.update);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.reload);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.live_update);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.mode);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.footer);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.color);
        button.setOnClickListener(mClickListener);
        button = findViewById(R.id.sound);
        button.setOnClickListener(mClickListener);

        button = findViewById(R.id.mode);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode++;
                if (mMode >= MODES.length) {
                    mMode = 0;
                }
                mGraph.setGraphMode(MODES[mMode]);
                Toast.makeText(ContohChartActivity.this, MODES_TEXT[mMode], Toast.LENGTH_SHORT).show();
            }
        });


        // Create random data
        mCursor = createInMemoryCursor();

        // Retrieve the data and inject the cursor so the view can start observing changes
        mGraph = findViewById(R.id.graph);
        mMode = mGraph.getGraphMode();
        mSound = mGraph.isPlaySelectionSoundEffect() ? 1 : 0;
        mSound += mGraph.getSelectionSoundEffectSource() != 0 ? 1 : 0;

        // Setup info view
        LayoutInflater inflater = LayoutInflater.from(this);
        mTimestamp = findViewById(R.id.item_timestamp);
        ViewGroup series = findViewById(R.id.item_series);
        mSeries = new TextView[COLUMN_NAMES.length - 1];
        mSeriesColors = new View[COLUMN_NAMES.length - 1];
        for (int i = 1; i < COLUMN_NAMES.length; i++) {
            View v = inflater.inflate(R.layout.serie_item_layout, series, false);
            TextView title = v.findViewById(R.id.title);
            title.setText(getString(R.string.item_name, COLUMN_NAMES[i]));
            mSeries[i - 1] = v.findViewById(R.id.value);
            mSeries[i - 1].setText("-");
            mSeriesColors[i - 1] = v.findViewById(R.id.color);
            mSeriesColors[i - 1].setBackgroundColor(Color.TRANSPARENT);
            series.addView(v);
        }

        // Setup graph view data and start listening
        mGraph.addOnSelectedItemChangedListener(new TimelineChartView.OnSelectedItemChangedListener() {
            @Override
            public void onSelectedItemChanged(TimelineChartView.Item selectedItem, boolean fromUser) {
                mTimestamp.setText(DATETIME_FORMATTER.format(selectedItem.mTimestamp));
                for (int i = 0; i < mSeries.length; i++) {
                    mSeries[i].setText(NUMBER_FORMATTER.format(selectedItem.mSeries[i]));
                }
            }

            @Override
            public void onNothingSelected() {
                mTimestamp.setText("-");
                for (TextView v : mSeries) {
                    v.setText("-");
                }
            }
        });
        mGraph.addOnColorPaletteChangedListener(new TimelineChartView.OnColorPaletteChangedListener() {
            @Override
            public void onColorPaletteChanged(int[] palette) {
                int count = mSeriesColors.length;
                for (int i = 0; i < count; i++) {
                    mSeriesColors[i].setBackgroundColor(palette[i]);
                }
            }
        });
        mGraph.setOnClickItemListener(new TimelineChartView.OnClickItemListener() {
            @Override
            public void onClickItem(TimelineChartView.Item item, int serie) {
                String timestamp = DATETIME_FORMATTER.format(item.mTimestamp);
                Toast.makeText(ContohChartActivity.this, "onClickItem => " + timestamp
                        + ", serie: " + serie, Toast.LENGTH_SHORT).show();
                mGraph.smoothScrollTo(item.mTimestamp);
            }
        });
        mGraph.setOnLongClickItemListener(new TimelineChartView.OnLongClickItemListener() {
            @Override
            public void onLongClickItem(TimelineChartView.Item item, int serie) {
                String timestamp = DATETIME_FORMATTER.format(item.mTimestamp);
                Toast.makeText(ContohChartActivity.this, "onLongClickItem => " + timestamp
                        + ", serie: " + serie, Toast.LENGTH_SHORT).show();

            }
        });
        mGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ContohChartActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
        mGraph.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ContohChartActivity.this, "onLongClick", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mGraph.observeData(mCursor);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int random(int max) {
        return (int) (Math.random() * (max + 1));
    }

    private Object[] createItem(long timestamp) {
        Object[] item = new Object[COLUMN_NAMES.length];
        item[0] = timestamp;
        for (int i = 1; i < COLUMN_NAMES.length; i++) {
            item[i] = random(9999);
        }
        return item;
    }

    private InMemoryCursor createInMemoryCursor() {
        InMemoryCursor cursor = new InMemoryCursor(COLUMN_NAMES);
        createRandomData(cursor);
        return cursor;
    }

    private void createRandomData(InMemoryCursor cursor) {
        List<Object[]> data = new ArrayList<>();
        Calendar today = Calendar.getInstance(Locale.getDefault());
        mStart = (Calendar) today.clone();
        mStart.add(Calendar.DAY_OF_WEEK, 6);
        while (mStart.compareTo(today) <= 0) {
            data.add(createItem(mStart.getTimeInMillis()));
            mStart.add(Calendar.DAY_OF_WEEK, 1);
        }
        mStart.add(Calendar.DAY_OF_WEEK, -1);
        cursor.addAll(data);
    }
}