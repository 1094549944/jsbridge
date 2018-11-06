/*
 * @Author: jiaxinying 
 * @Date: 2018-11-02 16:03:25 
 * @Last Modified by: jiaxinying
 * @Last Modified time: 2018-11-02 16:58:15
 * 关于h5 和native 之间的交互 JSBridge 解决问题，偏向前端
 * 使用URL 拦截方式，借用iframe 
 */

/**
 * ios,使用自有shouldStartLoadWithRequest 方法
 * 安卓：shouldOverrideUrlLoading
 * 
 * 
 */


var bridge = {
  'linkNative': function (func, type, params, callback) {
    var funcId = 'cb_' + +new Date()
    // window[funcId] = callback || function () { }
    params = params || {}

    var req = {
      handler: func,
      params: params || undefined,
      callback: callback && funcId
    }
    var src = 'taogu://' + type + '?' + JSON.stringify(req)

    var frag = document.createElement('iframe')
    frag.style.display = 'none'

    frag.src = src
    document.body.appendChild(frag);
    setTimeout(function () {
      document.body.removeChild(frag)
    }, 1000)
  },
  'showLoading': function () {
    this.linkNative('showLoading', 'functional')
    return this
  },
  'closeLoading': function () {
    this.linkNative('closeLoading', 'functional')
    return this
  },
  'showAlert': function (params) {
    this.linkNative('showAlert', 'functional', params)
  },
  'toast': function (params) {
    this.linkNative('toast', 'functional', params)
    return this
  },
  // 发起支付请求
  'pending_payment': function (params, callback) {
    this.linkNative('pending_payment', 'functional', params, callback)
  }
}

