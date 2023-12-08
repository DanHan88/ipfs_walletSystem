$(document).ready(function() {
						$('#dataTableContainer').show();
						 $('#payout_fil_request').on('click', function() {
					       $('#payout_request_modal').modal('show');
					    });
						
						 $('#payout_confirm_button').on('click', function() {	
							 let fil_amount= $('#payout_fil_amount').val();
							 let user_id = $('#payout_fil_request').val();
							 let wallet_address = $('#payout_fil_address').val();
					       $.ajax({
			                    type: "POST",
			                    url: "/addWalletWithdrawal",
			                    contentType: "application/json",
			                    data: JSON.stringify({
							        fil_amount: fil_amount,
							        user_id: user_id,
							        wallet_address : wallet_address
							    }),
			                    success: function (data) {
									$('#payout_request_modal').modal('hide');
									if(data=='success'){
										if ($('#alert_header').hasClass("bg-danger")) 
											{
							            		$('#alert_header').removeClass("bg-danger").addClass("bg-success");
							       		 	} 
										$('#alert_title').text("제품등록 성공");
										$('#alert_modal').modal('show');
			                        }
			                        else if(data=='failed:session_closed'){
										$('#session_alert_investment').modal('show');
									}
			                        else{
										if ($('#alert_header').hasClass("bg-success")) 
										{
								            $('#alert_header').removeClass("bg-success").addClass("bg-danger");
							       		} 		
							       		 $('#alert_title').text("제품등록 실패 : 중복된 제품명");
								            $('#alert_modal').modal('show');	
									}	
									$('#payout_confirm_button').show();	
			                    }
			                });
					    });
						
					     $('#dataTable').DataTable({
					    	 "order": [[0, 'desc']]
						});
					    
});