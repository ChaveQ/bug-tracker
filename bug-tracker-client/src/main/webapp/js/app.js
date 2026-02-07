const API_URL = window.APP_CONFIG.apiUrl;

$(document).ready(function () {
  loadBugs();

  $("#filterSeverity").on("change", function (event) {
    const severity = $(this).val();
    loadBugs(severity);
  })

  $('#bugModal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    if (button.hasClass("btn-success")) {
      $("#modalTitle").text("Report new bug");
      $("#bugId").val("");
      $("#bugForm")[0].reset();
    }
  });

  $(document).on("click", ".bug-row", function () {
    let row = $(this);
    $("#modalTitle").text("Edit Bug #" + row.data("id"));
    $("#bugId").val(row.data("id"));
    $("#title").val(row.data("title"));
    $("#description").val(row.data("description"));
    $("#severity").val(row.data("severity"));
    $("#status").val(row.data("status"));

    new bootstrap.Modal(document.getElementById('bugModal')).show();
  });

  $("#saveBugBtn").click(function () {
    let id = $("#bugId").val();
    let isUpdate = (id !== "");
    const severity = $("#filterSeverity").val() || "";

    const bugData = {
      id: id ? parseInt(id) : null,
      title: $("#title").val(),
      description: $("#description").val(),
      severity: $("#severity").val(),
      status: $("#status").val()
    };

    $.ajax({
      url: isUpdate ? API_URL + "/" + id : API_URL,
      type: isUpdate ? "PUT" : "POST",
      contentType: "application/json",
      data: JSON.stringify(bugData),
      success: function () {
        bootstrap.Modal.getInstance(document.getElementById('bugModal')).hide();
        loadBugs(severity);
      },
      error: function (xhr) {
        alert("Error " + xhr.status + ": Request failed");
      }
    });
  });

  function loadBugs(severity = "") {
    let url = severity === "" ? API_URL : API_URL + "?severity=" + severity;

    $.ajax({
      url: url,
      type: "GET",
      success: function (bugs) {
        let tbody = $("#bug-table-body");
        tbody.empty();

        if (bugs.length === 0) {
          tbody.append(
              '<tr><td colspan="3" class="text-center">No bugs found in database.</td></tr>');
        } else {
          $.each(bugs, function (i, bug) {
            tbody.append(`
            <tr class="bug-row" 
                data-id="${bug.id}" data-title="${bug.title}" 
                data-description="${bug.description || ''}" 
                data-severity="${bug.severity}" data-status="${bug.status}"
                style="cursor:pointer">
              <td>${bug.title}</td>
              <td>${bug.severity}</td>
              <td>${bug.status}</td>
            </tr>`);
          });
        }

        $("#status-message").text("Loading completed")
        .addClass("alert-success").removeClass("alert-danger alert-info");
      },
      error: function (xhr, status, error) {
        console.error("Connection failed! " + error);

        $("#status-message").text("Backend unreachable")
        .addClass("alert-danger").removeClass("alert-success alert-info");
      }
    });
  }
});