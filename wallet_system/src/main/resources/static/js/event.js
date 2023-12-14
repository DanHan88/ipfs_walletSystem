$(document).ready(function() {
						 $('.eventBodyBtn').on('click', function() {
							$('#event_body').val($(this).data('event-dody'));
					    	$('#eventBodyModal').modal('show');
					    });
	
	
					$('#dataTableContainer').show();
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});		    
});