const searchs = { username: "", role: "" };
const usersDataTable = $('#usersTable').DataTable({
	"orderMulti": true,
	"searching": false,
	"lengthChange": false,
	"processing": true,
	"serverSide": true,
	"ajax": {
		"url": "users",
		"type": "POST",
		"data": function(params, _settings) {
			params.username = searchs.username;
			params.role = searchs.role;
		},
		"beforeSend": function(jqXHR, settings) {
			$("#txt-search").prop('disabled', true);
			$("#txt-role").prop('disabled', true);
			$("#btn-search").prop('disabled', true);
		},
		"complete": function(jqXHR, textStatus) {
			$("#txt-search").prop('disabled', false);
			$("#txt-role").prop('disabled', false);
			$("#btn-search").prop('disabled', false);
		},
		"error": function(xhr, error, thrown) {
			console.log(error);
		}
	},
	"columnDefs": [{ targets: [0, 5], width: 10 },],
	"columns": [
		{ data: "id", name: "id", className: "text-center" },
		{ data: "username", name: "username" },
		{ data: "password", name: "password", render: function() { return '************'; } },
		{
			data: "role", name: "role", render: function(data) {
				if (data === 'ROLE_ADMIN') {
					return 'Quản lý';
				}
				return 'Nhân viên';
			}
		},
		{
			data: "status", name: "status", className: "text-center", render: function(data) {
				if (data === 0) {
					return '<i class="bi bi-shield-fill-exclamation" style="color: brown"></i>';
				}
				return '<i class="bi bi-shield-fill-check" style="color: forestgreen"></i>';
			}
		},
		{
			data: "id", name: "edit", className: "dropdown text-center", orderable: false,
			render: function(data, type, row, meta) {
				return '<i role="button" class="bi bi-gear-fill" id="dropdown' + data +
					'" data-bs-toggle="dropdown" aria-expanded="false"></i>' +
					'<ul class="dropdown-menu" aria-labelledby="dropdown' + data + '">' +
					'<li><a class="dropdown-item bi bi-person-lines-fill" href="#" onclick=loadModelUser(this,' + data + ')> Edit</a></li>' +
					'<li><a class="dropdown-item bi bi-person-x-fill" href="#" onclick=deleteUser(' + data + ')> Delete</a></li>' +
					'</ul>';
			}
		},
	],
});

const loadModelUser = function(target, id) {
	const modal = $('#modal-area');
	const modalContent = modal.find("div.modal-content")[0];

	$(target).prop('disabled', true);

	$.ajax({
		type: "GET",
		url: "users/user/" + id,
		beforeSend: function(jqXHR, settings) {
			$(modalContent).html('<div class="text-center"><div class="spinner-grow text-danger" role="status" style="width: 3rem; height: 3rem;"></div></div>');
			$(modal).modal({ backdrop: 'static' }).modal("show");
		},
		success: function(data, textStatus, jqXHR) {
			$(modalContent).html(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
		},
		complete: function(jqXHR, textStatus) {
			$(target).prop('disabled', false);
			const UsersForm = $("#UsersForm");
			if (UsersForm) {
				UsersForm.submit(function(event) { updateOrInsert(event); });
			}
		},
	});
};

const updateOrInsert = function(event) {
	event.preventDefault();

	const modal = $('#modal-area');
	const modalContent = $(modal).find("div.modal-content")[0];
	const modalBody = $(modalContent).find("div.modal-body")[0];

	const target = event.currentTarget;

	$.ajax({
		url: "users/user",
		type: "POST",
		data: $(target).serialize(),
		beforeSend: function(jqXHR, settings) {
			$(target).find(":input").prop('disabled', true);
			$("#form-spinner").removeClass("invisible");
		},
		success: function(data, textStatus, jqXHR) {
			DialogAlert({ message: data.message });
			$(modal).modal("hide");
			usersDataTable.ajax.reload(null, false);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			if (jqXHR.status === 400) {
				$(modalContent).html(jqXHR.responseText);
			}
		},
		complete: function(jqXHR, textStatus) {
			const UsersForm = $("#UsersForm");
			if (UsersForm) {
				UsersForm.submit(function(event) { updateOrInsert(event); });
			}
		},
	});
}

const deleteUser = function(id) {
	DialogConfirm(null, function() {
		$.ajax({
			url: "users/user/" + id,
			type: "DELETE",
			beforeSend: function(jqXHR, settings) {
			},
			success: function(data, textStatus, jqXHR) {
				usersDataTable.ajax.reload(null, false);
				DialogAlert({ message: data.message });
			},
			error: function(jqXHR, textStatus, errorThrown) {

			},
			complete: function(jqXHR, textStatus) {

			},
		});
	});
}

$('#btn-new').click(function() { loadModelUser(this, 0); });
$('#btn-search').click(function() {
	searchs.username = $('#txt-search').val();
	searchs.role = $("#txt-role").val();
	usersDataTable.ajax.reload();
});
