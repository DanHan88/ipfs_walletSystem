$(document).ready(function() {
						$('#dataTableContainer').show();
						
						 $('.announcementBodyBtn').on('click', function() {
							 
							 $('#announcement_body').val($(this).data('announce-dody'));
							 
					    	$('#announcementBodyModal').modal('show');
					    });
						
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});
					    
});