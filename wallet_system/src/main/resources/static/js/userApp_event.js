$(document).ready(function() {
						$('#dataTableContainer').show();
						
						
						 $('.eventBodyBtn').on('click', function() {
							$('#event_body').val($(this).data('event-dody'));
					    	$('#eventBodyModal').modal('show');
					    });
					   
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});
					    
});