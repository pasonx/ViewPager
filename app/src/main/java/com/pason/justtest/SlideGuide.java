package com.pason.justtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.pason.justtest.utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pason on 2015/8/7.
 */
public class SlideGuide extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager = null;
    private List<View> mView = new ArrayList<View>(); //List泛型存储三个mView
    private ImageView[] dots = new ImageView[3]; //将三个滑动圆圈设置为ImageView
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3}; //三个圆圈id
    private Button btn = null; //开启MainActivity按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);//加载主页面的ViewPager，里面包括三个小部分
        init();
        initDots();
    }
    private void init(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);//获得ViewPager对象
        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());//设置滑动特效
        //LayoutInflate可以直接加载xml布局，传入上下文
        LayoutInflater inflater = LayoutInflater.from(this);
        //将三个mView渲染添加xml设置的图像
        mView.add(inflater.inflate(R.layout.one,null));
        mView.add(inflater.inflate(R.layout.two,null));
        mView.add(inflater.inflate(R.layout.three,null));
        //ViewPager设置监听器
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() { //返回滑动的页面数
                return mView.size();
            }

            //类似初始化效果，向ViewPager添加页面效果
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(mView.get(position));
                return mView.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(mView.get(position));

            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return (view == o);
            }
        });
        btn = (Button) mView.get(2).findViewById(R.id.btn);
        //监听启动MainActivity的按钮，通过构造匿名对象来监听
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SlideGuide.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mViewPager.setOnPageChangeListener(this);
    }
    //初始化ImageView数组,使每个小圆圈分别对应数组每个元素实例
    private void initDots(){
        for (int i = 0 ; i < mView.size() ; i ++){
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    /*覆写OnPageChangeListener接口的方法，当加载到新的页面就开始判断
    * 当页面位置为当前页面的时候，将小圆圈设置为非透明，其他设置为透明
    */
    @Override
    public void onPageSelected(int position) {
        for(int i = 0; i < mView.size() ; i ++){
            if (position == i){
                dots[i].setImageResource(R.mipmap.login_point_selected);
            }else{
                dots[i].setImageResource(R.mipmap.login_point);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
