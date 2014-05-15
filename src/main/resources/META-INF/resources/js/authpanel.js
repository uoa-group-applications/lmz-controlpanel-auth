(function(LMZPA, undefined) {

	LMZPA.cbClicked = function (){
		var checked = $('#cbDumpHeaders').prop("checked");
		console.log(checked);
		console.log("Sending '"+checked+"' to: "+UOA.endpoints.admin.authpanel);
		if (checked == 'true' || checked == true){
			$("#testLink").show();
		}else{
			$("#testLink").hide();
		}
		
		$.ajax({type: 'POST',
			dataType : "JSON",
			url: UOA.endpoints.admin.authpanel, //url,
			data: JSON.stringify({dumpHeaderFlag: checked })
		}).done(function(data) {

		});
	};

})(window.LMZPA || (window.LMZPA = {}));
 