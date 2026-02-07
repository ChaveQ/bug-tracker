<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ResourceBundle" %>
<%
    String apiUrl = System.getenv("API_BUGTRACKER_URL");
    if (apiUrl == null || apiUrl.trim().isEmpty()) {
        ResourceBundle rb = ResourceBundle.getBundle("client");
        apiUrl = rb.getString("api.bugtracker.url");
    }
%>
<script>
  window.APP_CONFIG = {
    apiUrl: "<%= apiUrl %>"
  };
</script>
<html>
<head>
    <title>Bug Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
      body { background-color: #f8f9fa; padding: 20px; }
      .bug-container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
      .bug-row:hover { background-color: #f1f1f1; }
    </style>
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1>Bug Tracker Dashboard</h1>
        <select id="filterSeverity" class="form-select" style="width: auto;">
            <option value="">All Severities</option>
            <option value="CRITICAL">Critical</option>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
        </select>
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#bugModal">Report Bug</button>
    </div>

    <div id="status-message" class="alert alert-info small py-1">Connecting to backend...</div>

    <div class="bug-container">
        <table class="table table-hover">
            <thead class="table-light">
            <tr>
                <th>Title</th>
                <th>Severity</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody id="bug-table-body">
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="bug-modal.jsp"/>
<script src="${pageContext.request.contextPath}/js/app.js"></script>

</body>
</html>
