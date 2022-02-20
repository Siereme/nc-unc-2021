<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="false">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Errors</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <ul class="list-group">
              <c:forEach var="error" items="${errors}">
                  <li><p class="list-group-item text-danger m-0">${error}</p></li>
              </c:forEach>
          </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">OK</button>
      </div>
    </div>
  </div>
</div>

<script>
    window.onload = () => {
        if(document.getElementById("exampleModal")){
            var myModal = new bootstrap.Modal(document.getElementById("exampleModal"));
            myModal.show()
        }
    }
</script>