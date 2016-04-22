<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JQuery 是很多插件的基础必须写在最前面  -->
<!-- 

<script src="${base}/static/plugins/jquery/jquery-1.7.2.js" type="text/javascript"></script>

JQuery 1.9.1之后版本部分方法变动,比如:live()方法被删除
As of jQuery 1.7, the .live() method is deprecated. Use .on() to attach event handlers. 
Users of older versions of jQuery should use .delegate() in preference to .live().
This method provides a means to attach delegated event handlers to the document element of a page, 
which simplifies the use of event handlers when content is dynamically added to a page. 
See the discussion of direct versus delegated events in the.on() method for more information.
改进后的使用建议：
1. $(selector).live(events, data, handler); // jQuery 1.3+
2. $(document).delegate(selector, events, data, handler); // jQuery 1.4.3+
3. $(document).on(events, selector, data, handler); // jQuery 1.7+

 -->
<script src="${base}/static/plugins/jquery/jquery-${(not empty jqueryVersion) ? jqueryVersion : '1.11.0' }.js"></script>
<script src="${base}/static/plugins/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="${base}/static/plugins/bootstrap/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${base}/static/plugins/bootstrap/js/bootstrap-modalmanager.js" type="text/javascript"></script>
<script src="${base}/static/plugins/JSON/json2.js" type="text/javascript"></script>
<script src="${base}/static/plugins/select2/select2.js" type="text/javascript"></script>
<script src="${base}/static/plugins/uploadify/jquery.uploadify.js" type="text/javascript"></script>
<script src="${base}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${base}/static/plugins/jquery-validation/jquery.validate.js" type="text/javascript"></script>
<script src="${base}/static/plugins/base64/base64.js" type="text/javascript"></script>
<script src="${base}/static/common/default/js/base.js" type="text/javascript"></script>