function resizeWebView() {
						    var webViewHeight = $(window).height();
						    var maxContentHeight = webViewHeight  - $('#webviewTopHeight').height(); 
						    $('#webviewHeight').css({
						        'max-height': maxContentHeight + 'px'
						    });
						}	
$(document).ready(function() {
					     $('.announcementEventBodyBtn').on('click', function() {
							 debugger;
							 $('#eventAnnouncemen_body').val($(this).find('#eventAnnouncement_body').text());
					    	$('#eventAnnouncementBodyModal').modal('show');
					    });	
  
});