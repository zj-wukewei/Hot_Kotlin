package com.wkw.hot.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.wkw.hot.R


/**
 * Created by hzwukewei on 2017-6-12.
 * https://github.com/LianjiaTech/ProgressLayout
 */
class ProgressLayout : RelativeLayout {
    companion object {
        private val defStyleAttr = R.attr.progressLayoutDefStyle
        private val NOT_SET = -1

        private val LOADING_TAG = "ProgressLayout.loading_tag"
        private val NONE_TAG = "ProgressLayout.none_tag"
        private val ERROR_TAG = "ProgressLayout.error_tag"
    }


    private var layoutInflater: LayoutInflater? = null

    /*Some ViewGroup*/
    private var loadingContainer: View? = null
    private var noneContainer: View? = null
    private var networkErrorContainer: View? = null
    private var failedContainer: View? = null

    /*Some Id*/
    private var loadingId: Int = 0
    private var noneId: Int = 0
    private var failedId: Int = 0
    private var networkErrorId: Int = 0

    private var mNoDataText: CharSequence = ""
    private var mFailedText: CharSequence = ""
    private var failedTextView: TextView? = null
    private val contentViews = ArrayList<View>()

    enum class LAYOUT_TYPE {

        /**
         * 正在加载
         */
        LOADING,
        /**
         * 无内容
         */
        NONE,
        /**
         * 内容显示
         */
        CONTENT,
        /**
         * 网络错误
         */
        NETWORK_ERROR,
        /**
         * 加载失败
         */
        FAILED
    }

    private var currentState = LAYOUT_TYPE.LOADING

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        if (!this.isInEditMode) {
            this.init(context, attrs, defStyleAttr)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        this.layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressLayout, defStyleAttr,
                R.style.DefaultSmartStyle) ?: return

        try {
            this.loadingId = typedArray.getResourceId(R.styleable.ProgressLayout_loading_layout, NOT_SET)
            this.noneId = typedArray.getResourceId(R.styleable.ProgressLayout_none_content, NOT_SET)
            this.networkErrorId = typedArray.getResourceId(R.styleable.ProgressLayout_network_content, NOT_SET)
            this.failedId = typedArray.getResourceId(R.styleable.ProgressLayout_failed_content, NOT_SET)
        } finally {
            typedArray.recycle()
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)

        if (child.tag == null || !child.tag.equals(LOADING_TAG) && !child.tag.equals(NONE_TAG) &&
                !child.tag.equals(ERROR_TAG)) {

            this.contentViews.add(child)

            if (!this.isInEditMode) {
                this.setContentVisibility(false)
            }
        }
    }

    fun showLoading() {

        this@ProgressLayout.showLoadingView()

        this@ProgressLayout.hideNoneView()
        this@ProgressLayout.hideNetErrorView()
        this@ProgressLayout.hideFailedView()
        this@ProgressLayout.setContentVisibility(false)

        this.currentState = LAYOUT_TYPE.LOADING
    }

    fun showNone() {
        this@ProgressLayout.showNone(null)
    }

    fun showNone(retryListener: View.OnClickListener?) {

        this@ProgressLayout.showNoneView(retryListener)

        this@ProgressLayout.hideLoadingView()
        this@ProgressLayout.hideNetErrorView()
        this@ProgressLayout.hideFailedView()
        this@ProgressLayout.setContentVisibility(false)

        this.currentState = LAYOUT_TYPE.NONE
    }

    fun showNetError() {
        this@ProgressLayout.showNetError(null)
    }

    fun showNetError(retryListener: View.OnClickListener?) {

        this@ProgressLayout.showNetErrorView(retryListener)

        this@ProgressLayout.hideLoadingView()
        this@ProgressLayout.hideNoneView()
        this@ProgressLayout.hideFailedView()
        this@ProgressLayout.setContentVisibility(false)

        this.currentState = LAYOUT_TYPE.NETWORK_ERROR
    }

    fun showFailed() {
        this@ProgressLayout.showFailed(null)
    }

    fun showFailed(retryListener: View.OnClickListener?) {

        this@ProgressLayout.showFailedView(retryListener)

        this@ProgressLayout.hideLoadingView()
        this@ProgressLayout.hideNoneView()
        this@ProgressLayout.hideNetErrorView()
        this@ProgressLayout.setContentVisibility(false)

        this.currentState = LAYOUT_TYPE.FAILED
    }

    fun showContent() {

        this@ProgressLayout.hideLoadingView()
        this@ProgressLayout.hideNoneView()
        this@ProgressLayout.hideNetErrorView()
        this@ProgressLayout.hideFailedView()

        this@ProgressLayout.setContentVisibility(true)

        this.currentState = LAYOUT_TYPE.CONTENT
    }

    fun getCurrentState(): LAYOUT_TYPE {
        return currentState
    }

    /**
     * 显示正在加载界面
     */
    private fun showLoadingView() {

        if (this.loadingContainer == null) {

            if (loadingId == NOT_SET) {
                throw IllegalStateException(
                        "cannot call showLoadingView() when loadingId was NO_SET which value is -1")
            }

            this.loadingContainer = this.layoutInflater!!.inflate(loadingId, this@ProgressLayout, false)
            this.loadingContainer!!.tag = LOADING_TAG

            val layoutParams = loadingContainer!!.layoutParams as LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            this@ProgressLayout.addView(loadingContainer, layoutParams)
        } else {
            this.loadingContainer!!.visibility = View.VISIBLE
        }
    }

    fun setNoDataText(noDataText: CharSequence) {
        mNoDataText = noDataText
    }

    fun setFailedText(failedText: CharSequence) {
        mFailedText = failedText
    }

    /**
     * 显示无内容界面

     * @param retryListener 点击事件回调
     */
    private fun showNoneView(retryListener: View.OnClickListener?) {

        if (this.noneContainer == null) {

            if (noneId == NOT_SET) {
                throw IllegalStateException(
                        "cannot call showNoneView() when noneId was NO_SET which value is -1")
            }

            this.noneContainer = this.layoutInflater!!.inflate(noneId, this@ProgressLayout, false)
            this.noneContainer!!.tag = NONE_TAG

            if (mNoDataText.toString().isNotBlank()) {
                (this.noneContainer!!.findViewById(R.id.textview_no_data) as TextView).text = mNoDataText
            }

            val layoutParams = noneContainer!!.layoutParams as LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            this@ProgressLayout.addView(noneContainer, layoutParams)

            if (retryListener != null) {
                this.noneContainer!!.isClickable = true
                this.noneContainer!!.setOnClickListener(retryListener)
            }
        } else {
            this.noneContainer!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示网络错误界面

     * @param retryListener 点击事件回调
     */
    private fun showNetErrorView(retryListener: View.OnClickListener?) {

        if (this.networkErrorContainer == null) {

            if (networkErrorId == NOT_SET) {
                throw IllegalStateException(
                        "cannot call showNetErrorView() when networkErrorId was NO_SET which value is -1")
            }

            this.networkErrorContainer = this.layoutInflater!!.inflate(networkErrorId, this@ProgressLayout, false)
            this.networkErrorContainer!!.tag = ERROR_TAG

            val layoutParams = networkErrorContainer!!.layoutParams as LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            this@ProgressLayout.addView(networkErrorContainer, layoutParams)

            if (retryListener != null) {
                this.networkErrorContainer!!.isClickable = true
                this.networkErrorContainer!!.setOnClickListener(retryListener)
            }
        } else {
            this.networkErrorContainer!!.visibility = View.VISIBLE
        }
    }

    /**
     * 显示加载失败界面

     * @param retryListener 点击事件回调
     */
    private fun showFailedView(retryListener: View.OnClickListener?) {

        if (this.failedContainer == null) {

            if (failedId == NOT_SET) {
                throw IllegalStateException(
                        "cannot call showFailedView() when failedId was NO_SET which value is -1")
            }

            this.failedContainer = this.layoutInflater!!.inflate(failedId, this@ProgressLayout, false)
            this.failedContainer!!.tag = ERROR_TAG
            try {
                failedTextView = this.failedContainer!!.findViewById(R.id.textview_failed) as TextView
                setFailledText()
            } catch (e: Exception) {
                e.printStackTrace()
            }


            val layoutParams = failedContainer!!.layoutParams as LayoutParams
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            this@ProgressLayout.addView(failedContainer, layoutParams)

            if (retryListener != null) {
                this.failedContainer!!.isClickable = true
                this.failedContainer!!.setOnClickListener(retryListener)
            }
        } else {
            setFailledText()
            this.failedContainer!!.visibility = View.VISIBLE
        }
    }

    private fun setFailledText() {
        if (mFailedText.toString().isNotBlank() && failedTextView != null) {
            failedTextView!!.text = mFailedText
        }
    }

    /**
     * 隐藏正在加载界面
     */
    private fun hideLoadingView() {
        if (loadingContainer != null && loadingContainer!!.visibility !== GONE) {
            this.loadingContainer!!.visibility = GONE
        }
    }

    /**
     * 隐藏无内容界面
     */
    private fun hideNoneView() {
        if (noneContainer != null && noneContainer!!.visibility !== GONE) {
            this.noneContainer!!.visibility = GONE
        }
    }

    /**
     * 隐藏网络错误界面
     */
    private fun hideNetErrorView() {
        if (networkErrorContainer != null && networkErrorContainer!!.visibility !== GONE) {
            this.networkErrorContainer!!.visibility = GONE
        }
    }

    /**
     * 隐藏数据错误界面
     */
    private fun hideFailedView() {
        if (failedContainer != null && failedContainer!!.visibility !== GONE) {
            this.failedContainer!!.visibility = GONE
        }
    }

    fun isLoading(): Boolean {
        return this.currentState == LAYOUT_TYPE.LOADING
    }

    fun isContent(): Boolean {
        return this.currentState == LAYOUT_TYPE.CONTENT
    }

    fun isNone(): Boolean {
        return this.currentState == LAYOUT_TYPE.NONE
    }

    fun isNetworkError(): Boolean {
        return this.currentState == LAYOUT_TYPE.NETWORK_ERROR
    }

    fun isFailed(): Boolean {
        return this.currentState == LAYOUT_TYPE.FAILED
    }

    private fun setContentVisibility(visible: Boolean) {
        for (contentView in contentViews) {
            contentView.visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}