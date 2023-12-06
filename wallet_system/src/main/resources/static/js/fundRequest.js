$(document).ready(function() {
						$('#dataTableContainer').show();
								
						$('.request-approve').each(function() {
						   if($(this).parent().parent().find('#user_status').text()!='요청'){
							   $(this).hide();
							   $(this).parent().parent().find('.request-decline').hide();
						   }
						});
						
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});
					    
});