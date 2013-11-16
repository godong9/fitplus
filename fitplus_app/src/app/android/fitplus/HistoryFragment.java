package app.android.fitplus;

import com.essence.chart.Chart;
import com.essence.chart.GridData;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class HistoryFragment extends Fragment {
	//essence chart variable
	static final int WEEK_CHART = 1;
	static final int MONTH_CHART = 2;
	static final int TYPE_CHART = 3;
	private Chart m_Chart = null;
	private String m_Version = "unknown";
	private SeekBar m_SeekBarChartRotateX = null;
	private SeekBar m_SeekBarChartRotateY = null;
	private SeekBar m_SeekBarChartRotateZ = null;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		view = inflater.inflate(R.layout.fragment_history, container, false);
		this.context = getActivity();
		
		m_Chart = (Chart)view.findViewById(R.id.chart01);
		makeChart(WEEK_CHART);
		
		final ImageButton historyTitleWeekBtn = (ImageButton) view.findViewById(R.id.historyTitleWeekBtn);
		final ImageButton historyTitleMonthBtn = (ImageButton) view.findViewById(R.id.historyTitleMonthBtn);
		final ImageButton historyTitleYearBtn = (ImageButton) view.findViewById(R.id.historyTitleYearBtn);
			
		//주간 버튼 클릭시
		historyTitleWeekBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				makeChart(WEEK_CHART);
			}
		});
		//월간 버튼 클릭시
		historyTitleMonthBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				makeChart(MONTH_CHART);
			}
		});
		//연간 버튼 클릭시
		historyTitleYearBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{	
				makeChart(TYPE_CHART);
			}
		});
	
		return view;
	}
	
	public void makeChart(int chartType){
		if(chartType == WEEK_CHART){
			//Chart 에서 발생된 Motion event 를 받기 위한 Callback interface.
			//m_Chart.setCallback(m_ChartCallback);
			//차트 타입을 설정한다.
			m_Chart.setChartType(Chart.Chart_Type_Line);
			//차트 타이틀을 설정한다.
			m_Chart.setTitle("Exercise History (Week)");
			m_Chart.setTitleFontSize (20);
			m_Chart.setXAxisTitle("월");
			m_Chart.setXAxisFontColor(0x00000000);
			m_Chart.setYAxisTitle("횟수");
			m_Chart.setYAxisFontColor(0x00000000);
			//m_Chart.setZAxisTitle("z축 타이틀");
			//m_Chart.setZAxisFontColor(Color.RED);
			//차트 타이틀을 보여준다.감춘다.
			m_Chart.setTitleVisible(true);
			String[] strColumns = { "Type", "횟수(회)", "시간(분)"};
			String[] strRows = { "Week", "월", "화", "수", "목", "금", "토", "일"};
			int nRows = strRows.length;
			int nColumns = strColumns.length;
			GridData gridData = new GridData(nRows, nColumns);
			double[][] dValue = {{10,2},
								{6,23},
								{0,36},
								{22,24},
								{68,30},
								{3,8},
								{29,7},
								{20,28}}; //initialize data
			
		
			for (int nRow = 0; nRow < nRows; nRow++) {
				for(int nColumn = 0; nColumn < nColumns; nColumn++) {
					if (nRow == 0)
					{
						gridData.setCell(nRow, nColumn, strColumns[nColumn]);
					}
					else if (nColumn == 0)
					{
						gridData.setCell(nRow, nColumn, strRows[nRow]);
					}
					else
					{
						gridData.setCell(nRow, nColumn, dValue[nRow - 1][nColumn -1]);
					}
				}
			}
			//plotBy 0 이면 row 값을 x축에 / 1 이면 column 값을 x축에 배치한다.
			int plotBy = 0;
			//차트에 데이터를 설정한다.
			m_Chart.setSourceData(gridData, plotBy);
			//범례를 보여준다.감춘다.
			m_Chart.setLegendVisible(true);
			//anymaition 효과를 보여줄 수 있는 그래프인지 결과 반환
			//Log.i("banhong",">>"+m_Chart.isAnimation());
			//차트의 배경 테두리 색을 지정한다.
			m_Chart.setPlotBackdropColor(123899);
			//차트 배경화면을 설정한다.
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			//m_Chart.setPlotBackdropPicture(path+"/tmp_1371960231945/jpg");
		}
		else if(chartType == MONTH_CHART){
			//Chart 에서 발생된 Motion event 를 받기 위한 Callback interface.
			//m_Chart.setCallback(m_ChartCallback);
			//차트 타입을 설정한다.
			m_Chart.setChartType(Chart.Chart_Type_Clustered_Column);
			//차트 타이틀을 설정한다.
			m_Chart.setTitle("Exercise History (Month)");
			m_Chart.setTitleFontSize (20);
			m_Chart.setXAxisTitle("월");
			m_Chart.setXAxisFontColor(0x00000000);
			m_Chart.setYAxisTitle("횟수");
			m_Chart.setYAxisFontColor(0x00000000);
			//m_Chart.setZAxisTitle("z축 타이틀");
			//m_Chart.setZAxisFontColor(Color.RED);
			//차트 타이틀을 보여준다.감춘다.
			m_Chart.setTitleVisible(true);
			String[] strColumns = { "Type", "횟수(회)", "시간(분)"};
			String[] strRows = { "Month", "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };
			int nRows = strRows.length;
			int nColumns = strColumns.length;
			GridData gridData = new GridData(nRows, nColumns);
			double[][] dValue = {{23,46},
								{6,23},
								{0,36},
								{22,24},
								{68,66},
								{75,23},
								{29,12},
								{67,46}, 
								{3,43},
								{65,14},
								{13,23},
								{12,45}}; //initialize data
			
		
			for (int nRow = 0; nRow < nRows; nRow++) {
				for(int nColumn = 0; nColumn < nColumns; nColumn++) {
					if (nRow == 0)
					{
						gridData.setCell(nRow, nColumn, strColumns[nColumn]);
					}
					else if (nColumn == 0)
					{
						gridData.setCell(nRow, nColumn, strRows[nRow]);
					}
					else
					{
						gridData.setCell(nRow, nColumn, dValue[nRow - 1][nColumn -1]);
					}
				}
			}
			//plotBy 0 이면 row 값을 x축에 / 1 이면 column 값을 x축에 배치한다.
			int plotBy = 0;
			//차트에 데이터를 설정한다.
			m_Chart.setSourceData(gridData, plotBy);
			//범례를 보여준다.감춘다.
			m_Chart.setLegendVisible(true);
			//anymaition 효과를 보여줄 수 있는 그래프인지 결과 반환
			//Log.i("banhong",">>"+m_Chart.isAnimation());
			m_Chart.beginAnimation(false);
			//차트의 배경 테두리 색을 지정한다.
			m_Chart.setPlotBackdropColor(123899);
			//차트 배경화면을 설정한다.
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			//m_Chart.setPlotBackdropPicture(path+"/tmp_1371960231945/jpg");
		}
		else if(chartType == TYPE_CHART){
			//Chart 에서 발생된 Motion event 를 받기 위한 Callback interface.
			//m_Chart.setCallback(m_ChartCallback);
			//차트 타입을 설정한다.
			m_Chart.setChartType(Chart.Chart_Type_Pie);
			//차트 타이틀을 설정한다.
			m_Chart.setTitle("Exercise Type");
			m_Chart.setTitleFontSize (20);
		
			//차트 타이틀을 보여준다.감춘다.
			m_Chart.setTitleVisible(true);
			String[] strColumns = { "Type", "가슴", "등", "어깨", "팔", "복부", "허벅지", "종아리" };
			String[] strRows = { "Month", "운동 종류별" };
			int nRows = strRows.length;
			int nColumns = strColumns.length;
			GridData gridData = new GridData(nRows, nColumns);
			double[][] dValue = { { 10, 20, 48, 27, 39, 97, 34 } }; //initialize data
			
		
			for (int nRow = 0; nRow < nRows; nRow++) {
				for(int nColumn = 0; nColumn < nColumns; nColumn++) {
					if (nRow == 0)
					{
						gridData.setCell(nRow, nColumn, strColumns[nColumn]);
					}
					else if (nColumn == 0)
					{
						gridData.setCell(nRow, nColumn, strRows[nRow]);
					}
					else
					{
						gridData.setCell(nRow, nColumn, dValue[nRow - 1][nColumn -1]);
					}
				}
			}
			//plotBy 0 이면 row 값을 x축에 / 1 이면 column 값을 x축에 배치한다.
			int plotBy = 0;
			//차트에 데이터를 설정한다.
			m_Chart.setSourceData(gridData, plotBy);
			//범례를 보여준다.감춘다.
			m_Chart.setLegendVisible(true);
			//anymaition 효과를 보여줄 수 있는 그래프인지 결과 반환
			//Log.i("banhong",">>"+m_Chart.isAnimation());
			m_Chart.beginAnimation(false);
			//차트의 배경 테두리 색을 지정한다.
			m_Chart.setPlotBackdropColor(123899);
			//차트 배경화면을 설정한다.
			String path = Environment.getExternalStorageDirectory().getAbsolutePath();
			//m_Chart.setPlotBackdropPicture(path+"/tmp_1371960231945/jpg");
		}
	}
	
}


