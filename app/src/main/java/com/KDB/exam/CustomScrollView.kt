package com.KDB.exam

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setMargins
import kotlin.math.roundToInt

class CustomScrollView: ScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var isScrollable:Boolean=false
    var isAddingCanvas:Boolean=false
    var layout:LinearLayout?=null
    var startTime:Long=0
    var endTime:Long=0
    var focusedPageId:Int=1
    lateinit var canvasManager:CanvasManager

    private val addBox:TextView= TextView(context).apply {
        text="Add Screen"
        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (resources.displayMetrics.heightPixels*0.1).toInt())
        setPadding(0,0,0,getDP(10))
        gravity=Gravity.CENTER or Gravity.BOTTOM
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isScrollable && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(ev!!.action) {
            MotionEvent.ACTION_DOWN->{

            }
            MotionEvent.ACTION_MOVE->{
                if(!canScrollVertically(1)){
                    //Toast.makeText(context, "Scroll View bottom reached", Toast.LENGTH_SHORT).show()
                    if(!isAddingCanvas){
                        layout!!.addView(addBox)
                        isAddingCanvas=true
                        startTime=System.currentTimeMillis()
                    }
                }
            }
            MotionEvent.ACTION_UP->{
                focusedIdCheck()
                if(!canScrollVertically(1)&&isAddingCanvas){
                    endTime=System.currentTimeMillis()
                    if(endTime-startTime>1000f){
                        Toast.makeText(context, "addView", Toast.LENGTH_SHORT).show()
                        addView()
                    }
                }
                layout!!.removeView(addBox)
                isAddingCanvas=false
            }
        }
        return isScrollable && super.onTouchEvent(ev)
    }
    fun addView(){
        val view:canvasView=canvasView(context).apply {
            val layoutBox=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, resources.displayMetrics.heightPixels)
            layoutBox.setMargins(getDP(5))
            layoutParams = layoutBox
            background=resources.getDrawable(R.color.white,null)
        }
        layout!!.addView(view)
        canvasManager.pages.add(view)
        view.page=canvasManager.pages.size
//        Log.d("asd", canvasManager.pages.size.toString())
//        if(canvasManager.pages.size==2){
//            if(canvasManager.pages[0].canvas==canvasManager.pages[1].canvas){
//                Log.d("asd", "same");
//            }
//            else{Log.d("asd", "diff");}
//        }
    }
    private fun setPage():Int{
        return 0
    }
    private fun getDP(value:Int):Int{ return (value*resources.displayMetrics.density).roundToInt() }
    private fun focusedIdCheck(){
        focusedPageId=(scrollY.toFloat()/resources.displayMetrics.heightPixels.toFloat()).roundToInt()+1
    }
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
//        if(!canScrollVertically(1)){
//
//        }
//        else if(!canScrollVertically(-1)){
//            //Toast.makeText(context, "Scroll View top reached", Toast.LENGTH_SHORT).show()
//            Log.d("asd", "first")
//        }
//        else{
//            if(isAddingCanvas){
//                isAddingCanvas=false
//            }
//        }


//    val view = getChildAt(childCount - 1)
//    val topDetector = scrollY
//    val bottomDetector: Int = (view.bottom) - (height + scrollY)
//    if (bottomDetector == 0) {
//
//    }
//    if (topDetector <= 0) {
//
//    }
    }

}