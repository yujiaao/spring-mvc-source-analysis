# reloading demo

 表示应用状态的变量以单例方式保存在Application对象中。
 通过GlobalFilter这个WebFilter实例， 在Web请求到来时进行检查，
 如果是加载状态，FilterChain中断执行， 否则正常执行。
 