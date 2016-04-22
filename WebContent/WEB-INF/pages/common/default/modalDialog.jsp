<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 基本的弹窗 START -->
<div class="modal hide fade" id="alertDialog">
	<div class="modal-header" style="height: 15px; padding-top: 0px;">
		<a class="close" data-dismiss="modal">×</a>
		<h4 id="alertDialogH4"></h4>
	</div>
	<div class="alert alert-block alert-error fade in">
		<h4 class="alert-heading" id="alertDialogAlertHeading"></h4>
		<p id="alertContext"></p>
		<p>
			<button class="btn btn-danger" id="alertBtn" data-dismiss="modal"></button>
			<!-- <button class="btn">Or do this</button> -->
		</p>
	</div>
</div>
<div class="modal hide fade" id="successDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="successDialogH4"></h3>
	</div>
	<div class="modal-body">
		<p id="successContext"></p>
	</div>
	<div class="modal-footer">
		<button id="successBtn" class="btn btn-primary"></button>
	</div>
</div>
<div class="modal hide fade" id="confirmDialog">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="confirmDialogH4"></h3>
	</div>
	<div class="modal-body">
		<p id="confirmContext"></p>
	</div>
	<div class="modal-footer">
		<button id="cancelBtn" class="btn" data-dismiss="modal"></button>
		<button id="confirmBtn" class="btn btn-danger"></button>
	</div>
</div>
<!-- 基本的弹窗 END -->