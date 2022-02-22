package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mUserName : String ? = null
    private var mCurrecrAnswers: Int  = 0

    private var mCurrentPositsion: Int = 1
    private var mQestionsList: ArrayList<Question> ? = null
    private var mSelectedOptionsPositsion: Int = 0
    private var progressBar: ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestions: TextView? = null
    private var ivImage : ImageView? = null
    private var tvOptionOne:TextView? = null
    private var tvOptionTwo:TextView? = null
    private var tvOptionThree:TextView? = null
    private var tvOptionFour:TextView? = null
    private var btnSubmit:Button? = null



    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestions = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        mQestionsList = Constants.getQuestions()
        setQuestion()

    }

    private fun setQuestion() {

        defaultOptionsView()

        val question: Question = mQestionsList!![mCurrentPositsion - 1]
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPositsion
        tvProgress?.text = "$mCurrentPositsion/${progressBar?.max?.plus(1)}"
        tvQuestions?.text = question.questions
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        if (mCurrentPositsion == mQestionsList!!.size) {
            btnSubmit?.text = "Finish"
        }else{
            btnSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionsView(){
        var options = ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0,it)
        }
        tvOptionTwo?.let{
            options.add(1,it)
        }
        tvOptionThree?.let{
            options.add(2,it)
        }
        tvOptionFour?.let{
            options.add(3,it)
        }
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg,

            )
        }
    }

    private fun selectedOprtionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionsPositsion = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one->{
                tvOptionOne?.let {
                    selectedOprtionView(it,1)
                }
            }
            R.id.tv_option_two->{
                tvOptionTwo?.let {
                    selectedOprtionView(it,2)
                }
            }
            R.id.tv_option_three->{
                tvOptionThree?.let {
                    selectedOprtionView(it,3)
                }
            }
            R.id.tv_option_four->{
                tvOptionFour?.let {
                    selectedOprtionView(it,4)
                }
            }
            R.id.btn_submit->{
                if(mSelectedOptionsPositsion==0){
                    mCurrentPositsion++
                    when{
                        mCurrentPositsion <= mQestionsList!!.size->{
                            setQuestion()
                        }else->{
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANSVERS,mCurrecrAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTION,mQestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQestionsList?.get(mCurrentPositsion - 1)
                    if(question!!.correctAnswer!=mSelectedOptionsPositsion){
                        answerView(mSelectedOptionsPositsion,R.drawable.wrong_option_border_bg)
                    }
                    else{
                        mCurrecrAnswers++

                    }
                    answerView(question.correctAnswer,R.drawable.currect_option_border_bg)

                    if (mCurrentPositsion==mQestionsList!!.size){
                        btnSubmit?.text = "Finish"
                    }else{
                        btnSubmit?.text = "Go To Next Question"
                    }
                    mSelectedOptionsPositsion = 0

                }
            }
        }
    }

    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1->{
                tvOptionOne?.background = ContextCompat.getDrawable(this,drawableView)
            }
            2->{
                tvOptionTwo?.background = ContextCompat.getDrawable(this,drawableView)
            }
            3->{
                tvOptionThree?.background = ContextCompat.getDrawable(this,drawableView)
            }
            4->{
                tvOptionFour?.background = ContextCompat.getDrawable(this,drawableView)
            }
        }
    }
}