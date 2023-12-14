$(document).ready(function() {			
						 $('.announcementBodyBtn').on('click', function() {
							 
							 $('#announcement_body').val($(this).data('announce-dody'));
							 
					    	$('#announcementBodyModal').modal('show');
					    });     
						    
						 $('#dataTableContainer').show();
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});		

					    
});