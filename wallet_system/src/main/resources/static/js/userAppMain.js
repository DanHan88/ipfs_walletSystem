function resizeWebView() {
						    var webViewHeight = $(window).height();  // 현재 WebView의 높이
						    var maxContentHeight = webViewHeight  - $('#webviewTopHeight').height(); 
						
						    $('#webviewHeight').css({
						        'max-height': maxContentHeight + 'px'
						    });
						}	
$(document).ready(function() {
					     $('.announcementEventBodyBtn').on('click', function() {
							 $('#eventAnnouncemen_body').val($(this).find('#eventAnnouncement_body').text());
					    	$('#eventAnnouncementBodyModal').modal('show');
					    });	
  
});